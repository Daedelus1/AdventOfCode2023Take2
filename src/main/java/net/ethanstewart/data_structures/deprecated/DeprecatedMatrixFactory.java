package net.ethanstewart.data_structures.deprecated;

import java.util.Arrays;
import java.util.function.Function;

/**
 * @deprecated
 */
public class DeprecatedMatrixFactory {
    public static <T> DeprecatedMatrix<T> stringToMatrix(String seed, String delimiter, Function<Character, T> converter) {
        String[] temp = seed.split(delimiter);
        if (Arrays.stream(temp).anyMatch(line -> line.trim().length() != temp[0].trim().length())) {
            throw new IllegalArgumentException("STRING DOES NOT HAVE CONSISTENT WIDTH");
        }
        DeprecatedDimension matrixDimension = new DeprecatedDimension(temp[0].length(), temp.length);
        return new DeprecatedMatrix<>(matrixDimension, point ->
                converter.apply(temp[point.y()].charAt(point.x())));

    }
}
