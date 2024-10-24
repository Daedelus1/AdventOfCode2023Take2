package net.ethanstewart.advents.Day18;

import net.ethanstewart.data_structures.tensors.MatrixAddress;

public enum Direction {
    NORTH, SOUTH, EAST, WEST;

    public static Direction parseDirection(char directionChar) {
        return switch (directionChar) {
            case 'U' -> NORTH;
            case 'D' -> SOUTH;
            case 'L' -> WEST;
            case 'R' -> EAST;
            default -> throw new IllegalStateException("Unexpected value: " + directionChar);
        };
    }

    public MatrixAddress unitVector() {
        return switch (this) {
            case NORTH -> new MatrixAddress(0, -1);
            case SOUTH -> new MatrixAddress(0, 1);
            case EAST -> new MatrixAddress(1, 0);
            case WEST -> new MatrixAddress(-1, 0);
        };
    }

    public Direction getInverse() {
        return switch (this) {
            case NORTH -> SOUTH;
            case SOUTH -> NORTH;
            case EAST -> WEST;
            case WEST -> EAST;
        };
    }
}
