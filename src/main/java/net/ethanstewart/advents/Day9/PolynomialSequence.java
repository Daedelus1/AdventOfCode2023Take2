package net.ethanstewart.advents.Day9;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import net.ethanstewart.data_structures.deprecated.DeprecatedCoordinate;

import java.util.stream.IntStream;

public record PolynomialSequence(ImmutableList<DeprecatedCoordinate> points) {

    public static PolynomialSequence parseSequence(String sequenceString, int xOffset) {
        String[] numberStrings = sequenceString.trim().split("\\s+");
        return new PolynomialSequence(IntStream.range(0, numberStrings.length)
                .mapToObj(n -> new DeprecatedCoordinate(n + xOffset, Integer.parseInt(numberStrings[n])))
                .collect(ImmutableList.toImmutableList()));
    }

    public static PolynomialSequence parseSequence(String sequenceString) {
        return parseSequence(sequenceString, 0);
    }

    public double interpolateToPoint(int x) {
        double sum = 0;
        int degree = points.size() - 1;
        for (int j = 0; j <= degree; j++) {
            double product = 1;
            for (int m = 0; m <= degree; m++) {
                if (m == j) {
                    continue;
                }
                product *= (double) (x - points.get(m).x()) / (points.get(j).x() - points.get(m).x());
            }
            sum += points.get(j).y() * product;
        }
        return sum;
    }

    public long interpolateToNextPoint() {
        return Math.round(interpolateToPoint(points.get(points.size() - 1).x() + 1));
    }

    public long interpolateToPointBeforeFirst() {
        return Math.round(interpolateToPoint(-1));
    }

    public int getDegree() {
        PolynomialSequence workingSequence = this;
        int count = 0;
        while (!workingSequence.isAllZeros()) {
            count++;
            workingSequence = workingSequence.getDerivativeSequence();
        }
        return count - 1;
    }

    PolynomialSequence getDerivativeSequence() {
        ImmutableList.Builder<DeprecatedCoordinate> builder = new Builder<>();
        for (int i = 1; i < points.size(); i++) {
            builder.add(new DeprecatedCoordinate(i, points.get(i).y() - points.get(i - 1).y()));
        }
        return new PolynomialSequence(builder.build());
    }

    PolynomialSequence append(DeprecatedCoordinate item) {
        return new PolynomialSequence(ImmutableList.<DeprecatedCoordinate>builder().addAll(this.points).add(item).build());
    }

    private boolean isAllZeros() {
        return points.stream().map(DeprecatedCoordinate::y).allMatch(y -> y == 0);
    }
}
