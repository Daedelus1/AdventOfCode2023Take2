package org.example.template;

import org.example.advents.Day5.ItemManager;
import org.example.data_structures.DebugMode;
import org.example.data_structures.InputManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.stream.Collectors;

public class DayTemplate {
    private static final InputManager INPUT_MANAGER;
    private static final DebugMode DEBUG_MODE = DebugMode.REAL;
    
    static {
        try {
            INPUT_MANAGER = new InputManager(
                    """
                            """,
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
        throw new IllegalStateException("TODO: PART 1");
    }
    
    private static long part2() {
        throw new IllegalStateException("TODO: PART 2");
    }
    
    public static void main(String[] args) {
        System.out.printf("Part 1 : %s\n", part1());
        System.out.printf("Part 2 : %s\n", part2());
    }
    
}