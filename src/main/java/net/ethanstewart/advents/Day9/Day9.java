package net.ethanstewart.advents.Day9;

import com.google.common.flogger.FluentLogger;
import net.ethanstewart.data_structures.DebugMode;
import net.ethanstewart.data_structures.InputManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class Day9 {
    private static final InputManager INPUT_MANAGER;
    private static final DebugMode DEBUG_MODE = DebugMode.REAL;
    private static final FluentLogger FLOGGER = FluentLogger.forEnclosingClass();
    private static final Level LOGGING_LEVEL = Level.FINE;

    static {
        try {
            INPUT_MANAGER = new InputManager(
                    """
                            0 3 6 9 12 15
                            1 3 6 10 15 21
                            10 13 16 21 30 45""",
                    """
                            """,
                    new BufferedReader(new FileReader(
                            "N:\\Drive\\Programming\\AdventOfCode2023Take2\\src\\main\\java\\org\\example\\advents\\Day9\\Input.txt"))
                            .lines().collect(Collectors.joining("\n")), DEBUG_MODE);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static long part1() {
        return Arrays.stream(INPUT_MANAGER.getInput().trim().split("\\n"))
                .map(PolynomialSequence::parseSequence)
                .mapToLong(PolynomialSequence::interpolateToNextPoint)
                .sum();
    }

    private static long part2() {
        return Arrays.stream(INPUT_MANAGER.getInput().trim().split("\\n"))
                .map(PolynomialSequence::parseSequence)
                .mapToLong(PolynomialSequence::interpolateToPointBeforeFirst)
                .sum();
    }

    public static void main(String[] args) {
        System.out.printf("Part 1 : %s\n", part1());
        System.out.printf("Part 2 : %s\n", part2());
    }

}
