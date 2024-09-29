package net.ethanstewart.tensors;

/**
 * Addressable is the supertype for all tensor addresses
 */
abstract class Addressable<A extends Addressable<A>> {
    private final int dimensionCount;

    protected Addressable(int dimensionCount) {
        this.dimensionCount = dimensionCount;
    }

    int getDimensionCount() {
        return dimensionCount;
    }

    abstract long getItemAtDimensionIndex(int dimensionIndex);

    abstract A makeNewAddress(long... values);

    abstract A getOrigin();

}
