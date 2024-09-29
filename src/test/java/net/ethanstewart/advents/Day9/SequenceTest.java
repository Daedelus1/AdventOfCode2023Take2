package net.ethanstewart.advents.Day9;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.truth.Truth;
import net.ethanstewart.data_structures.deprecated.DeprecatedCoordinate;
import org.junit.Test;

public class SequenceTest {
    @Test
    public void interpolationTest() {
        record TestCase(PolynomialSequence sequence, int xInput, int expectedOutput) {
        }
        ImmutableSet<TestCase> cases = ImmutableSet.<TestCase>builder()
                .add(new TestCase(PolynomialSequence.parseSequence("1 3 6 10 15 21"), 6, 28))
                .add(new TestCase(PolynomialSequence.parseSequence("0 3 6 9 12 15"), 6, 18))
                .add(new TestCase(PolynomialSequence.parseSequence("10 13 16 21 30 45"), 6, 68))
                .build();
        cases.forEach(testCase -> Truth.assertThat(testCase.sequence.interpolateToPoint(testCase.xInput))
                .isEqualTo(testCase.expectedOutput));
    }

    @Test
    public void parsingTest() {
        ImmutableMap<String, PolynomialSequence> testCases = ImmutableMap.<String, PolynomialSequence>builder()
                .put("0 3 6 9 12 15", new PolynomialSequence(ImmutableList.<DeprecatedCoordinate>builder()
                        .add(
                                new DeprecatedCoordinate(0, 0),
                                new DeprecatedCoordinate(1, 3),
                                new DeprecatedCoordinate(2, 6),
                                new DeprecatedCoordinate(3, 9),
                                new DeprecatedCoordinate(4, 12),
                                new DeprecatedCoordinate(5, 15)
                        )
                        .build()))
                .put("1 3 6 10 15 21", new PolynomialSequence(ImmutableList.<DeprecatedCoordinate>builder()
                        .add(
                                new DeprecatedCoordinate(0, 1),
                                new DeprecatedCoordinate(1, 3),
                                new DeprecatedCoordinate(2, 6),
                                new DeprecatedCoordinate(3, 10),
                                new DeprecatedCoordinate(4, 15),
                                new DeprecatedCoordinate(5, 21)
                        )
                        .build()))
                .put("10 13 16 21 30 45", new PolynomialSequence(ImmutableList.<DeprecatedCoordinate>builder()
                        .add(
                                new DeprecatedCoordinate(0, 10),
                                new DeprecatedCoordinate(1, 13),
                                new DeprecatedCoordinate(2, 16),
                                new DeprecatedCoordinate(3, 21),
                                new DeprecatedCoordinate(4, 30),
                                new DeprecatedCoordinate(5, 45)
                        )
                        .build()))
                .build();
        testCases.forEach((string, sequence) -> Truth.assertThat(PolynomialSequence.parseSequence(string)).isEqualTo(sequence));
    }

    @Test
    public void derivativeTest() {
        ImmutableMap<PolynomialSequence, PolynomialSequence> testCases = ImmutableMap.<PolynomialSequence, PolynomialSequence>builder()
                .put(PolynomialSequence.parseSequence("0 3 6 9 12 15"),
                        PolynomialSequence.parseSequence("3 3 3 3 3", 1))
                .put(PolynomialSequence.parseSequence("1 3 6 10 15 21"),
                        PolynomialSequence.parseSequence("2 3 4 5 6", 1))
                .put(PolynomialSequence.parseSequence("10 13 16 21 30 45"),
                        PolynomialSequence.parseSequence("3 3 5 9 15", 1))
                .build();
        testCases.forEach((start, expectedDerivative) ->
                Truth.assertThat(start.getDerivativeSequence()).isEqualTo(expectedDerivative));
    }

    @Test
    public void degreeTest() {
        ImmutableMap<PolynomialSequence, Integer> testCases = ImmutableMap.<PolynomialSequence, Integer>builder()
                .put(PolynomialSequence.parseSequence("0 3 6 9 12 15"), 1)
                .put(PolynomialSequence.parseSequence("1 3 6 10 15 21"), 2)
                .put(PolynomialSequence.parseSequence("10 13 16 21 30 45"), 3)
                .build();
        testCases.forEach((sequence, degree) -> Truth.assertThat(sequence.getDegree()).isEqualTo(degree));
    }

}
