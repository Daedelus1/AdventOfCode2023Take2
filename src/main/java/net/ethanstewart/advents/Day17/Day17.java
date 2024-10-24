package net.ethanstewart.advents.Day17;

import com.google.common.collect.ImmutableMap;
import com.google.common.flogger.FluentLogger;
import net.ethanstewart.data_structures.DebugMode;
import net.ethanstewart.data_structures.InputManager;
import net.ethanstewart.data_structures.tensors.Matrix;
import net.ethanstewart.data_structures.tensors.MatrixAddress;
import net.ethanstewart.data_structures.tensors.MutableMatrix;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.PriorityQueue;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day17 {
    private static final InputManager INPUT_MANAGER;
    private static final DebugMode DEBUG_MODE = DebugMode.REAL;
    private static final FluentLogger FLOGGER = FluentLogger.forEnclosingClass();
    private static final Level LOGGING_LEVEL = Level.INFO;
    private static final Supplier<Matrix<Integer>> HEAT_LOSS_MATRIX_SUPPLIER;

    static {
        try {
            INPUT_MANAGER = new InputManager(
                    """
                            2413432311323
                            3215453535623
                            3255245654254
                            3446585845452
                            4546657867536
                            1438598798454
                            4457876987766
                            3637877979653
                            4654967986887
                            4564679986453
                            1224686865563
                            2546548887735
                            4322674655533""",
                    """
                            111111111111
                            999999999991
                            999999999991
                            999999999991
                            999999999991""",
                    new BufferedReader(new FileReader(
                            "N:\\Drive\\Programming\\Java\\AdventOfCode2023Take2\\src\\main\\java\\net\\ethanstewart\\advents\\Day17\\Input.txt"))
                            .lines().collect(Collectors.joining("\n")), DEBUG_MODE);
            String platformString = INPUT_MANAGER.getInput();
            String[] rowStrings = platformString.trim().split("\\n+");
            HEAT_LOSS_MATRIX_SUPPLIER = () -> new Matrix<>(new MatrixAddress(rowStrings[0].length() - 1, rowStrings.length - 1),
                    a -> Integer.parseInt(String.valueOf(rowStrings[(int) a.getY()].charAt((int) a.getX()))));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static int pathfind(int minDistBeforeTurn, int maxDistanceBeforeTurn) {
        final Matrix<Integer> instantaneousCostMatrix = HEAT_LOSS_MATRIX_SUPPLIER.get();
        final MatrixAddress STARTING_ADDRESS = new MatrixAddress(0, 0);
        final MatrixAddress ENDING_ADDRESS = new MatrixAddress(instantaneousCostMatrix.getWidth() - 1, instantaneousCostMatrix.getHeight() - 1);
        System.out.println(ENDING_ADDRESS.getX() + ENDING_ADDRESS.getY() * ENDING_ADDRESS.getX());
        final ImmutableMap<Direction, MutableMatrix<Integer>> directionToLowestCostMap;
        {
            Supplier<MutableMatrix<Integer>> emptyCostMatrixSupplier = () ->
                    new MutableMatrix<>(new MatrixAddress(
                            instantaneousCostMatrix.getWidth() + 1,
                            instantaneousCostMatrix.getHeight() + 1),
                            _ -> Integer.MAX_VALUE);
            directionToLowestCostMap = ImmutableMap.<Direction, MutableMatrix<Integer>>builder()
                    .put(Direction.NORTH, emptyCostMatrixSupplier.get())
                    .put(Direction.EAST, emptyCostMatrixSupplier.get())
                    .put(Direction.SOUTH, emptyCostMatrixSupplier.get())
                    .put(Direction.WEST, emptyCostMatrixSupplier.get())
                    .build();
        }

        PriorityQueue<TileInformation> searchQueue = new PriorityQueue<>();
        searchQueue.add(new TileInformation(
                STARTING_ADDRESS,
                Direction.EAST,
                0,
                0
        ));
        searchQueue.add(new TileInformation(
                STARTING_ADDRESS,
                Direction.SOUTH,
                0,
                0
        ));
        while (!searchQueue.isEmpty()) {
            TileInformation information = searchQueue.poll();
            if (information.tileLocation().equals(ENDING_ADDRESS)) {
                return information.cost();
            }
            IntStream.range(minDistBeforeTurn + 1, maxDistanceBeforeTurn + 1)
                    .boxed()
                    .forEach(distance -> information.directionOfEntry()
                            .getPerpendicularDirections()
                            .stream()
                            .filter(direction -> instantaneousCostMatrix.containsAddress(
                                    direction.unitVector().scale(distance).add(information.tileLocation())))
                            .map(direction -> {
                                MatrixAddress tileLocation = direction.unitVector().scale(distance).add(information.tileLocation());
                                return new TileInformation(
                                        tileLocation,
                                        direction,
                                        distance,
                                        IntStream.range(0, distance)
                                                .mapToObj(distance1 -> tileLocation.add(direction.unitVector().scale(-distance1)))
                                                .mapToInt(instantaneousCostMatrix::get)
                                                .sum() + information.cost());
                            })
                            .forEach(information1 -> {
                                if (information1.cost() < directionToLowestCostMap.get(information1.directionOfEntry()).get(information1.tileLocation())) {
                                    directionToLowestCostMap.get(information1.directionOfEntry()).set(information1.tileLocation(), information1.cost());
                                    searchQueue.add(information1);
                                }
                            }));
        }
        return -1;
    }

    public static long part1() {
        return pathfind(0, 3);
    }

    public static long part2() {
        return pathfind(3, 10);
    }

    public static void main(String[] args) {
        System.out.printf("Part 1 : %s\n", part1());
        System.out.printf("Part 2 : %s\n", part2());
    }

}
