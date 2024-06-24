package org.example.advents.Day5;

import com.google.common.collect.ImmutableSet;
import org.example.advents.Day5.Range.ReverseMode;

import java.util.Arrays;

public record ItemMapper(String name, ImmutableSet<Range> ranges) {
    public static ItemMapper parseMapper(String mapperString, ReverseMode reverseMode) {
        String name = mapperString.substring(0, mapperString.indexOf(":"));
        return new ItemMapper(name,
                Arrays.stream(
                        mapperString
                                .substring(mapperString.indexOf(":") + 1)
                                .trim()
                                .split("\\n")
                        )
                        .map(Range::parseRange)
                        .map(range -> reverseMode == ReverseMode.REVERSE ? range.inverse() : range)
                        .collect(ImmutableSet.toImmutableSet()));
    }
    
    public long convertToNextItem(long item) {
        return ranges
                .stream()
                .filter(range -> range.isInRange(item))
                .findFirst()
                .map(range -> range.convert(item))
                .orElse(item);
    }
}
