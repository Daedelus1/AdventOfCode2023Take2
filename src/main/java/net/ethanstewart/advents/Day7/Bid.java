package net.ethanstewart.advents.Day7;

public record Bid(Hand hand, int bid) {
    static Bid parseBid(String bidString) {
        String[] parts = bidString.trim().split("\\s+");
        return new Bid(Hand.parseHand(parts[0]), Integer.parseInt(parts[1]));
    }

    @Override
    public String toString() {
        return String.format("Bid[Hand=%s, Bid=%s, HandType=%s]", hand.toDisplayString(), bid, hand.getHandType());
    }
}
