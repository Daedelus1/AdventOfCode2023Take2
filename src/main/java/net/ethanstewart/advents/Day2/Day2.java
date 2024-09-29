package net.ethanstewart.advents.Day2;

import com.google.common.collect.ImmutableMap;
import net.ethanstewart.advents.Day2.Round.Color;
import net.ethanstewart.data_structures.DebugMode;
import net.ethanstewart.data_structures.InputManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Day2 {
    private static final InputManager INPUT_MANAGER;
    private static final DebugMode DEBUG_MODE = DebugMode.REAL;

    static {
        try {
            INPUT_MANAGER = new InputManager("""
                    Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
                    Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
                    Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
                    Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
                    Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green""",
                    """
                            Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
                            Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
                            Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
                            Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
                            Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green""", new BufferedReader(new FileReader(
                    "N:\\Drive\\Programming\\AdventOfCode2023Take2\\src\\main\\java\\org\\example\\advents\\Day2\\Input.txt"))
                    .lines().collect(Collectors.joining("\n")), DEBUG_MODE);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static int part1() {
        ImmutableMap<Color, Integer> maxCounts = ImmutableMap.<Color, Integer>builder()
                .put(Color.RED, 12)
                .put(Color.GREEN, 13)
                .put(Color.BLUE, 14)
                .build();
        return Arrays.stream(INPUT_MANAGER.getInput().split("\\n"))
                .map(Game::parseGame)
                .filter(game -> game.isPossible(maxCounts))
                .mapToInt(Game::id)
                .sum();
    }

    private static int part2() {
        return Arrays.stream(INPUT_MANAGER.getInput().split("\\n"))
                .map(Game::parseGame)
                .mapToInt(Game::getPower)
                .sum();
    }

    public static void main(String[] args) {
        System.out.println(part2());
    }
}
