package net.ethanstewart.advents.Day12;

import com.google.common.flogger.FluentLogger;
import net.ethanstewart.data_structures.DebugMode;
import net.ethanstewart.data_structures.InputManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class Day12 {
    private static final InputManager INPUT_MANAGER;
    private static final DebugMode DEBUG_MODE = DebugMode.REAL;
    private static final FluentLogger FLOGGER = FluentLogger.forEnclosingClass();
    private static final Level LOGGING_LEVEL = Level.FINE;

    static {
        try {
            INPUT_MANAGER = new InputManager(
                    """
                            ???.### 1,1,3
                            .??..??...?##. 1,1,3
                            ?#?#?#?#?#?#?#? 1,3,1,6
                            ????.#...#... 4,1,1
                            ????.######..#####. 1,6,5
                            ?###???????? 3,2,1""",
                    """
                            """,
                    new BufferedReader(new FileReader(
                            "N:\\Drive\\Programming\\Java\\AdventOfCode2023Take2\\src\\main\\java\\net\\ethanstewart\\advents\\Day12\\Input.txt"))
                            .lines().collect(Collectors.joining("\n")), DEBUG_MODE);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static long part1() {
        return Arrays.stream(INPUT_MANAGER.getInput().split("\\n"))
                .map(SpringRow::parseSpringRow)
                .mapToLong(SpringRow::getPossibleArrangementCounts)
                .sum();
    }

    public static long part2() {
        return Arrays.stream(INPUT_MANAGER.getInput().split("\\n"))
                .parallel()
                .map(SpringRow::parseAndUnfoldSpringRow)
                .mapToLong(SpringRow::getPossibleArrangementCounts)
                .count();
    }

    public static void main(String[] args) {
//        System.out.println(SpringRow.parseSpringRow("???.### 1,1,3").permutationIsValid(0b101));
//        System.out.println(SpringRow.parseAndUnfoldSpringRow("?###???????? 3,2,1"));
//        System.out.println(SpringRow.parseSpringRow("?###???????? 3,2,1").getPossibleArrangementCounts());
//        System.out.printf("Part 1 : %s\n", part1());
        System.out.printf("Part 2 : %s\n", part2());
    }

}
