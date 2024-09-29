package net.ethanstewart.advents.Day5;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import java.util.Arrays;

public record ItemMapper(String name, ImmutableList<Range> ranges) {
    static ItemMapper parseMapper(String mapperString) {
        String name = mapperString.substring(0, mapperString.indexOf(":"));
        return new ItemMapper(name,
                Arrays.stream(
                                mapperString
                                        .substring(mapperString.indexOf(":") + 1)
                                        .trim()
                                        .split("\\n")
                        )
                        .map(Range::parseRange)
                        .collect(ImmutableList.toImmutableList()));
    }


    long convertToNextItem(long item) {
        return ranges
                .stream()
                .filter(range -> range.isInRange(item))
                .findFirst()
                .map(range -> range.convert(item))
                .orElse(item);
    }

    ImmutableSet<Long> convertToPreviousItem(long item) {
        ImmutableSet<Long> ouput = ranges
                .stream()
                .map(Range::inverse)
                .filter(range -> range.isInRange(item))
                .map(range -> range.convert(item))
                .collect(ImmutableSet.toImmutableSet());
        return ouput.isEmpty() ? ImmutableSet.of(item) : ouput;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ItemMapper && this.ranges.equals(((ItemMapper) obj).ranges);
    }
}
