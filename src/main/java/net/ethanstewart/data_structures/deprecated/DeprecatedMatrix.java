package net.ethanstewart.data_structures.deprecated;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * @deprecated
 * @param <T>
 */
@Deprecated
public class DeprecatedMatrix<T> {
    protected final ImmutableList<T> matrix;
    private final DeprecatedDimension matrixDimensions;

    public DeprecatedMatrix(@NotNull DeprecatedDimension matrixDimensions, @NotNull Function<DeprecatedCoordinate, T> CoordinateItemConverter) {
        this.matrixDimensions = matrixDimensions;
        List<T> tempData = new ArrayList<>(matrixDimensions.width() * matrixDimensions.height());

        matrixDimensions.toRegion().allCoordinatesInRegion().stream().sorted(DeprecatedCoordinate::compareTo).forEach(coordinate ->
                tempData.add(CoordinateItemConverter.apply(coordinate)));

        if (tempData.contains(null)) {
            throw new IllegalArgumentException("DATA CONTAINS NULL");
        }
        matrix = ImmutableList.copyOf(tempData);
    }

    public ImmutableSet<DeprecatedCoordinate> getAllPoints() {
        return this.matrixDimensions.toRegion().allCoordinatesInRegion();
    }

    public T getItemAtCoordinate(@NotNull DeprecatedCoordinate point) {
        DeprecatedRegion region = matrixDimensions.toRegion();
        if (!region.contains(point)) {
            throw new IndexOutOfBoundsException(point + "IS OUT OF BOUNDS");
        }
        return matrix.get(point.y() * matrixDimensions.width() + point.x());
    }

    public T getItemAtCoordinate(int x, int y) {
        return getItemAtCoordinate(new DeprecatedCoordinate(x, y));
    }

    public DeprecatedDimension getMatrixDimensions() {
        return matrixDimensions;
    }

    private ImmutableList<T> getMatrix() {
        return matrix;
    }


    @Override
    public boolean equals(Object other) {
        return other instanceof DeprecatedMatrix<?> &&
                this.matrixDimensions.equals(((DeprecatedMatrix<?>) other).matrixDimensions) &&
                this.matrix.equals(((DeprecatedMatrix<?>) other).getMatrix());
    }

    @Override
    public String toString() {
        return String.format("DeprecatedMatrix[Dimensions: %s | Data = %s]", matrixDimensions, matrix.toString());
    }


    public String toDisplayString(@NotNull Function<T, String> itemConverter, String delimiter) {
        StringBuilder out = new StringBuilder();
        matrix.stream().map(itemConverter).forEach(out::append);
        IntStream.range(1, this.matrixDimensions.height())
                .forEach(i -> out.insert(i * this.getMatrixDimensions().width() + i - 1, delimiter));
        return out.toString();
    }
}
