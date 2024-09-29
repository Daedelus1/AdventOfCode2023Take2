package net.ethanstewart.tensors;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.StreamSupport;

public class MutableTensor<T, A extends Addressable<A>> extends Tensor<T, A> {
    
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
