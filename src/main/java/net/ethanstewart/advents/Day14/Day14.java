package net.ethanstewart.advents.Day14;

import com.google.common.flogger.FluentLogger;
import net.ethanstewart.tensors.MatrixAddress;
import net.ethanstewart.tensors.MutableMatrix;
import net.ethanstewart.data_structures.DebugMode;
import net.ethanstewart.data_structures.InputManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.function.BiFunction;
import java.util.logging.Level;
import java.util.stream.Collectors;

import static java.util.concurrent.TimeUnit.*;

public class Day14 {
    private static final InputManager INPUT_MANAGER;
    private static final DebugMode DEBUG_MODE = DebugMode.REAL;
    private static final FluentLogger FLOGGER = FluentLogger.forEnclosingClass();
    private static final Level LOGGING_LEVEL = Level.INFO;
    
    static {
        try {
            INPUT_MANAGER = new InputManager(
                    """
                            O....#....
                            O.OO#....#
                            .....##...
                            OO.#O....O
                            .O.....O#.
                            O.#..O.#.#
                            ..O..#O..O
                            .......O..
                            #....###..
                            #OO..#....""",
                    """
                            """,
                    new BufferedReader(new FileReader(
                            "N:\\Drive\\Programming\\Java\\AdventOfCode2023Take2\\src\\main\\java\\org\\example\\advents\\Day14\\Input.txt"))
                            .lines().collect(Collectors.joining("\n")), DEBUG_MODE);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    
    private static <T> void swapTiles(MutableMatrix<T> matrix, MatrixAddress address1, MatrixAddress address2) {
        T temp = matrix.get(address1);
        matrix.set(address1, matrix.get(address2));
        matrix.set(address2, temp);
    }
    
    private static long part1() {
        String platformString = INPUT_MANAGER.getInput();
        String[] rowStrings = platformString.trim().split("\\n+");
        MutableMatrix<Tile> platform = new MutableMatrix<Tile>(new MatrixAddress(rowStrings[0].length() - 1, rowStrings.length - 1),
                a -> Tile.parseTile(rowStrings[(int) a.getY()].charAt((int) a.getX())));
        pushRocks(platform, Direction.NORTH);
        return platform.getAllAddresses().stream()
                .filter(a -> platform.get(a) == Tile.ROUND_ROCK)
                .mapToLong(a -> platform.getHeight() - a.getY())
                .sum();
    }
    
    private static long part2() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("DD:HH:mm:ss");
        DecimalFormat percentFormatter = new DecimalFormat("00.00%");
        String platformString = INPUT_MANAGER.getInput();
        String[] rowStrings = platformString.trim().split("\\n+");
        MutableMatrix<Tile> platform = new MutableMatrix<Tile>(new MatrixAddress(rowStrings[0].length() - 1, rowStrings.length - 1),
                a -> Tile.parseTile(rowStrings[(int) a.getY()].charAt((int) a.getX())));
        long startTimeMS = System.currentTimeMillis();
        long cycleStartTimeMS = startTimeMS;
        long cycleEndTimeMS;
        int NUM_TRIALS = 1000000000;
        for (int i = 0; i < NUM_TRIALS; i++) {
            spinCycle(platform);
            if (i % 1000 == 0) {
                if (i == 0) {
                    continue;
                }
                cycleEndTimeMS = System.currentTimeMillis();
                long elapsedMS = (cycleEndTimeMS - startTimeMS);
                long remainingMS = (long) ((double) (elapsedMS) * (NUM_TRIALS - i) / ((double) i));
                FLOGGER.at(LOGGING_LEVEL).log(String.format("Cycle: %s | Percent Complete: %s | Elapsed Time: %s | Time Remaining: %s | MS Remaining: %s",
                        i,
                        percentFormatter.format((double) (i) / (NUM_TRIALS)),
                        millisecondsToHMS(elapsedMS),
                        millisecondsToHMS(remainingMS),
                        remainingMS
                ));
                cycleStartTimeMS = cycleEndTimeMS;
            }
        }
        
        return platform.getAllAddresses().stream()
                .filter(a -> platform.get(a) == Tile.ROUND_ROCK)
                .mapToLong(a -> platform.getHeight() - a.getY())
                .sum();
    }
    
    private static String millisecondsToHMS(long milliseconds) {
        String hms = String.format("%02d:%02d:%02d", MILLISECONDS.toHours(milliseconds),
                MILLISECONDS.toMinutes(milliseconds) - HOURS.toMinutes(MILLISECONDS.toHours(milliseconds)),
                MILLISECONDS.toSeconds(milliseconds) - MINUTES.toSeconds(MILLISECONDS.toMinutes(milliseconds)));
        System.out.println(hms);
        return hms;
    }
    
    public static void main(String[] args) {
//        System.out.printf("Part 1 : %s\n", part1());
        System.out.printf("Part 2 : %s\n", part2());
    }
    
    private static void spinCycle(MutableMatrix<Tile> matrix) {
        pushRocks(matrix, Direction.NORTH);
        pushRocks(matrix, Direction.WEST);
        pushRocks(matrix, Direction.SOUTH);
        pushRocks(matrix, Direction.EAST);
    }
    
    private static void pushRocks(MutableMatrix<Tile> matrix, Direction direction) {
        long numSectors;
        long sectorLength;
        switch (direction) {
            case NORTH, SOUTH -> {
                numSectors = matrix.getWidth();
                sectorLength = matrix.getHeight();
            }
            default -> {
                numSectors = matrix.getHeight();
                sectorLength = matrix.getWidth();
            }
        }
        BiFunction<Integer, Integer, MatrixAddress> sectorIndexToAddressFunction = switch (direction) {
            case NORTH -> (sector, index) -> new MatrixAddress(sector, index);
            case WEST -> (sector, index) -> new MatrixAddress(index, sector);
            case SOUTH -> (sector, index) -> new MatrixAddress(sector, matrix.getHeight() - index - 1);
            case EAST -> (sector, index) -> new MatrixAddress(matrix.getWidth() - index - 1, sector);
        };
        for (int sector = 0; sector < numSectors; sector++) {
            int tail = 0;
            for (int head = 0; head < sectorLength; head++) {
                MatrixAddress headAddress = sectorIndexToAddressFunction.apply(sector, head);
                MatrixAddress tailAddress = sectorIndexToAddressFunction.apply(sector, tail);
                switch (matrix.get(headAddress)) {
                    case ROUND_ROCK -> {
                        swapTiles(matrix, headAddress, tailAddress);
                        tail++;
                    }
                    case SQUARE_ROCK -> {
                        tail = head + 1;
                    }
                }
            }
        }
    }
    
    private enum Direction {
        NORTH, EAST, SOUTH, WEST;
        
        public MatrixAddress toUnitVector() {
            return switch (this) {
                case NORTH -> new MatrixAddress(0, -1);
                case EAST -> new MatrixAddress(1, 0);
                case SOUTH -> new MatrixAddress(0, 1);
                case WEST -> new MatrixAddress(-1, 0);
            };
        }
    }
    
}
