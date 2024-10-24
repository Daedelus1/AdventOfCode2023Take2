package net.ethanstewart.advents.Day10;

import com.google.common.flogger.FluentLogger;
import net.ethanstewart.data_structures.DebugMode;
import net.ethanstewart.data_structures.InputManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class Day10 {
    private static final InputManager INPUT_MANAGER;
    private static final DebugMode DEBUG_MODE = DebugMode.REAL;
    private static final FluentLogger FLOGGER = FluentLogger.forEnclosingClass();
    private static final Level LOGGING_LEVEL = Level.FINE;

    static {
        try {
            INPUT_MANAGER = new InputManager(
                    """
                            ..F7.
                            .FJ|.
                            SJ.L7
                            |F--J
                            LJ...""",
                    """
                            FF7FSF7F7F7F7F7F---7
                            L|LJ||||||||||||F--J
                            FL-7LJLJ||||||LJL-77
                            F--JF--7||LJLJ7F7FJ-
                            L---JF-JLJ.||-FJLJJ7
                            |F|F-JF---7F7-L7L|7|
                            |FFJF7L7F-JF7|JL---7
                            7-L-JL7||F7|L7F-7F7|
                            L.L7LFJ|||||FJL7||LJ
                            L7JLJL-JLJLJL--JLJ.L""",
                    new BufferedReader(new FileReader(
                            "N:\\Drive\\Programming\\Java\\AdventOfCode2023Take2\\src\\main\\java\\net\\ethanstewart\\advents\\Day10\\Input.txt"))
                            .lines().collect(Collectors.joining("\n")), DEBUG_MODE);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static long part1() {
        return PipeDeprecatedMatrix.parsePipeMatrix(INPUT_MANAGER.getInput()).getLoop().size() / 2;
    }

    public static long part2() {
        return PipeDeprecatedMatrix.parsePipeMatrix(INPUT_MANAGER.getInput()).getEnclosedArea();
    }

    public static void main(String[] args) {
        System.out.printf("Part 1 : %s\n", part1());
        System.out.printf("Part 2 : %s\n", part2());
    }

}
