package net.ethanstewart.data_structures;

import java.math.BigInteger;
import java.util.function.Function;

public record ImmutableBitmap(BigInteger data) {
    public static ImmutableBitmap create(int numBits, Function<Integer, Boolean> bitSetter) {
        BigInteger bigInteger = BigInteger.ZERO;
        for (int i = 0; i < numBits; i++) {
            if (bitSetter.apply(i)) {
                bigInteger = bigInteger.setBit(i);
            }
        }
        return new ImmutableBitmap(bigInteger);
    }

    public boolean getBit(int index) {
        return data.testBit(index);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(data.bitLength());
        for (int i = (data.bitLength() / 4 * 4) + 3; i >= 0; i--) {
            builder.append(getBit(i) ? "1" : "0");
            if (i % 4 == 0 && i != 0) {
                builder.append(" ");
            }
        }
        return builder.toString();
    }
}
