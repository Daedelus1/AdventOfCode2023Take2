package net.ethanstewart.data_structures.tensors;

import java.util.Objects;
import java.util.function.Function;

public class Matrix<T> extends Tensor<T, MatrixAddress> {
    public Matrix(MatrixAddress maxAddress, Iterable<T> dataIterable) {
        super(maxAddress, dataIterable);
    }

    public Matrix(MatrixAddress maxAddress, Function<MatrixAddress, T> addressValueConverter) {
        super(maxAddress, addressValueConverter);
    }

    public Matrix(String matrixString,
                  Function<Character, T> characterConverter,
                  MatrixAddress maxAddress) {
        super(maxAddress, matrixAddress -> characterConverter.apply(matrixString.charAt((int)
                ((maxAddress.getY() + 1) * matrixAddress.getY() + matrixAddress.getX())))
        );
    }
    
    public long getWidth() {
        return bounds.highBoundIncl().getX() + 1;
    }
    
    public long getHeight() {
        return bounds.highBoundIncl().getY() + 1;
    }
    
    
    public String toDisplayString(Function<T, String> itemStringConverter, String rowDelimiter, String columnDelimiter) {
        StringBuilder out = new StringBuilder();
        for (int row = 0; row < getHeight(); row++) {
            for (int col = 0; col < getWidth(); col++) {
                out.append(itemStringConverter.apply(get(new MatrixAddress(col, row))));
                out.append(rowDelimiter);
            }
            out.append(columnDelimiter);
        }
        return out.toString();
    }
}
