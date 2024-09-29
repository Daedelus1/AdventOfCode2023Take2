package net.ethanstewart.advents.Day6;

import com.google.common.flogger.FluentLogger;
import net.ethanstewart.data_structures.DebugMode;
import net.ethanstewart.data_structures.InputManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class Day6 {
    private static final InputManager INPUT_MANAGER;
    private static final DebugMode DEBUG_MODE = DebugMode.REAL;
    private static final FluentLogger FLOGGER = FluentLogger.forEnclosingClass();
    private static final Level LOGGING_LEVEL = Level.FINE;

    static {
        try {
            INPUT_MANAGER = new InputManager(
                    """
                            Time:      7  15   30
                            Distance:  9  40  200""",
                    """
                            """,
                    new BufferedReader(new FileReader(
                            "N:\\Drive\\Programming\\AdventOfCode2023Take2\\src\\main\\java\\org\\example\\advents\\Day6\\Input.txt"))
                            .lines().collect(Collectors.joining("\n")), DEBUG_MODE);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static long part1() {
        return Race.parseRaces(INPUT_MANAGER.getInput())
                .stream()
                .mapToLong(Race::numIntegerPressingTimesThatBeatRecord)
                .reduce((a, b) -> a * b)
                .getAsLong();
    }

    private static long part2() {
        return Race.parseRacePart2(INPUT_MANAGER.getInput()).numIntegerPressingTimesThatBeatRecord();
    }

    public static void main(String[] args) {
        System.out.printf("Part 1 : %s\n", part1());
        System.out.printf("Part 2 : %s\n", part2());
    }

}
