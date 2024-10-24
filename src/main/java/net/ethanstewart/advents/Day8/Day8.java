package net.ethanstewart.advents.Day8;

import com.google.common.flogger.FluentLogger;
import net.ethanstewart.data_structures.DebugMode;
import net.ethanstewart.data_structures.InputManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class Day8 {
    private static final InputManager INPUT_MANAGER;
    private static final DebugMode DEBUG_MODE = DebugMode.REAL;
    private static final FluentLogger FLOGGER = FluentLogger.forEnclosingClass();
    private static final Level LOGGING_LEVEL = Level.INFO;

    static {
        try {
            INPUT_MANAGER = new InputManager(
                    """
                            LLR
                            
                            AAA = (BBB, BBB)
                            BBB = (AAA, ZZZ)
                            ZZZ = (ZZZ, ZZZ)""",
                    """
                            """,
                    new BufferedReader(new FileReader(
                            "N:\\Drive\\Programming\\Java\\AdventOfCode2023Take2\\src\\main\\java\\net\\ethanstewart\\advents\\Day8\\Input.txt"))
                            .lines().collect(Collectors.joining("\n")), DEBUG_MODE);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static long part1() {
        Network network = Network.parseNetwork(INPUT_MANAGER.getInput());
        return network.distanceToMatchingId(network.nodeMap().get("AAA"), "ZZZ");
    }

    public static long part2() {
        Network network = Network.parseNetwork(INPUT_MANAGER.getInput());
        long[] startingNodes = network
                .nodeMap()
                .values()
                .stream()
                .filter(node -> node.id().matches(".*A"))
                .mapToLong(node -> network.distanceToMatchingId(node, ".*Z"))
                .toArray();
        System.out.println(Arrays.toString(startingNodes));
        return lcm(startingNodes);
    }

    private static long lcm(long a, long b) {
        return a * (b / gcd(a, b));
    }

    private static long lcm(long[] input) {
        long result = input[0];
        for (int i = 1; i < input.length; i++) result = lcm(result, input[i]);
        return result;
    }

    private static long gcd(long a, long b) {
        while (b > 0) {
            long temp = b;
            b = a % b; // % is remainder
            a = temp;
        }
        return a;
    }

    private static long gcd(long[] input) {
        long result = input[0];
        for (int i = 1; i < input.length; i++) result = gcd(result, input[i]);
        return result;
    }

    public static void main(String[] args) {
        System.out.printf("Part 1 : %s\n", part1());
        System.out.printf("Part 2 : %s\n", part2());
    }

}
