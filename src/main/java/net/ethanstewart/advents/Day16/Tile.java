package net.ethanstewart.advents.Day16;

import com.google.common.collect.ImmutableSet;

import static net.ethanstewart.advents.Day16.Direction.*;

public enum Tile {
    VOID, NE_SW_MIRROR, NW_SE_MIRROR, VERTICAL_SPLITTER, HORIZONTAL_SPLITTER;

    public static Tile parseTile(char tileChar) {
        return switch (tileChar) {
            case '.' -> VOID;
            case '/' -> NE_SW_MIRROR;
            case '\\' -> NW_SE_MIRROR;
            case '-' -> HORIZONTAL_SPLITTER;
            case '|' -> VERTICAL_SPLITTER;
            default -> throw new IllegalStateException("Unexpected value: " + tileChar);
        };
    }

    public ImmutableSet<Direction> reflect(Direction startingDirection) {
        return switch (this) {
            case VOID -> ImmutableSet.of(startingDirection);
            case NE_SW_MIRROR -> ImmutableSet.of(switch (startingDirection) {
                case NORTH -> EAST;
                case SOUTH -> WEST;
                case EAST -> NORTH;
                case WEST -> SOUTH;
            });
            case NW_SE_MIRROR -> ImmutableSet.of(switch (startingDirection) {
                case NORTH -> WEST;
                case SOUTH -> EAST;
                case EAST -> SOUTH;
                case WEST -> NORTH;
            });
            case VERTICAL_SPLITTER -> switch (startingDirection) {
                case EAST, WEST -> ImmutableSet.of(NORTH, SOUTH);
                case NORTH, SOUTH -> ImmutableSet.of(startingDirection);
            };
            case HORIZONTAL_SPLITTER -> switch (startingDirection) {
                case NORTH, SOUTH -> ImmutableSet.of(EAST, WEST);
                case EAST, WEST -> ImmutableSet.of(startingDirection);
            };
        };
    }

    public String toDisplayString() {
        return switch (this) {
            case VOID -> ".";
            case NE_SW_MIRROR -> "/";
            case NW_SE_MIRROR -> "\\";
            case VERTICAL_SPLITTER -> "|";
            case HORIZONTAL_SPLITTER -> "-";
        };
    }
}
