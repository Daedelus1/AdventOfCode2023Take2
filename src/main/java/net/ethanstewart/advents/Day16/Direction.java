package net.ethanstewart.advents.Day16;

import net.ethanstewart.data_structures.tensors.MatrixAddress;

public enum Direction {
    NORTH, SOUTH, EAST, WEST;

    public MatrixAddress unitVector() {
        return switch (this) {
            case NORTH -> new MatrixAddress(0, -1);
            case SOUTH -> new MatrixAddress(0, 1);
            case EAST -> new MatrixAddress(1, 0);
            case WEST -> new MatrixAddress(-1, 0);
        };
    }
}
