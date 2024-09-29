package net.ethanstewart.advents.Day7;

import com.google.common.collect.ImmutableSet;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.IntStream;

public record Round(ImmutableSet<Bid> bids) {
    public static Round parseRound(String roundString) {
        return new Round(Arrays.stream(roundString.trim().split("\\n+"))
                .map(Bid::parseBid)
                .collect(ImmutableSet.toImmutableSet()));
    }

    public int winnings() {
        Bid[] rankings = bids.stream().sorted(Comparator.comparing(Bid::hand)).toArray(Bid[]::new);
        System.out.println(Arrays.toString(rankings).replaceAll("], B", "]\nB"));
        return IntStream.range(0, rankings.length).map(i -> (i + 1) * rankings[i].bid()).sum();
    }
}
