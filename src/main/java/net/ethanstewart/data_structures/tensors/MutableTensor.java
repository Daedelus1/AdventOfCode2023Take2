package net.ethanstewart.data_structures.tensors;

import java.util.function.Function;

public class MutableTensor<T, A extends Addressable<A>> extends Tensor<T, A> implements Tensor.Mutable<T, A> {

    public MutableTensor(A maxAddress, Iterable<T> dataIterable) {
        super(maxAddress, dataIterable);
    }

    public MutableTensor(A maxAddress, Function<A, T> addressValueConverter) {
        super(maxAddress, addressValueConverter);
    }

    public void set(A address, T value) {
        data.set((int) bounds.indexAddress(address), value);
    }
}
