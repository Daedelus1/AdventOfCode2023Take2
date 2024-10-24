package net.ethanstewart.advents.Day15;

import com.google.common.flogger.FluentLogger;
import net.ethanstewart.data_structures.DebugMode;
import net.ethanstewart.data_structures.InputManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class Day15 {
    private static final InputManager INPUT_MANAGER;
    private static final DebugMode DEBUG_MODE = DebugMode.REAL;
    private static final FluentLogger FLOGGER = FluentLogger.forEnclosingClass();
    private static final Level LOGGING_LEVEL = Level.FINE;

    static {
        try {
            INPUT_MANAGER = new InputManager(
                    """
                            rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7""",
                    """
                            """,
                    new BufferedReader(new FileReader(
                            "N:\\Drive\\Programming\\Java\\AdventOfCode2023Take2\\src\\main\\java\\net\\ethanstewart\\advents\\Day15\\Input.txt"))
                            .lines().collect(Collectors.joining("\n")), DEBUG_MODE);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    static long hashString(String input) {
        long hashValue = 0;
        for (String charString : input.split("")) {
            // magic numbers given in prompt
            hashValue = ((hashValue + charString.charAt(0)) * 17) % 256;
        }
        return hashValue;
    }

    public static long part1() {
        String input = INPUT_MANAGER.getInput().replaceAll("\\n", "");
        return Arrays.stream(input.split(","))
                .mapToLong(Day15::hashString)
                .sum();
    }

    public static long part2() {
        throw new IllegalStateException("TODO: PART 2");
    }

    public static void main(String[] args) {
        System.out.printf("Part 1 : %s\n", part1());
//        System.out.printf("Part 2 : %s\n", part2());
    }

}
