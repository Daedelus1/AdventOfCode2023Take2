package net.ethanstewart.data_structures.tensors;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Tensor<T, A extends Addressable<A>> {
    protected final List<T> data;
    protected final View<A> bounds;
    
    public Tensor(A maxAddress, Iterable<T> dataIterable) {
        this.bounds = new View<>(maxAddress.getOrigin(), maxAddress);
        this.data = StreamSupport.stream(dataIterable.spliterator(), false)
                .collect(Collectors.toList());
    }
    
    public Tensor(A maxAddress, Function<A, T> addressValueConverter) {
        this.bounds = new View<>(maxAddress.getOrigin(), maxAddress);
        this.data = bounds.getAllContainedAddresses()
                .stream()
                .sorted(Comparator.comparing(bounds::indexAddress))
                .map(addressValueConverter)
                .collect(Collectors.toList());
    }
    
    
    public T get(A address) {
        return data.get((int) bounds.indexAddress(address));
    }
    
    public ImmutableSet<A> getAllAddresses() {
        return bounds.getAllContainedAddresses();
    }
    
    public SparseTensor<T, A> toSparseTensor(T valueOfVoid) {
        ImmutableMap.Builder<A, T> builder = ImmutableMap.builder();
        bounds.getAllContainedAddresses().forEach(address -> {
            T value = data.get((int) bounds.indexAddress(address));
            if (!value.equals(valueOfVoid)) {
                builder.put(address, value);
            }
        });
        return new SparseTensor<>(
                builder.build(),
                bounds,
                valueOfVoid
        );
    }

    public ImmutableList<T> getData() {
        return ImmutableList.copyOf(data);
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Tensor &&
               this.bounds.equals(((Tensor<?, ?>) obj).bounds) &&
               this.data.equals(((Tensor<?, ?>) obj).data);
    }
    
    @Override
    public String toString() {
        return String.format("[Bounds: %s, Data: %s]", bounds, data);
    }
}
