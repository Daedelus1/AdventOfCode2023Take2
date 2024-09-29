package net.ethanstewart.data_structures;

public record InputManager(String testInputPart1, String testInputPart2, String realInput, DebugMode mode) {
    public String getInput(DebugMode mode) {
        return switch (mode) {
            case PART1 -> testInputPart1;
            case PART2 -> testInputPart2;
            case REAL -> realInput;
        };
    }

    public String getInput() {
        return getInput(mode);
    }
}
