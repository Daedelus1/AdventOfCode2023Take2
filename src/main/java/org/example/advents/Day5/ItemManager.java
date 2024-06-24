package org.example.advents.Day5;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.ImmutableSet;
import org.example.advents.Day5.Range.ReverseMode;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Stream;

public record ItemManager(ImmutableMap<Item, ItemMapper> converters, ImmutableList<Long> seeds) {
    public static ItemManager parseItemManager(String itemManagerString) {
        return parseItemManager(itemManagerString, ReverseMode.STANDARD);
    }
    
    public static ItemManager parseItemManager(String itemManagerString, ReverseMode reverseMode) {
        String[] blocks = itemManagerString.trim().split("\\n\\n+");
        return new ItemManager(getItemItemMapper(blocks, reverseMode), getSeeds(blocks));
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
    private static ImmutableMap<Item, ItemMapper> getItemItemMapper(String[] blocks, ReverseMode reverseMode) {
        Builder<Item, ItemMapper> builder = ImmutableMap.builder();
        Stream<String> blockStream = reverseMode == ReverseMode.STANDARD ? Arrays.stream(blocks) : Arrays.stream(blocks).sorted(Comparator.reverseOrder());
        blockStream.skip(1)
                .forEach(block ->
                        builder.put(
                                Item.parseItem(block.substring(0, block.indexOf("-"))),
                                ItemMapper.parseMapper(block, reverseMode)
                        )
                );
        return builder.build();
    }
    
    public long convertSeedToLocation(long seedId) {
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
    
    public ImmutableSet<Long> getLocations() {
        return seeds.parallelStream().map(this::convertSeedToLocation).collect(ImmutableSet.toImmutableSet());
    }
    
}
