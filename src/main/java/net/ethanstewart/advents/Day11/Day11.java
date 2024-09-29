package net.ethanstewart.advents.Day11;

import com.google.common.flogger.FluentLogger;
import net.ethanstewart.data_structures.DebugMode;
import net.ethanstewart.data_structures.InputManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class Day11 {
    private static final InputManager INPUT_MANAGER;
    private static final DebugMode DEBUG_MODE = DebugMode.REAL;
    private static final FluentLogger FLOGGER = FluentLogger.forEnclosingClass();
    private static final Level LOGGING_LEVEL = Level.FINE;

    static {
        try {
            INPUT_MANAGER = new InputManager(
                    """
                            ...#......
                            .......#..
                            #.........
                            ..........
                            ......#...
                            .#........
                            .........#
                            ..........
                            .......#..
                            #...#.....""",
                    """
                            """,
                    new BufferedReader(new FileReader(
                            "N:\\Drive\\Programming\\AdventOfCode2023Take2\\src\\main\\java\\org\\example\\advents\\Day11\\Input.txt"))
                            .lines().collect(Collectors.joining("\n")), DEBUG_MODE);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static long part1() {
        return Image.parseImage(INPUT_MANAGER.getInput()).expand(1).getSumOfDistanceBetweenAllGalaxies();
    }

    private static long part2() {
        return Image.parseImage(INPUT_MANAGER.getInput()).expand(1000000 - 1).getSumOfDistanceBetweenAllGalaxies();
    }

    public static void main(String[] args) {
        System.out.println(INPUT_MANAGER.getInput());
        System.out.println(Image.parseImage(INPUT_MANAGER.getInput()));
        System.out.println(Image.parseImage(INPUT_MANAGER.getInput()).expand(1000000 - 1));
        System.out.printf("Part 1 : %s\n", part1());
        System.out.printf("Part 2 : %s\n", part2());
    }

}
