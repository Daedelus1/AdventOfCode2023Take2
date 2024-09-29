package net.ethanstewart.advents.Day11;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.ethanstewart.data_structures.deprecated.DeprecatedCoordinate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public record Image(ImmutableSet<DeprecatedCoordinate> galaxyLocations) {
    public static Image parseImage(String imageString) {
        int width = imageString.indexOf("\n");
        Matcher galaxyFinder = Pattern.compile("#").matcher(imageString.replaceAll("\\n", ""));
        ImmutableSet.Builder<DeprecatedCoordinate> builder = ImmutableSet.builder();
        while (galaxyFinder.find()) {
            builder.add(DeprecatedCoordinate.fromIndex(galaxyFinder.start(), width));
        }
        return new Image(builder.build());
    }

    public ImmutableSet<Integer> getEmptyRows() {
        if (galaxyLocations.isEmpty()) {
            return ImmutableSet.of();
        }
        return IntStream.range(0, galaxyLocations.stream().mapToInt(DeprecatedCoordinate::y).max().getAsInt())
                .filter(y -> galaxyLocations.stream().map(DeprecatedCoordinate::y).noneMatch(n -> y == n))
                .boxed()
                .collect(ImmutableSet.toImmutableSet());
    }

    public ImmutableSet<Integer> getEmptyColumns() {
        if (galaxyLocations.isEmpty()) {
            return ImmutableSet.of();
        }
        return IntStream.range(0, galaxyLocations.stream().mapToInt(DeprecatedCoordinate::x).max().getAsInt())
                .filter(x -> galaxyLocations.stream().map(DeprecatedCoordinate::x).noneMatch(n -> x == n))
                .boxed()
                .collect(ImmutableSet.toImmutableSet());
    }

    public Image expand(int factor) {
        ImmutableSet<Integer> emptyRows = getEmptyRows();
        ImmutableSet<Integer> emptyCols = getEmptyColumns();
        return new Image(
                this.galaxyLocations.parallelStream()
                        .map(coordinate -> new DeprecatedCoordinate(
                                (int) (coordinate.x() + factor * emptyCols.stream().filter(n -> n < coordinate.x()).count()),
                                (int) (coordinate.y() + factor * emptyRows.stream().filter(n -> n < coordinate.y()).count())
                        )).collect(ImmutableSet.toImmutableSet()));
    }

    public long getSumOfDistanceBetweenAllGalaxies() {
        ImmutableList<DeprecatedCoordinate> orderedGalaxies = galaxyLocations.asList();
        return LongStream.range(0, orderedGalaxies.size() - 1)
                .map(a -> LongStream.range(a + 1, orderedGalaxies.size())
                        .mapToObj(b -> orderedGalaxies.get((int) a).difference(orderedGalaxies.get((int) b)))
                        .mapToLong(coordinate -> coordinate.x() + coordinate.y())
                        .sum())
                .sum();
    }
}
