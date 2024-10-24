package net.ethanstewart.advents.Day18;

import com.google.common.flogger.FluentLogger;
import net.ethanstewart.data_structures.DebugMode;
import net.ethanstewart.data_structures.InputManager;
import net.ethanstewart.data_structures.tensors.MatrixAddress;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class Day18 {
    private static final InputManager INPUT_MANAGER;
    private static final DebugMode DEBUG_MODE = DebugMode.REAL;
    private static final FluentLogger FLOGGER = FluentLogger.forEnclosingClass();
    private static final Level LOGGING_LEVEL = Level.FINE;

    static {
        try {
            INPUT_MANAGER = new InputManager(
                    """
                            R 6 (#70c710)
                            D 5 (#0dc571)
                            L 2 (#5713f0)
                            D 2 (#d2c081)
                            R 2 (#59c680)
                            D 2 (#411b91)
                            L 5 (#8ceee2)
                            U 2 (#caa173)
                            L 1 (#1b58a2)
                            U 2 (#caa171)
                            R 2 (#7807d2)
                            U 3 (#a77fa3)
                            L 2 (#015232)
                            U 2 (#7a21e3)""",
                    """
                            """,
                    new BufferedReader(new FileReader(
                            "N:\\Drive\\Programming\\Java\\AdventOfCode2023Take2\\src\\main\\java\\net\\ethanstewart\\advents\\Day18\\Input.txt"))
                            .lines().collect(Collectors.joining("\n")), DEBUG_MODE);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static double getAreaOfPolygonGivenItsOrderedVertices(MatrixAddress... vertices) {
        long total = 0;
        for (int i = 0; i < vertices.length - 1; i++) {
            total += vertices[i].getX() * vertices[i + 1].getY();
            total -= vertices[i].getY() * vertices[i + 1].getX();
        }
        return Math.abs(total / 2.0);
    }

    private static double getLatticePointsContainedWithinPolygonGivenItsVertices(MatrixAddress... vertices) {
        long total = 0;
        for (int i = 0; i < vertices.length; i++) {
            total += Math.abs(vertices[i].getX() - vertices[(i + 1) % vertices.length].getX());
            total += Math.abs(vertices[i].getY() - vertices[(i + 1) % vertices.length].getY());
        }
        return getAreaOfPolygonGivenItsOrderedVertices(vertices) + (total / 2.0) + 1;
    }

    private static MatrixAddress[] parseDirectionsIntoVertices(DebugMode debugMode) {
        String INPUT = INPUT_MANAGER.getInput();
        String[] directions = INPUT.split("\\n");
        MatrixAddress startingAddress = new MatrixAddress(0, 0);
        MatrixAddress[] vertices = new MatrixAddress[directions.length + 1];
        vertices[0] = startingAddress;
        for (int i = 0; i < directions.length; i++) {
            String[] args = directions[i].trim().split("\\s+");
            switch (debugMode) {
                case PART1 -> vertices[i + 1] = vertices[i].add(Direction.parseDirection(args[0].charAt(0))
                        .unitVector()
                        .scale(Integer.parseInt(args[1])));
                case PART2 -> vertices[i + 1] = vertices[i].add((switch (args[2].charAt(7)) {
                    case '0' -> Direction.EAST.unitVector();
                    case '1' -> Direction.SOUTH.unitVector();
                    case '2' -> Direction.WEST.unitVector();
                    case '3' -> Direction.NORTH.unitVector();
                    default -> throw new IllegalStateException("Unexpected value: " + args[2].charAt(7));
                }).scale(Integer.parseInt(args[2].substring(2, 7), 16)));
            }
        }
        return vertices;
    }

    public static long part1() {
        return (long) getLatticePointsContainedWithinPolygonGivenItsVertices(parseDirectionsIntoVertices(DebugMode.PART1));
    }

    public static long part2() {
        return (long) getLatticePointsContainedWithinPolygonGivenItsVertices(parseDirectionsIntoVertices(DebugMode.PART2));
    }

    public static void main(String[] args) {
        System.out.printf("Part 1 : %s\n", part1());
        System.out.printf("Part 2 : %s\n", part2());
    }

}
