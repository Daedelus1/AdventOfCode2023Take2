package net.ethanstewart.data_structures.tensors;

import java.util.function.Function;

public class Tensor3 <T> extends Tensor<T, Tensor3Address>{
    public Tensor3(Tensor3Address maxAddress, Iterable<T> dataIterable) {
        super(maxAddress, dataIterable);
    }

    public Tensor3(Tensor3Address maxAddress, Function<Tensor3Address, T> addressValueConverter) {
        super(maxAddress, addressValueConverter);
    }
}
