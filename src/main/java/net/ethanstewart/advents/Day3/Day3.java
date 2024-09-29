package net.ethanstewart.advents.Day3;

import com.google.common.collect.ImmutableSet;
import net.ethanstewart.data_structures.DebugMode;
import net.ethanstewart.data_structures.InputManager;
import net.ethanstewart.data_structures.deprecated.DeprecatedCoordinate;
import net.ethanstewart.data_structures.deprecated.DeprecatedRegion;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.stream.Collectors;

public class Day3 {
    private static final InputManager INPUT_MANAGER;
    private static final DebugMode DEBUG_MODE = DebugMode.REAL;

    static {
        try {
            INPUT_MANAGER = new InputManager(
                    """
                            12.......*..
                            +.........34
                            .......-12..
                            ..78........
                            ..*....60...
                            78.........9
                            .5.....23..$
                            8...90*12...
                            ............
                            2.2......12.
                            .*.........*
                            1.1..503+.56""",
                    """
                            """,
                    new BufferedReader(new FileReader(
                            "N:\\Drive\\Programming\\AdventOfCode2023Take2\\src\\main\\java\\org\\example\\advents\\Day3\\Input.txt"))
                            .lines().collect(Collectors.joining("\n")), DEBUG_MODE);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static int part1() {
        Engine engine = Engine.parseEngine(INPUT_MANAGER.getInput());
        return engine.getAllNumberRegions()
                .stream()
                .filter(region -> region.getPerimeter().stream()
                        .filter(coordinate -> engine.getMatrixDimensions().toRegion().contains(coordinate))
                        .map(engine::getItemAtCoordinate).anyMatch(Tile::isSymbol))
                .mapToInt(engine::readNumberInRegion)
                .sum();
    }

    private static int part2() {
        Engine engine = Engine.parseEngine(INPUT_MANAGER.getInput());
        int[][] counts = new int[engine.getMatrixDimensions().height()][engine.getMatrixDimensions().height()];
        ImmutableSet<DeprecatedRegion> allNumberRegions = engine.getAllNumberRegions();
        allNumberRegions
                .stream()
                .map(DeprecatedRegion::getPerimeter)
                .flatMap(ImmutableSet::stream)
                .filter(coordinate -> engine.getMatrixDimensions().toRegion().contains(coordinate))
                .forEach(coordinate -> counts[coordinate.y()][coordinate.x()]++);
        ImmutableSet<DeprecatedCoordinate> allGearLocations = engine.getAllPoints()
                .stream()
                .filter(coordinate -> engine.getItemAtCoordinate(coordinate).isGear())
                .filter(coordinate -> counts[coordinate.y()][coordinate.x()] == 2)
                .collect(ImmutableSet.toImmutableSet());
        return allGearLocations.stream()
                .map(gearLocation -> allNumberRegions
                        .stream()
                        .filter(region -> region
                                .getPerimeter()
                                .contains(gearLocation)))
                .mapToInt(regions -> regions
                        .mapToInt(engine::readNumberInRegion)
                        .reduce((a, b) -> a * b)
                        .getAsInt())
                .sum();
    }

    /*
    XXXX
    xxxX
    xxxx
    xXXX
     */
    public static void main(String[] args) {
//        System.out.printf("Part 1 : %s", part1());
        System.out.printf("Part 2 : %s", part2());
    }
}
