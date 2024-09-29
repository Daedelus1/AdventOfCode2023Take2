package net.ethanstewart.advents.Day3;

import com.google.common.collect.ImmutableSet;
import net.ethanstewart.data_structures.deprecated.DeprecatedCoordinate;
import net.ethanstewart.data_structures.deprecated.DeprecatedDimension;
import net.ethanstewart.data_structures.deprecated.DeprecatedMatrix;
import net.ethanstewart.data_structures.deprecated.DeprecatedRegion;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.function.Function;

public class Engine extends DeprecatedMatrix<Tile> {

    public Engine(@NotNull DeprecatedDimension matrixDimensions, @NotNull Function<DeprecatedCoordinate, Tile> CoordinateItemConverter) {
        super(matrixDimensions, CoordinateItemConverter);
    }

    public static Engine parseEngine(String engineString) {
        String adjustedString = engineString.trim().replaceAll("\\n+", "");
        int width = engineString.indexOf("\n");
        // checking that all lines are of the same length
        if (Arrays.stream(engineString.split("\\n")).mapToInt(String::length)
                .anyMatch(len -> len != width)) {
            throw new IllegalArgumentException("ENGINE HAS INCONSISTENT WIDTH");
        }
        int height = adjustedString.length() / width;
        return new Engine(new DeprecatedDimension(width, height), coordinate ->
                Tile.parseTile(adjustedString.charAt(coordinate.toIndex(width))));
    }

    public ImmutableSet<DeprecatedRegion> getAllNumberRegions() {
        ImmutableSet.Builder<DeprecatedRegion> builder = ImmutableSet.builder();
        for (int row = 0; row < getMatrixDimensions().height(); row++) {
            boolean isReadingNumber = false;
            DeprecatedCoordinate start = null;
            for (int x = 0; x < getMatrixDimensions().width(); x++) {
                if (!isReadingNumber) {
                    if (getItemAtCoordinate(x, row).isNumber()) {
                        isReadingNumber = true;
                        start = new DeprecatedCoordinate(x, row);
                    }
                } else if (!getItemAtCoordinate(x, row).isNumber()) {
                    builder.add(new DeprecatedRegion(start, new DeprecatedCoordinate(x - 1, row)));
                    isReadingNumber = false;
                }
            }
            if (isReadingNumber) {
                builder.add(new DeprecatedRegion(start, new DeprecatedCoordinate(getMatrixDimensions().width() - 1, start.y())));
            }
        }
        return builder.build();
    }

    int readNumberInRegion(DeprecatedRegion numberRegion) {
        int out = 0;
        int y = numberRegion.startIncl().y();
        for (int x = numberRegion.startIncl().x(); x <= numberRegion.endIncl().x(); x++) {
            out *= 10;
            out += getItemAtCoordinate(x, y).numericize();
        }
        return out;
    }

    private boolean coordinateIsInBounds(DeprecatedCoordinate point) {
        return getMatrixDimensions().toRegion().contains(point);
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (int y = 0; y < getMatrixDimensions().height(); y++) {
            for (int x = 0; x < getMatrixDimensions().width(); x++) {
                out.append(getItemAtCoordinate(new DeprecatedCoordinate(x, y)).toDisplayString());
            }
            out.append("\n");
        }
        return out.toString();
    }
}
