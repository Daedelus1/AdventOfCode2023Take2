package net.ethanstewart.advents.Day1;

import com.google.common.collect.ImmutableMap;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day1 {
    private static final String INPUT_STRING;
    private static final String NUMBER_REGEX = "0|1|2|3|4|5|6|7|8|9";
    private static final ImmutableMap<String, Integer> NUMERICIZER = ImmutableMap.<String, Integer>builder()
            .put("1", 1)
            .put("2", 2)
            .put("3", 3)
            .put("4", 4)
            .put("5", 5)
            .put("6", 6)
            .put("7", 7)
            .put("8", 8)
            .put("9", 9)
            .put("one", 1)
            .put("two", 2)
            .put("three", 3)
            .put("four", 4)
            .put("five", 5)
            .put("six", 6)
            .put("seven", 7)
            .put("eight", 8)
            .put("nine", 9)
            .build();
    private static final String NUMBER_AND_NAME_REGEX = String.join("|", NUMERICIZER.keySet());

    static {
        try {
            INPUT_STRING = new BufferedReader(new FileReader("N:\\Drive\\Programming\\Java\\AdventOfCode2023Take2\\src\\main\\java\\net\\ethanstewart\\advents\\Day1\\Input.txt"))
                    .lines().collect(Collectors.joining("\n"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static String replaceRange(String seed, String replacement, int startIndexIncl, int endIndexExcl) {
        return seed.substring(0, startIndexIncl) +
                replacement +
                seed.substring(endIndexExcl);
    }

    private static String reverseString(String seed) {
        StringBuilder out = new StringBuilder(seed.length());
        for (int i = seed.length() - 1; i >= 0; i--) {
            out.append(seed.charAt(i));
        }
        return out.toString();
    }


    private static int findCalibrationValueOfLine(String line, String reversableRegex) {
        Matcher forwardMatcher = Pattern.compile(reversableRegex).matcher(line);
        Matcher reverseMatcher = Pattern.compile(reverseString(reversableRegex)).matcher(reverseString(line));
        if (reverseMatcher.find() && forwardMatcher.find()) {
            return (NUMERICIZER.get(forwardMatcher.group()) * 10) + NUMERICIZER.get(reverseString(reverseMatcher.group()));
        } else {
            throw new IllegalStateException("NO MATCH FOUND IN STRING \"" + line + "\" USING REGEX `" + reversableRegex + "`");
        }
    }

    public static int part1() {
        return Arrays.stream(INPUT_STRING.split("\\n")).mapToInt(line -> Day1.findCalibrationValueOfLine(line, NUMBER_REGEX)).sum();
    }

    public static int part2() {
        return Arrays.stream(INPUT_STRING.split("\\n")).mapToInt(line -> Day1.findCalibrationValueOfLine(line, NUMBER_AND_NAME_REGEX)).sum();
    }

    public static void main(String[] args) {
        System.out.printf("Part 1: %s\n", part1());
        System.out.printf("Part 2: %s\n", part2());
    }
}
