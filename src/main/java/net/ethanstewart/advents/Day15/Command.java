package net.ethanstewart.advents.Day15;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.OptionalInt;

import static net.ethanstewart.advents.Day15.Operation.REMOVE;
import static net.ethanstewart.advents.Day15.Operation.SET;

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
        return (int) Day15.hashString(label);
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
