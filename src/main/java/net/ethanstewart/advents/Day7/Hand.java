package net.ethanstewart.advents.Day7;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultiset;
import net.ethanstewart.data_structures.DebugMode;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public record Hand(ImmutableList<Card> cardList) implements Comparable<Hand> {
    public static final Hand FIVE_JOKERS_EDGE_CASE = new Hand(ImmutableList.of(Card.C_J, Card.C_J, Card.C_J, Card.C_J, Card.C_J));
    private final static HashMap<Hand, Integer> numericValueTabulation = new HashMap<>();

    public static Hand parseHand(String handString) {
        return new Hand(Arrays.stream(handString.trim().split(""))
                .map(str -> str.charAt(0))
                .map(Card::parseCard)
                .collect(ImmutableList.toImmutableList()));
    }


    public HandType getHandType() {
        if (Day7.SORTING_MODE == DebugMode.PART1) {
            return HandType.getHandTypePart1(this);
        }
        return HandType.getHandTypePart2(this);
    }

    public int numericValue() {
        if (numericValueTabulation.containsKey(this)) {
            return numericValueTabulation.get(this);
        }
        int out = 0;
        for (Card card : this.cardList) {
            out *= 16;
            out += card.ordinal();
        }
        numericValueTabulation.put(this, out);
        return out;
    }


    @Override
    public int compareTo(@NotNull Hand other) {
        //+ if this is bigger
        int typeComparison = this.getHandType().compareTo(other.getHandType());
        if (typeComparison != 0) {
            return typeComparison;
        }

        return Integer.compare(this.numericValue(), other.numericValue());
    }

    @Override
    public String toString() {
        return "Hand" + this.cardList;
    }

    public String toDisplayString() {
        return this.cardList.stream().map(Card::toDisplayString).collect(Collectors.joining());
    }

    enum HandType implements Comparable<HandType> {
        HIGH_CARD,
        ONE_PAIR,
        TWO_PAIR,
        THREE_OF_A_KIND,
        FULL_HOUSE,
        FOUR_OF_A_KIND,
        FIVE_OF_A_KIND;

        private static final ImmutableMap<Collection<Long>, HandType> identificationTable = ImmutableMap.<Collection<Long>, HandType>builder()
                .put(ImmutableMultiset.<Long>builder().add(1L, 1L, 1L, 1L, 1L).build(), HIGH_CARD)
                .put(ImmutableMultiset.<Long>builder().add(1L, 1L, 1L, 2L).build(), ONE_PAIR)
                .put(ImmutableMultiset.<Long>builder().add(1L, 2L, 2L).build(), TWO_PAIR)
                .put(ImmutableMultiset.<Long>builder().add(2L, 3L).build(), FULL_HOUSE)
                .put(ImmutableMultiset.<Long>builder().add(3L, 1L, 1L).build(), THREE_OF_A_KIND)
                .put(ImmutableMultiset.<Long>builder().add(4L, 1L).build(), FOUR_OF_A_KIND)
                .put(ImmutableMultiset.<Long>builder().add(5L).build(), FIVE_OF_A_KIND)
                .build();

        public static HandType getHandTypePart1(Hand hand) {
            return identificationTable.get(
                    ImmutableMultiset.copyOf(hand.cardList.stream()
                            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                            .values()
                            .stream()
                            .collect(ImmutableMultiset.toImmutableMultiset())));
        }

        public static HandType getHandTypePart2(Hand hand) {
            if (hand.equals(FIVE_JOKERS_EDGE_CASE)) {
                return FIVE_OF_A_KIND;
            }
            long numJokers = hand.cardList.stream().filter(card -> card == Card.C_J).count();
            Long[] counts = hand.cardList.stream()
                    .filter(card -> card != Card.C_J)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                    .values().stream().sorted(Comparator.reverseOrder()).toArray(Long[]::new);
            counts[0] += numJokers;
            return identificationTable.get(ImmutableMultiset.copyOf(counts));
        }
    }
}
