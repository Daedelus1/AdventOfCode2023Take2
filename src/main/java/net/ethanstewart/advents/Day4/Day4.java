package net.ethanstewart.advents.Day4;

import net.ethanstewart.data_structures.DebugMode;
import net.ethanstewart.data_structures.InputManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Day4 {
    private static final InputManager INPUT_MANAGER;
    private static final DebugMode DEBUG_MODE = DebugMode.REAL;

    static {
        try {
            INPUT_MANAGER = new InputManager(
                    """
                            Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
                            Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
                            Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
                            Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
                            Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
                            Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11""",
                    """
                            """,
                    new BufferedReader(new FileReader(
                            "N:\\Drive\\Programming\\Java\\AdventOfCode2023Take2\\src\\main\\java\\net\\ethanstewart\\advents\\Day4\\Input.txt"))
                            .lines().collect(Collectors.joining("\n")), DEBUG_MODE);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static int part1() {
        return Arrays.stream(INPUT_MANAGER.getInput().trim().split("\\n"))
                .map(Card::parseCard)
                .mapToInt(Card::numPoints).sum();
    }

    public static int part2() {
        Card[] cards = Arrays.stream(INPUT_MANAGER.getInput().trim().split("\\n"))
                .map(Card::parseCard)
                .toArray(Card[]::new);
        int[] counts = new int[cards.length];
        Arrays.fill(counts, 1);
        for (int i = 0; i < counts.length; i++) {
            for (int p = cards[i].numWins() + i; p > i; p--) {
                counts[p] += counts[i];
            }
        }
        return Arrays.stream(counts).sum();
    }

    public static void main(String[] args) {
        System.out.printf("Part 1 : %s\n", part1());
        System.out.printf("Part 2 : %s\n", part2());
    }

}
