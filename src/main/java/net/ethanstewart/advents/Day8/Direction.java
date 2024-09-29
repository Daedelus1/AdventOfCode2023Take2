package net.ethanstewart.advents.Day8;

enum Direction {
    LEFT, RIGHT;

    static Direction parseDirection(String directionString) {
        return switch (directionString) {
            case "L" -> LEFT;
            case "R" -> RIGHT;
            default -> throw new IllegalArgumentException("Unexpected Value: " + directionString);
        };
    }
}
