package net.ethanstewart.data_structures.deprecated;

import com.google.common.collect.ImmutableSet;

import java.util.stream.IntStream;

/**
 * @deprecated
 * @param startIncl Inclusive
 * @param endIncl   Inclusive
 */
@Deprecated
public record DeprecatedRegion(DeprecatedCoordinate startIncl, DeprecatedCoordinate endIncl) {
    public static DeprecatedRegion newRegion(int startXIncl, int startYIncl, int endXIncl, int endYIncl) {
        return new DeprecatedRegion(new DeprecatedCoordinate(startXIncl, startYIncl), new DeprecatedCoordinate(endXIncl, endYIncl));
    }

    public boolean contains(DeprecatedCoordinate point) {
        return point.x() >= startIncl.x() && point.x() <= endIncl.x() &&
                point.y() >= startIncl.y() && point.y() <= endIncl.y() ||
                point.x() <= startIncl.x() && point.x() >= endIncl.x() &&
                        point.y() <= startIncl.y() && point.y() >= endIncl.y();
    }

    public ImmutableSet<DeprecatedCoordinate> getPerimeter() {
        ImmutableSet.Builder<DeprecatedCoordinate> builder = ImmutableSet.builder();
        for (int x = startIncl.x() - 1; x < getWidth() + startIncl().x() + 1; x++) {
            builder.add(new DeprecatedCoordinate(x, startIncl.y() - 1));
            builder.add(new DeprecatedCoordinate(x + 1, endIncl.y() + 1));
        }
        for (int y = startIncl.y() - 1; y < getHeight() + startIncl().y() + 1; y++) {
            builder.add(new DeprecatedCoordinate(startIncl.x() - 1, y + 1));
            builder.add(new DeprecatedCoordinate(endIncl.x() + 1, y));
        }
        return builder.build();
    }

    public int getArea() {
        return (getWidth() + 1) * (getHeight() + 1);
    }

    public DeprecatedRegion shift(DeprecatedCoordinate offset) {
        return new DeprecatedRegion(startIncl.add(offset), endIncl.add(offset));
    }

    public DeprecatedRegion inverseShift(DeprecatedCoordinate offset) {
        return new DeprecatedRegion(startIncl.subtract(offset), endIncl.subtract(offset));
    }

    public ImmutableSet<DeprecatedCoordinate> allCoordinatesInRegion() {
        return IntStream.range(startIncl.y(), endIncl.y() + 1).boxed().flatMap(row ->
                        IntStream.range(startIncl.x(), endIncl.x() + 1).mapToObj(col -> new DeprecatedCoordinate(col, row)))
                .sorted().collect(ImmutableSet.toImmutableSet());
    }


    public int getWidth() {
        return Math.abs(endIncl.x() - startIncl.x());
    }

    public int getHeight() {
        return Math.abs(endIncl.y() - startIncl.y());
    }

    public String toDisplayString() {
        return "[" + startIncl.toDisplayString() + ", " + endIncl.toDisplayString() + "]";
    }

    private enum Direction {
        NORTH, SOUTH, EAST, WEST;

        public Direction rotateClockWise() {
            return switch (this) {
                case NORTH -> EAST;
                case SOUTH -> WEST;
                case EAST -> SOUTH;
                case WEST -> NORTH;
            };
        }

        public DeprecatedCoordinate pushCoordinate(DeprecatedCoordinate start, int amount) {
            return start.add((switch (this) {
                case NORTH -> new DeprecatedCoordinate(0, 1);
                case SOUTH -> new DeprecatedCoordinate(0, -1);
                case EAST -> new DeprecatedCoordinate(1, 0);
                case WEST -> new DeprecatedCoordinate(-1, 0);
            }).multiply(amount));
        }
    }
}
