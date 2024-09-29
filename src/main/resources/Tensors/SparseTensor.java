package net.ethanstewart.tensors;

import com.google.common.collect.ImmutableMap;

import static com.google.common.base.Preconditions.checkArgument;

public class SparseTensor<T, A extends Addressable<A>> {
    private final ImmutableMap<A, T> addressValueMap;
    private final View<A> bounds;
    private final T valueOfVoid;

    /**
     * Precondition: bounds contains all points
     * @param pointToValueMap the mapping of every address whose values is not the void value
     * @param bounds the total area of the mapping, can extend beyond the value space
     * @param valueOfVoid the value that is skipped over
     */
    public SparseTensor(ImmutableMap<A, T> pointToValueMap, View<A> bounds, T valueOfVoid) {
        checkArgument(pointToValueMap.keySet().stream().allMatch(bounds::contains));
        this.addressValueMap = pointToValueMap;
        this.bounds = bounds;
        this.valueOfVoid = valueOfVoid;
    }

    public boolean containsAddressInBounds(A address) {
        return bounds.contains(address);
    }

    public boolean addressIsPresent(A address) {
        return bounds.contains(address) &&
                addressValueMap.containsKey(address);
    }

    public boolean valueIsPresent(T value) {
        return addressValueMap.containsValue(value);
    }

    public T get(A address) {
        return addressValueMap.getOrDefault(address, valueOfVoid);
    }


}
