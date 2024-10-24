package net.ethanstewart.data_structures.tensors;

import org.jetbrains.annotations.NotNull;

public class Tensor3Address extends Addressable<Tensor3Address> {
    final long x;
    final long y;
    final long z;

    protected Tensor3Address(int dimensionCount) {
        super(dimensionCount);
        x = 0;
        y = 0;
        z = 0;
    }

    public Tensor3Address(long x, long y, long z) {
        super(3);
        this.x = x;
        this.y = y;
        this.z = z;
    }


    @Override
    long getItemAtDimensionIndex(int dimensionIndex) {
        return switch (dimensionIndex) {
            case 0 -> x;
            case 1 -> y;
            case 2 -> z;
            default -> throw new IllegalStateException("Unexpected value: " + dimensionIndex);
        };
    }

    @Override
    Tensor3Address makeNewAddress(long @NotNull ... values) {
        return new Tensor3Address(values[0], values[1], values[2]);
    }

    @Override
    public Tensor3Address getOrigin() {
        return new Tensor3Address(0, 0, 0);
    }

    @Override
    public Tensor3Address add(Tensor3Address other) {
        return new Tensor3Address(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    @Override
    public Tensor3Address scale(long scalar) {
        return new Tensor3Address(x * scalar, y * scalar, z * scalar);
    }
}
