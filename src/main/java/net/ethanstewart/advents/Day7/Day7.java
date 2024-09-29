package net.ethanstewart.advents.Day7;

import com.google.common.flogger.FluentLogger;
import net.ethanstewart.data_structures.DebugMode;
import net.ethanstewart.data_structures.InputManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class Day7 {
    public static final DebugMode SORTING_MODE = DebugMode.PART2;
    private static final InputManager INPUT_MANAGER;
    private static final DebugMode DEBUG_MODE = DebugMode.REAL;
    private static final FluentLogger FLOGGER = FluentLogger.forEnclosingClass();
    private static final Level LOGGING_LEVEL = Level.FINE;

    static {
        try {
            INPUT_MANAGER = new InputManager(
                    """
                            2345A 1
                            Q2KJJ 13
                            Q2Q2Q 19
                            T3T3J 17
                            T3Q33 11
                            2345J 3
                            J345A 2
                            32T3K 5
                            T55J5 29
                            KK677 7
                            KTJJT 34
                            QQQJA 31
                            JJJJJ 37
                            JAAAA 43
                            AAAAJ 59
                            AAAAA 61
                            2AAAA 23
                            2JJJJ 53
                            JJJJ2 41""",
                    """
                            """,
                    new BufferedReader(new FileReader(
                            "N:\\Drive\\Programming\\AdventOfCode2023Take2\\src\\main\\java\\org\\example\\advents\\Day7\\Input.txt"))
                            .lines().collect(Collectors.joining("\n")), DEBUG_MODE);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static long part1() {
        return Round.parseRound(INPUT_MANAGER.getInput()).winnings();
    }

    private static long part2() {
        return part1();
    }

    public static void main(String[] args) {
//        System.out.println(Round.parseRound(INPUT_MANAGER.getInput()));
//        System.out.printf("Part 1 : %s\n", part1());
        System.out.printf("Part 2 : %s\n", part2());
//        System.out.println(Hand.parseHand("77J7J").getHandType());
    }

}
