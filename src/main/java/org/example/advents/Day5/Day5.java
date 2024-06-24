package org.example.advents.Day5;

import org.example.advents.Day5.Range.ReverseMode;
import org.example.data_structures.DebugMode;
import org.example.data_structures.InputManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.stream.Collectors;

public class Day5 {
    private static final InputManager INPUT_MANAGER;
    private static final DebugMode DEBUG_MODE = DebugMode.PART1;
    
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
        return ItemManager.parseItemManager(INPUT_MANAGER.getInput(), ReverseMode.REVERSE)
                .getLocations()
                .stream()
                .mapToLong(n -> n)
                .min()
                .getAsLong();
    }
    
    public static void main(String[] args) {
//        System.out.printf("Part 1 : %s\n", part1());
//        System.out.printf("Part 2 : %s\n", part2());
        System.out.println(ItemManager.parseItemManager(INPUT_MANAGER.getInput()).converters());
        System.out.println(ItemManager.parseItemManager(INPUT_MANAGER.getInput(), ReverseMode.REVERSE).converters());
        
    }
    
}
