package org.example.advents.Day3;

public enum Tile {
    GEAR, SYMBOL, VOID, ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE;
    
    public boolean isNumber(){
        return switch (this) {
            case ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, ZERO -> true;
            default -> false;
        };
    }
    
    public boolean isEmpty() {
        return this == VOID;
    }
    public boolean isGear() {
        return this == GEAR;
    }
    
    public boolean isSymbol() {
        return this == SYMBOL || this == GEAR;
    }
    public int numericize() {
        return switch (this) {
            case ZERO -> 0;
            case ONE -> 1;
            case TWO -> 2;
            case THREE -> 3;
            case FOUR -> 4;
            case FIVE -> 5;
            case SIX -> 6;
            case SEVEN -> 7;
            case EIGHT -> 8;
            case NINE -> 9;
            default -> throw new IllegalStateException("Unexpected value: " + this);
        };
    }
    
    public static Tile parseTile (char tileChar) {
        return switch (tileChar) {
            case '.' -> VOID;
            case '0' -> ZERO;
            case '1' -> ONE;
            case '2' -> TWO;
            case '3' -> THREE;
            case '4' -> FOUR;
            case '5' -> FIVE;
            case '6' -> SIX;
            case '7' -> SEVEN;
            case '8' -> EIGHT;
            case '9' ->  NINE;
            case '*' -> GEAR;
            default -> SYMBOL;
        };
    }
    
    public String toDisplayString() {
        return switch (this) {
            case SYMBOL -> "S";
            case VOID -> ".";
            case ZERO -> "0";
            case ONE -> "1";
            case TWO -> "2";
            case THREE -> "3";
            case FOUR -> "4";
            case FIVE -> "5";
            case SIX -> "6";
            case SEVEN -> "7";
            case EIGHT -> "8";
            case NINE -> "9";
            case GEAR -> "G";
        };
    }
}
