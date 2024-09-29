package net.ethanstewart.advents.Day5;

import com.google.common.collect.ImmutableSet;
import com.google.common.flogger.FluentLogger;
import com.google.common.truth.Truth;
import org.junit.Test;

import java.util.logging.Level;

public class ItemMapperTest {
    private static final FluentLogger FLOGGER = FluentLogger.forEnclosingClass();
    private static final Level LOGGING_LEVEL = Level.INFO;
    private static final ImmutableSet<ItemMapper> SAMPLE_MAPPERS =
            ImmutableSet.copyOf(ItemManager.getItemMapper("""
                    seed-to-soil map:
                    50 98 2
                    52 50 48
                    
                    soil-to-fertilizer map:
                    0 15 37
                    37 52 2
                    39 0 15
                    
                    fertilizer-to-water map:
                    49 53 8
                    0 11 42
                    42 0 7
                    57 7 4
                    
                    water-to-light map:
                    88 18 7
                    18 25 70
                    
                    light-to-temperature map:
                    45 77 23
                    81 45 19
                    68 64 13
                    
                    temperature-to-humidity map:
                    0 69 1
                    1 0 69
                    
                    humidity-to-location map:
                    60 56 37
                    56 93 4""".split("\\n\\n+")).values());

    @Test
    public void discreetInversionContinuityTest() {
        for (long i = 0; i < 1000; i++) {
            long finalI = i;
            FLOGGER.at(LOGGING_LEVEL).log("TESTING: %s", finalI);
            SAMPLE_MAPPERS.forEach(mapper -> Truth.assertThat(mapper.convertToPreviousItem(mapper.convertToNextItem(finalI))).contains(finalI));
        }
    }

}
