package net.ethanstewart.tensors;


import com.google.common.collect.ImmutableSet;

import java.util.stream.IntStream;

import static com.google.common.base.Preconditions.checkArgument;

public record View<A extends Addressable<A>>(A lowBoundIncl, A highBoundIncl) {
    public View {
        checkArgument(lowBoundIncl != null);
        checkArgument(highBoundIncl != null);
        checkArgument(lowBoundIncl.getDimensionCount() == highBoundIncl.getDimensionCount());
    }

    static <A extends Addressable<A>> View<A> create(A highBoundIncl) {
        return new View<>(highBoundIncl.getOrigin(), highBoundIncl);
    }

    public int getDimensionCount() {
        return this.lowBoundIncl.getDimensionCount();
    }

    public ImmutableSet<A> getAllContainedAddresses() {
        ImmutableSet.Builder<A> builder = ImmutableSet.builder();
        long[] numbers = new long[lowBoundIncl.getDimensionCount()];
        for (int d = 0; d < getDimensionCount(); d++) {
            numbers[d] = lowBoundIncl.getItemAtDimensionIndex(d);
        }
        iteration:
        while (true) {
            builder.add(highBoundIncl.makeNewAddress(numbers));
            for (int d = 0; d < getDimensionCount(); d++) {
                if (numbers[d] >= highBoundIncl.getItemAtDimensionIndex(d)) {
                    if (d == getDimensionCount() - 1) {
                        break iteration;
                    }
                    numbers[d] = lowBoundIncl.getItemAtDimensionIndex(d);
                    continue;
                } else {
                    numbers[d]++;
                }
                break;
            }
        }
        return builder.build();
    }

    public long indexAddress(A address) {
        checkArgument(this.contains(address));
        long out = 0;
        for (int d = getDimensionCount() - 1; d >= 0; d--) {
            out *= highBoundIncl.getItemAtDimensionIndex(d) + 1;
            out += address.getItemAtDimensionIndex(d);
        }
        return out;
    }

    public boolean contains(A address) {
        return IntStream.range(0, address.getDimensionCount())
                .noneMatch(i -> (address.getItemAtDimensionIndex(i) <
                        lowBoundIncl.getItemAtDimensionIndex(i)) ||
                        (address.getItemAtDimensionIndex(i) >
                                highBoundIncl.getItemAtDimensionIndex(i)));
    }

}
