package net.ethanstewart.data_structures.tensors;

/**
 * Addressable is the supertype for all tensor addresses
 */
public abstract class Addressable<A extends Addressable<A>> {
    private final int dimensionCount;

    protected Addressable(int dimensionCount) {
        this.dimensionCount = dimensionCount;
    }

    int getDimensionCount() {
        return dimensionCount;
    }

    abstract long getItemAtDimensionIndex(int dimensionIndex);

    abstract A makeNewAddress(long... values);

    public abstract A getOrigin();

    public abstract A add(A other);

    public abstract A scale(long scalar);


}
