package net.ethanstewart.advents.Day7;

public enum Card implements Comparable<Card> {
    C_J, C_2, C_3, C_4, C_5, C_6, C_7, C_8, C_9, C_T, C_Q, C_K, C_A;

    public static Card parseCard(char cardChar) {
        return switch (cardChar) {
            case '2' -> C_2;
            case '3' -> C_3;
            case '4' -> C_4;
            case '5' -> C_5;
            case '6' -> C_6;
            case '7' -> C_7;
            case '8' -> C_8;
            case '9' -> C_9;
            case 'T' -> C_T;
            case 'J' -> C_J;
            case 'Q' -> C_Q;
            case 'K' -> C_K;
            case 'A' -> C_A;
            default -> throw new IllegalStateException("Unexpected value: " + cardChar);
        };
    }

    public String toDisplayString() {
        return switch (this) {
            case C_2 -> "2";
            case C_3 -> "3";
            case C_4 -> "4";
            case C_5 -> "5";
            case C_6 -> "6";
            case C_7 -> "7";
            case C_8 -> "8";
            case C_9 -> "9";
            case C_T -> "T";
            case C_J -> "J";
            case C_Q -> "Q";
            case C_K -> "K";
            case C_A -> "A";
        };
    }
}
