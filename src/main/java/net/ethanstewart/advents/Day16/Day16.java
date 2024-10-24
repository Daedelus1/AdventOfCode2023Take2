package net.ethanstewart.advents.Day16;

import com.google.common.flogger.FluentLogger;
import net.ethanstewart.data_structures.DebugMode;
import net.ethanstewart.data_structures.InputManager;
import net.ethanstewart.data_structures.tensors.Matrix;
import net.ethanstewart.data_structures.tensors.MatrixAddress;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day16 {
    private static final InputManager INPUT_MANAGER;
    private static final DebugMode DEBUG_MODE = DebugMode.REAL;
    private static final FluentLogger FLOGGER = FluentLogger.forEnclosingClass();
    private static final Level LOGGING_LEVEL = Level.FINE;
    private static final Supplier<Matrix<Tile>> MATRIX_SUPPLIER;

    static {
        try {
            INPUT_MANAGER = new InputManager(
                    """
                            .|...\\....
                            |.-.\\.....
                            .....|-...
                            ........|.
                            ..........
                            .........\\
                            ..../.\\\\..
                            .-.-/..|..
                            .|....-|.\\
                            ..//.|....""",
                    """
                            """,
                    new BufferedReader(new FileReader(
                            "N:\\Drive\\Programming\\Java\\AdventOfCode2023Take2\\src\\main\\java\\net\\ethanstewart\\advents\\Day16\\Input.txt"))
                            .lines().collect(Collectors.joining("\n")), DEBUG_MODE);
            MATRIX_SUPPLIER = () -> {
                String platformString = INPUT_MANAGER.getInput();
                String[] rowStrings = platformString.trim().split("\\n+");
                return new Matrix<>(new MatrixAddress(rowStrings[0].length() - 1, rowStrings.length - 1),
                        a -> Tile.parseTile(rowStrings[(int) a.getY()].charAt((int) a.getX())));
            };
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static long PointBeam(BeamHead start) {
        Matrix<Tile> mirrorGrid = MATRIX_SUPPLIER.get();
        Matrix<HashSet<Direction>> directionSetGrid = new Matrix<>(
                new MatrixAddress(mirrorGrid.getWidth() - 1, mirrorGrid.getHeight() - 1),
                a -> new HashSet<>());
        Deque<BeamHead> beamHeadQueue = new ArrayDeque<>();
        beamHeadQueue.add(start);
        while (!beamHeadQueue.isEmpty()) {
            BeamHead beamHead = beamHeadQueue.pop();
            directionSetGrid.get(beamHead.location).add(beamHead.direction);
            mirrorGrid.get(beamHead.location).reflect(beamHead.direction).stream()
                    .map(direction -> new BeamHead(beamHead.location.add(direction.unitVector()), direction))
                    .filter(beamHead1 -> mirrorGrid.containsAddress(beamHead1.location))
                    .filter(beamHead1 -> !directionSetGrid.get(beamHead1.location).contains(beamHead1.direction))
                    .forEach(beamHeadQueue::add);
        }
        return directionSetGrid.getAllAddresses().stream()
                .map(directionSetGrid::get)
                .filter(n -> !n.isEmpty())
                .count();
    }

    public static long part1() {
        return PointBeam(new BeamHead(new MatrixAddress(0, 0), Direction.EAST));
    }

    public static long part2() {
        Matrix<Tile> matrix = MATRIX_SUPPLIER.get();
        return Stream.of(
                        IntStream.range(0, (int) matrix.getWidth())
                                .mapToObj(n -> new BeamHead(new MatrixAddress(n, 0), Direction.SOUTH)),
                        IntStream.range(0, (int) matrix.getWidth())
                                .mapToObj(n -> new BeamHead(new MatrixAddress(n, matrix.getHeight() - 1), Direction.NORTH)),
                        IntStream.range(0, (int) matrix.getHeight())
                                .mapToObj(n -> new BeamHead(new MatrixAddress(0, n), Direction.EAST)),
                        IntStream.range(0, (int) matrix.getHeight())
                                .mapToObj(n -> new BeamHead(new MatrixAddress(matrix.getWidth() - 1, n), Direction.WEST))
                )
                .flatMap(n -> n)
                .mapToLong(Day16::PointBeam)
                .max()
                .orElse(0);
    }

    public static void main(String[] args) {
//        System.out.printf("Part 1 : %s\n", part1());
        System.out.printf("Part 2 : %s\n", part2());
    }

    private record BeamHead(MatrixAddress location, Direction direction) {
    }

}
