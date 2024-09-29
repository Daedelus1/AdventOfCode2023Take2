package net.ethanstewart.data_structures.deprecated;

import com.google.common.collect.ImmutableSet;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * @deprecated
 * @param x
 * @param y
 */
@Deprecated
public record DeprecatedCoordinate(int x, int y) implements Comparable<DeprecatedCoordinate> {
    public static DeprecatedCoordinate fromIndex(int index, int width) {
        return new DeprecatedCoordinate(index % width, index / width);
    }

    public DeprecatedCoordinate add(@NotNull DeprecatedCoordinate other) {
        return new DeprecatedCoordinate(this.x + other.x, this.y + other.y);
    }

    public DeprecatedCoordinate add(int x, int y) {
        return this.add(new DeprecatedCoordinate(x, y));
    }

    public DeprecatedCoordinate subtract(@NotNull DeprecatedCoordinate other) {
        return new DeprecatedCoordinate(this.x - other.x, this.y - other.y);
    }

    public DeprecatedCoordinate subtract(int x, int y) {
        return this.subtract(new DeprecatedCoordinate(x, y));
    }

    public DeprecatedCoordinate difference(DeprecatedCoordinate other) {
        return new DeprecatedCoordinate(Math.abs(other.x - this.x), Math.abs(other.y - this.y));
    }

    public DeprecatedCoordinate difference(int x, int y) {
        return this.difference(new DeprecatedCoordinate(x, y));
    }

    public DeprecatedCoordinate multiply(int value) {
        return new DeprecatedCoordinate(this.x * value, this.y * value);
    }

    public DeprecatedCoordinate divide(int divisor) {
        return new DeprecatedCoordinate(this.x / divisor, this.y / divisor);
    }

    public DeprecatedCoordinate modulo(int modulus) {
        return new DeprecatedCoordinate(x % modulus, y % modulus);
    }

    public DeprecatedCoordinate move(DeprecatedDirection deprecatedDirection, int distance) {
        return this.add(deprecatedDirection.toUnitCoordinate().multiply(distance));
    }

    public DeprecatedCoordinate move(DeprecatedDirection deprecatedDirection) {
        return move(deprecatedDirection, 1);
    }

    public DeprecatedCoordinate toSignCoordinate() {
        return new DeprecatedCoordinate(Integer.signum(x), Integer.signum(y));
    }

    public ImmutableSet<DeprecatedCoordinate> getAdjacentCoordinates(DeprecatedDirection... deprecatedDirections) {
        return Arrays.stream(deprecatedDirections)
                .map(this::move)
                .collect(ImmutableSet.toImmutableSet());
    }

    public ImmutableSet<DeprecatedCoordinate> getAdjacentCoordinates() {
        return getAdjacentCoordinates(DeprecatedDirection.values());
    }

    public int toIndex(int width) {
        return this.y * width + this.x;
    }

    @Override
    public int compareTo(@NotNull DeprecatedCoordinate other) {
        DeprecatedCoordinate delta = this.subtract(other);
        return delta.y() != 0 ? delta.y() : delta.x();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof DeprecatedCoordinate && this.x == ((DeprecatedCoordinate) obj).x && this.y == ((DeprecatedCoordinate) obj).y;
    }

    public boolean equals(int x, int y) {
        return this.x == x && this.y == y;
    }

    public String toDisplayString() {
        return "(" + x + ", " + y + ")";
    }

}
