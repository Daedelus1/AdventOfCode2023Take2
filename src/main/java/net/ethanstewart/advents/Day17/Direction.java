package net.ethanstewart.advents.Day17;

import com.google.common.collect.ImmutableSet;
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

    public Direction getInverse() {
        return switch (this) {
            case NORTH -> SOUTH;
            case SOUTH -> NORTH;
            case EAST -> WEST;
            case WEST -> EAST;
        };
    }

    public ImmutableSet<Direction> getPerpendicularDirections() {
        return switch (this) {
            case NORTH, SOUTH -> ImmutableSet.of(EAST, WEST);
            case EAST, WEST -> ImmutableSet.of(NORTH, SOUTH);
        };
    }
}
