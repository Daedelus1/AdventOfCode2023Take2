package net.ethanstewart.advents.Day15;

import com.google.common.collect.ImmutableList;
import com.google.common.flogger.FluentLogger;
import net.ethanstewart.data_structures.DebugMode;
import net.ethanstewart.data_structures.InputManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.logging.Level;
import java.util.stream.Collectors;

import static net.ethanstewart.advents.Day15.Day15.Operation.REMOVE;
import static net.ethanstewart.advents.Day15.Day15.Operation.SET;

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
                            "N:\\Drive\\Programming\\Java\\AdventOfCode2023Take2\\src\\main\\java\\org\\example\\advents\\Day15\\Input.txt"))
                            .lines().collect(Collectors.joining("\n")), DEBUG_MODE);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    enum Operation {
        SET, REMOVE;

        static Operation parseOperation(String command) {
            if (command.contains("=")) {
                return SET;
            } else if (command.contains("-")) {
                return REMOVE;
            } else {
                throw new IllegalArgumentException("Unexpected input: " + command);
            }
        }
    }

    record Entry(String label, int focalPower) {

        public static Entry parseEntry(String entryString) {
            String[] parts = entryString.split("=");
            return new Entry(parts[0], Integer.parseInt(parts[1]));
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Entry && this.label.equals(((Entry) obj).label);
        }
    }

    record Command(Operation operation, String label, OptionalInt value) {
        public static Command parseCommand(String commandString) {
            Operation operation;
            String label;
            OptionalInt value;
            if (commandString.contains("=")) {
                operation = SET;
                String[] parts = commandString.split("=");
                label = parts[0];
                value = OptionalInt.of(Integer.parseInt(parts[1]));
            } else if (commandString.contains("-")) {
                operation = REMOVE;
                label = commandString.substring(0, commandString.indexOf('-'));
                value = OptionalInt.empty();
            } else {
                throw new IllegalArgumentException("Unable to parse operation; Unexpected input: " + commandString);
            }
            return new Command(operation, label, value);
        }

        public int getBoxIndex() {
            return (int) hashString(label);
        }

        public Entry toEntry() {
            return new Entry(label, value.orElse(-1));
        }

        public void execute(ImmutableList<List<Entry>> boxes) {
            List<Entry> boxOfInterest = boxes.get(getBoxIndex());
            Entry entry = toEntry();
            switch (operation) {
                case SET -> {
                    boxOfInterest.remove(entry);
                    boxOfInterest.add(entry);
                }
                case REMOVE -> {
                    boxOfInterest.remove(entry);
                }
            }
        }
    }

    private static long hashString(String input) {
        long hashValue = 0;
        for (String charString : input.split("")) {
            // magic numbers given in prompt
            hashValue = ((hashValue + charString.charAt(0)) * 17) % 256;
        }
        return hashValue;
    }

    private static long part1() {
        String input = INPUT_MANAGER.getInput().replaceAll("\\n", "");
        return Arrays.stream(input.split(","))
                .mapToLong(Day15::hashString)
                .sum();
    }

    private static long part2() {
        throw new IllegalStateException("TODO: PART 2");
    }

    public static void main(String[] args) {
        System.out.printf("Part 1 : %s\n", part1());
//        System.out.printf("Part 2 : %s\n", part2());
    }

}
