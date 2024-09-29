package net.ethanstewart.advents.Day5;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.ImmutableSet;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public record ItemManager(ImmutableMap<Item, ItemMapper> converters, ImmutableList<Long> seeds) {
    static ItemManager parseItemManager(String itemManagerString) {
        String[] blocks = itemManagerString.trim().split("\\n\\n+");
        return new ItemManager(getItemMapper(blocks), getSeeds(blocks));
    }

    private static ImmutableList<Long> getSeeds(String[] blocks) {
        return Arrays.stream(blocks[0]
                        .substring(blocks[0].indexOf(":") + 1)
                        .trim()
                        .split("\\s+"))
                .map(Long::parseLong)
                .collect(ImmutableList.toImmutableList());
    }

    @NotNull
    static ImmutableMap<Item, ItemMapper> getItemMapper(String[] blocks) {
        Builder<Item, ItemMapper> builder = ImmutableMap.builder();
        Arrays.stream(blocks)
                .skip(1)
                .forEach(block ->
                        builder.put(
                                Item.parseItem(block.substring(0, block.indexOf("-"))),
                                ItemMapper.parseMapper(block)
                        )
                );
        return builder.build();
    }

    boolean numberIsAtSeedIndexPart2(long index) {
        for (int i = 0; i < seeds.size() / 2; i++) {
            long min = Long.min(seeds.get(i * 2), seeds.get(i * 2) + seeds.get(i * 2 + 1));
            long max = Long.max(seeds.get(i * 2), seeds.get(i * 2) + seeds.get(i * 2 + 1));
            if (min <= index && max >= index) {
                return true;
            }
        }
        return false;
    }

    long convertSeedToLocation(long seedId) {
        long workingNumber = seedId;
        for (Item item : Item.values()) {
            if (converters.containsKey(item)) {
                workingNumber = Objects.requireNonNull(converters.get(item)).convertToNextItem(workingNumber);
            } else if (item != Item.LOCATION) {
                throw new IllegalStateException("CONVERTERS DOES NOT CONTAIN " + item);
            }
        }
        return workingNumber;
    }


    ImmutableSet<Long> convertLocationToSeed(long locationId) {
        Stream<Long> workingNumberStream = Stream.of(locationId);
        Item[] values = Item.values();
        for (int i = values.length - 1; i > 0; i--) {
            Item item = values[i - 1];
            if (converters.containsKey(item)) {
                workingNumberStream = workingNumberStream.flatMap(
                        n -> Objects.requireNonNull(converters.get(item))
                                .convertToPreviousItem(n).stream()
                );
            } else {
                throw new IllegalStateException("CONVERTERS DOES NOT CONTAIN " + item);
            }
        }
        return workingNumberStream.collect(ImmutableSet.toImmutableSet());
    }

    ImmutableSet<Long> getLocations() {
        return seeds.parallelStream().map(this::convertSeedToLocation).collect(ImmutableSet.toImmutableSet());
    }

}
