package net.ethanstewart.advents.Day5;

import com.google.common.collect.ImmutableSet;
import com.google.common.truth.Truth;
import org.junit.Test;

import java.util.Arrays;

public class RangeTest {
    private static final ImmutableSet<Range> SAMPLE_RANGES = Arrays.stream("""
            50 98 2
            52 50 48
            0 15 37
            37 52 2
            39 0 15
            49 53 8
            0 11 42
            42 0 7
            57 7 4
            88 18 7
            18 25 70
            45 77 23
            81 45 19
            68 64 13
            0 69 1
            1 0 69
            60 56 37
            56 93 4""".split("\\n")).map(Range::parseRange).collect(ImmutableSet.toImmutableSet());

    @Test
    public void inversionContinuityTest() {
        SAMPLE_RANGES.forEach(range -> Truth.assertThat(range.inverse().inverse()).isEqualTo(range));
    }

    @Test
    public void discreetInversionContinuityTest() {
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            SAMPLE_RANGES.forEach(range -> Truth.assertThat(range.inverse().convert(range.convert(finalI))).isEqualTo(finalI));
        }
    }
}
