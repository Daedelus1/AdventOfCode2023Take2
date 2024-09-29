package net.ethanstewart.advents.Day5;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.flogger.FluentLogger;
import com.google.common.truth.Truth;
import org.junit.Test;

import java.util.logging.Level;

public class ItemManagerTest {
    private static final FluentLogger FLOGGER = FluentLogger.forEnclosingClass();
    private static final Level LOGGING_LEVEL = Level.INFO;
    private static final ItemManager ITEM_MANAGER = ItemManager.parseItemManager(
            """
                    seeds: 79 14 55 13
                    
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
                    56 93 4""");

    @Test
    public void itemConversionContinuityTest() {
        for (long seed = 0; seed < 1000; seed++) {
            long location = ITEM_MANAGER.convertSeedToLocation(seed);
            ImmutableSet<Long> newSeeds = ITEM_MANAGER.convertLocationToSeed(location);
            FLOGGER.at(LOGGING_LEVEL).log("TESTING[ActualSeed: %s | SeedLocation: %s | PotentialSeeds: %s]", seed, location, newSeeds);
            Truth.assertThat(newSeeds).contains(seed);
        }
    }

    @Test
    public void convertSeedToLocationTest() {
        ImmutableMap<Integer, Integer> cases = ImmutableMap.<Integer, Integer>builder()
                .put(79, 82)
                .put(14, 43)
                .put(55, 86)
                .put(13, 35).build();
        cases.forEach((seedId, expectedLocationId) -> {
            FLOGGER.at(LOGGING_LEVEL).log("TESTING: %s", seedId);
            Truth.assertThat(ITEM_MANAGER.convertSeedToLocation(seedId)).isEqualTo(expectedLocationId);
        });
    }

    @Test
    public void convertLocationTest() {
        ImmutableMap<Long, Long> cases = ImmutableMap.<Long, Long>builder()
                .put(82L, 79L)
                .put(43L, 14L)
                .put(86L, 55L)
                .put(35L, 13L).build();
        cases.forEach((seedId, expectedLocationId) -> {
            FLOGGER.at(LOGGING_LEVEL).log("TESTING: %s", seedId);
            Truth.assertThat(ITEM_MANAGER.convertLocationToSeed(seedId)).contains(expectedLocationId);
        });
    }

}
