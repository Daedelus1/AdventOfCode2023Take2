package org.example.advents.Day2;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.example.advents.Day2.Round.Color;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Game(int id, ImmutableSet<Round> rounds) {
    
    private static final Pattern ANY_NUMBER_PATTERN = Pattern.compile("[0-9]+");
    
    static Game parseGame(String line) {
        Matcher numberMatcher = ANY_NUMBER_PATTERN.matcher(line);
        int id;
        if (numberMatcher.find()) {
            id = Integer.parseInt(numberMatcher.group());
        } else {
            throw new IllegalStateException("NO MATCH FOUND");
        }
        line = line.substring(numberMatcher.end() + 2);
        return new Game(id, Arrays.stream(line.split(";")).map(Round::parseRound).collect(ImmutableSet.toImmutableSet()));
    }
    
    ImmutableMap<Color, Integer> minCubesPossible() {
        Map<Color, Integer> maxColorCounts = new HashMap<>();
        rounds.stream().map(Round::counts).flatMap(counts -> counts.entrySet().stream())
                .filter((Entry<Color, Integer> entry) -> !maxColorCounts.containsKey(entry.getKey()) || maxColorCounts.get(entry.getKey()) < entry.getValue())
                .forEach((Entry<Color, Integer> entry) -> maxColorCounts.put(entry.getKey(), entry.getValue()));
        return ImmutableMap.copyOf(maxColorCounts);
    }
    
    int getPower() {
        return minCubesPossible().values().stream().reduce((a, b) -> a * b).get();
    }
    
    
    public boolean isPossible(ImmutableMap<Color, Integer> maxCounts) {
        ImmutableMap<Color, Integer> minPossibleCounts = minCubesPossible();
        return maxCounts.entrySet().stream()
                .allMatch(entry -> minPossibleCounts.get(entry.getKey()) <= entry.getValue());
    }
    
    
}
