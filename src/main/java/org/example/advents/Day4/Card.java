package org.example.advents.Day4;

import com.google.common.collect.ImmutableSet;

import java.util.Arrays;
import java.util.HashSet;

public record Card(int id, ImmutableSet<Integer> winningNumbers, ImmutableSet<Integer> possessedNumbers) {
    public static Card parseCard(String cardString) {
        int id = Integer.parseInt(cardString.substring(cardString.indexOf(" ") + 1, cardString.indexOf(":")).trim());
        ImmutableSet.Builder<Integer> winningNumbersBuilder = ImmutableSet.builder();
        ImmutableSet.Builder<Integer> possessedNumbersBuilder = ImmutableSet.builder();
        String workingCardString = cardString.substring(cardString.indexOf(":") + 1);
        
        Arrays.stream(workingCardString.split("\\|")[0].trim().split("\\s+"))
                .map(Integer::parseInt).forEach(winningNumbersBuilder::add);
    
    
        Arrays.stream(workingCardString.split("\\|")[1].trim().split("\\s+"))
                .map(Integer::parseInt).forEach(possessedNumbersBuilder::add);
        return new Card(id, winningNumbersBuilder.build(), possessedNumbersBuilder.build());
    }
    
    public int numWins(){
        int startingLength = winningNumbers.size() + possessedNumbers.size();
        return startingLength - ImmutableSet.<Integer>builder()
                .addAll(winningNumbers)
                .addAll(possessedNumbers).build().size();
    }
    
    public int numPoints() {
        return (int) Math.pow(2, numWins() - 1);
    }
}
