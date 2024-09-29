package net.ethanstewart.advents.Day10;

public enum PipeWall {
    WALL, VOID;

    public String toDisplayString() {
        return switch (this) {

            case WALL -> "#";
            case VOID -> ".";
        };
    }
}
