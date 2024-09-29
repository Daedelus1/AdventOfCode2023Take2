package net.ethanstewart.advents.Day6;

import com.google.common.collect.ImmutableMap;
import com.google.common.truth.Truth;
import org.junit.Test;

public class RaceTest {
    @Test
    public void numIntegerPressingTimesThatBeatRecordTest() {
        ImmutableMap<Race, Integer> testCases = ImmutableMap.<Race, Integer>builder()
                .put(new Race(7, 9), 4)
                .put(new Race(15, 40), 8)
                .put(new Race(30, 200), 9)
                .build();
        testCases.forEach((race, count) -> Truth.assertThat(race.numIntegerPressingTimesThatBeatRecord()).isEqualTo(count));
    }
}
