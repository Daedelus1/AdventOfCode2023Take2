package net.ethanstewart.data_structures;

import net.ethanstewart.BinaryLogic;

import java.math.BigInteger;

public class MutableBitmap {
    private final byte[] bits;

    public MutableBitmap(int capacity) {
        this.bits = new byte[(capacity % 8 == 0) ? capacity / 8 : capacity / 8 + 1];
    }

    public MutableBitmap(int capacity, BigInteger value) {
        this.bits = new byte[(capacity % 8 == 0) ? capacity / 8 : capacity / 8 + 1];
    }

    public void setBit(int index, boolean bit) {
        if (bit) {
            bits[index / 8] = BinaryLogic.setBit(bits[index / 8], (byte) (index % 8));
        } else {
            bits[index / 8] = BinaryLogic.resetBit(bits[index / 8], (byte) (index % 8));
        }
    }

    public boolean getBit(int index) {
        return BinaryLogic.getBit(bits[index / 8], index % 8);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(bits.length * 8);
        for (int i = bits.length * 8 - 1; i >= 0; i--) {
            builder.append(getBit(i) ? "1" : "0");
            if (i % 4 == 0) {
                builder.append(" ");
            }
        }
        return builder.toString();
    }
}
