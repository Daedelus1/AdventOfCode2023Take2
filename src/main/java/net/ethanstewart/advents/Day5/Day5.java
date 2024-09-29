package net.ethanstewart.advents.Day5;

import com.google.common.flogger.FluentLogger;
import net.ethanstewart.data_structures.DebugMode;
import net.ethanstewart.data_structures.InputManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.OptionalLong;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Day5 {
    static final InputManager INPUT_MANAGER;
    private static final DebugMode DEBUG_MODE = DebugMode.REAL;
    private static final FluentLogger FLOGGER = FluentLogger.forEnclosingClass();
    private static final Level LOGGING_LEVEL = Level.INFO;

    static {
        try {
            INPUT_MANAGER = new InputManager(
                    """
                            seeds: 79 14 55 13
                            
                            seed-to-soil map:
                            50 98 2
                            52 50 48
                            
                            soil-to-fertilizer map:
                            0 15 37
                            37 52 2
                            39 0 15
                            
                            fertilizer-to-water map:
                            49 53 8
                            0 11 42
                            42 0 7
                            57 7 4
                            
                            water-to-light map:
                            88 18 7
                            18 25 70
                            
                            light-to-temperature map:
                            45 77 23
                            81 45 19
                            68 64 13
                            
                            temperature-to-humidity map:
                            0 69 1
                            1 0 69
                            
                            humidity-to-location map:
                            60 56 37
                            56 93 4""",
                    """
                            """,
                    new BufferedReader(new FileReader(
                            "N:\\Drive\\Programming\\AdventOfCode2023Take2\\src\\main\\java\\org\\example\\advents\\Day5\\Input.txt"))
                            .lines().collect(Collectors.joining("\n")), DEBUG_MODE);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static long part1() {
        return ItemManager.parseItemManager(INPUT_MANAGER.getInput())
                .getLocations()
                .stream()
                .mapToLong(n -> n)
                .min()
                .getAsLong();
    }

    private static long part2() {
        final long NUM_THREADS = 5000;
        final ItemManager itemManager = ItemManager.parseItemManager(INPUT_MANAGER.getInput());
        for (long i = 0; i < 20000000; i += NUM_THREADS) {
            OptionalLong optionalLong = LongStream.range(i, i + NUM_THREADS).parallel()
                    .filter(n -> itemManager
                            .convertLocationToSeed(n)
                            .stream()
                            .anyMatch(itemManager::numberIsAtSeedIndexPart2))
                    .findFirst();
            if (optionalLong.isEmpty()) {
                FLOGGER.at(LOGGING_LEVEL).atMostEvery(1, TimeUnit.SECONDS)
                        .log("%s through %s FAILED%n", i, i + NUM_THREADS);
                continue;
            }
            FLOGGER.at(LOGGING_LEVEL).log("FOUND NUMBER: %s", optionalLong.getAsLong());
            return optionalLong.getAsLong();
        }
        return -1;
    }

    public static void main(String[] args) {
//        final ItemManager itemManager = ItemManager.parseItemManager(INPUT_MANAGER.getInput());
        //        System.out.printf("Part 1 : %s\n", part1());
        System.out.printf("Part 2 : %s\n", part2());
    }

}
