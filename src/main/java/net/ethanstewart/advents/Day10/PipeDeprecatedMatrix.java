package net.ethanstewart.advents.Day10;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.ethanstewart.data_structures.MutableBitmap;
import net.ethanstewart.data_structures.deprecated.*;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import static net.ethanstewart.data_structures.deprecated.DeprecatedDirection.*;

public class PipeDeprecatedMatrix extends DeprecatedMatrix<PipeSegment> {
    private final DeprecatedCoordinate startLocation;

    public PipeDeprecatedMatrix(@NotNull DeprecatedDimension matrixDimensions, @NotNull Function<DeprecatedCoordinate, PipeSegment> CoordinateItemConverter) {
        super(matrixDimensions, CoordinateItemConverter);
        this.startLocation = this.getAllPoints().stream()
                .filter(coordinate -> getItemAtCoordinate(coordinate).equals(PipeSegment.START))
                .findFirst()
                .get();
    }

    public static PipeDeprecatedMatrix parsePipeMatrix(String pipeMatrixString) {
        int width = pipeMatrixString.trim().indexOf("\n");
        String workingString = pipeMatrixString.trim().replaceAll("\\n", "");
        int height = workingString.length() / width;
        return new PipeDeprecatedMatrix(new DeprecatedDimension(width, height), coordinate ->
                PipeSegment.parsePipeSegment(workingString.charAt(coordinate.toIndex(width))));
    }

    /**
     * Precondition: coordinateIsValidFunction.apply(startLocation) must be true
     */
    private static ImmutableSet<DeprecatedCoordinate> floodFill(DeprecatedMatrix<PipeWall> deprecatedMatrix, DeprecatedCoordinate startLocation, DeprecatedRegion floodedRegion, Function<DeprecatedCoordinate, Boolean> isValid) {
        Set<DeprecatedCoordinate> processedCoordinates = new HashSet<>();
        Queue<DeprecatedCoordinate> coordinatesToBeProcessed = new LinkedList<>();
        MutableBitmap hasBeenTouchedBitmap = new MutableBitmap(floodedRegion.getArea());
        coordinatesToBeProcessed.add(startLocation);
        while (!coordinatesToBeProcessed.isEmpty()) {
            DeprecatedCoordinate workingCoordinate = coordinatesToBeProcessed.poll();
            processedCoordinates.add(workingCoordinate);
            workingCoordinate.getAdjacentCoordinates(DeprecatedDirection.values())
                    .stream()
                    .filter(floodedRegion::contains)
                    .filter(coordinate ->
                            !hasBeenTouchedBitmap.getBit(coordinate
                                    .toIndex(floodedRegion.getWidth())))
                    .filter(isValid::apply)
                    .forEach(coordinate -> {
                        hasBeenTouchedBitmap.setBit(coordinate.toIndex(floodedRegion.getWidth()), true);
                        coordinatesToBeProcessed.add(coordinate);
                    });
        }
        return ImmutableSet.copyOf(processedCoordinates);
    }

    private DeprecatedMatrix<PipeWall> expand() {
        ImmutableList<DeprecatedCoordinate> loop = getLoop();
        return new DeprecatedMatrix<>(new DeprecatedDimension(getMatrixDimensions().width() * 3,
                getMatrixDimensions().height() * 3),
                coordinate -> {
                    if (getItemAtCoordinate(coordinate.divide(3)) == PipeSegment.START ||
                            loop.contains(coordinate.divide(3)) &&
                                    getItemAtCoordinate(coordinate.divide(3))
                                            .expansion()
                                            .contains(coordinate.modulo(3))) {
                        return PipeWall.WALL;
                    } else {
                        return PipeWall.VOID;
                    }
                });
    }

    public long getEnclosedArea() {
        DeprecatedMatrix<PipeWall> expansion = this.expand();
        ImmutableList<DeprecatedCoordinate> loop = getLoop();
        ImmutableSet<DeprecatedCoordinate> floodedTiles =
                Stream.of(new DeprecatedCoordinate(0, 0),
                                new DeprecatedCoordinate(0, expansion.getMatrixDimensions().width()))
                        .parallel()
                        .map(periCoord -> floodFill(expansion, periCoord,
                                expansion.getMatrixDimensions().toRegion(),
                                coordinate -> expansion.getItemAtCoordinate(coordinate) != PipeWall.WALL)
                                .stream()
                                .filter(coordinate -> coordinate.modulo(3).equals(1, 1))
                                .map(coordinate -> coordinate.divide(3))
                                .collect(ImmutableSet.toImmutableSet()))
                        .flatMap(ImmutableSet::stream)
                        .distinct()
                        .collect(ImmutableSet.toImmutableSet());
//        System.out.println(new DeprecatedMatrix<Boolean>(getMatrixDimensions(),
//                coordinate -> !(loop.contains(coordinate) || floodedTiles.contains(coordinate)))
//                .toDisplayString(bool -> bool ? "#" : ".", "\n"));
//        System.out.println(expand().toDisplayString(pipeWall -> pipeWall == PipeWall.WALL ? "#" : ".", "\n"));
        return getAllPoints()
                .stream()
                .filter(coordinate -> !floodedTiles.contains(coordinate))
                .filter(coordinate -> !loop.contains(coordinate))
                .count();
    }

    public ImmutableList<DeprecatedCoordinate> getLoop() {
        ImmutableList.Builder<DeprecatedCoordinate> builder = ImmutableList.builder();
        builder.add(startLocation);
        DeprecatedCoordinate pointer = startLocation.getAdjacentCoordinates(NORTH, SOUTH, EAST, WEST)
                .stream()
                .filter(coordinate -> getMatrixDimensions().toRegion().contains(coordinate))
                .filter(coordinate -> this.getItemAtCoordinate(coordinate)
                        .getConnections()
                        .contains(DeprecatedDirection.parseDirectionFromOrigin(startLocation, coordinate)))
                .findFirst()
                .get();
        DeprecatedDirection deprecatedDirectionOfMovement = DeprecatedDirection.parseDirectionFromOrigin(pointer, startLocation);
        while (getItemAtCoordinate(pointer) != PipeSegment.START) {
            builder.add(pointer);
            final DeprecatedDirection previousDeprecatedDirectionOfMovement = deprecatedDirectionOfMovement;
            deprecatedDirectionOfMovement = getItemAtCoordinate(pointer).getConnections()
                    .stream()
                    .filter(deprecatedDirection -> deprecatedDirection != previousDeprecatedDirectionOfMovement.inverse())
                    .findFirst()
                    .get();
            pointer = pointer.move(deprecatedDirectionOfMovement);
        }

        return builder.build();
    }

    @Override
    public String toString() {
        return String.format("PipeDeprecatedMatrix[Dimensions: %s | Data: [%s] | StartLocation: %s", getMatrixDimensions(), super.toDisplayString(PipeSegment::toDisplayString, ""), startLocation);
    }
}
