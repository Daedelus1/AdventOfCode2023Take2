package net.ethanstewart.advents.Day19;

public record Rating(long x, long m, long a, long s) {
    static Rating parseRating(String ratingString) {
        String[] parts = ratingString.substring(3, ratingString.length() - 1).split("[^0-9]+");
        return new Rating(
                Long.parseLong(parts[0]),
                Long.parseLong(parts[1]),
                Long.parseLong(parts[2]),
                Long.parseLong(parts[3])
        );
    }

    public long getValue(Category category) {
        return switch (category) {
            case X -> this.x;
            case M -> this.m;
            case A -> this.a;
            case S -> this.s;
        };
    }

    public enum Category {
        X, M, A, S
    }
}
