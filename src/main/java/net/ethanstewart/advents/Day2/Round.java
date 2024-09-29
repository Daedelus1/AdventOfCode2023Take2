package net.ethanstewart.advents.Day2;

import com.google.common.collect.ImmutableMap;

import java.util.regex.Pattern;

public record Round(ImmutableMap<Color, Integer> counts) {
    private static final Pattern ANY_NUMBER_PATTERN = Pattern.compile("[0-9]+");

    static Round parseRound(String roundString) {
        ImmutableMap.Builder<Color, Integer> builder = ImmutableMap.builder();
        String[] hands = roundString.split(",\\s*");
        for (String hand : hands) {
            String[] parts = hand.trim().split("\\s+");
            builder.put(Color.parseColor(parts[1]), Integer.parseInt(parts[0]));
        }
        return new Round(builder.build());
    }


    enum Color {
        RED, GREEN, BLUE;

        static Color parseColor(String colorStr) {
            return switch (colorStr.toLowerCase()) {
                case "red" -> RED;
                case "blue" -> BLUE;
                case "green" -> GREEN;
                default -> throw new IllegalArgumentException(String.format("UNEXPECTED COLOR_STR: \"%s\"", colorStr));
            };
        }
    }
}
