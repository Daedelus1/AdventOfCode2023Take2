package net.ethanstewart.advents.Day14;

public enum Tile {
    VOID, ROUND_ROCK, SQUARE_ROCK;
    
    public static Tile parseTile(char tileChar) {
        return switch (tileChar){
            case '.' -> VOID;
            case 'O' -> ROUND_ROCK;
            case '#' -> SQUARE_ROCK;
            default -> throw new IllegalStateException("Unexpected value: " + tileChar);
        };
    }
    public String toDisplayString() {
        return switch (this) {
            case VOID -> ".";
            case ROUND_ROCK -> "O";
            case SQUARE_ROCK -> "#";
        };
    }
}
