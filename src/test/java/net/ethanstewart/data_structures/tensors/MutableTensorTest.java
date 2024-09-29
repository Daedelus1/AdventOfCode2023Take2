package net.ethanstewart.data_structures.tensors;

import com.google.common.collect.ImmutableSet;
import com.google.common.truth.Truth;
import org.junit.Test;

import java.util.function.Function;

public class MutableTensorTest {
    @Test
    public void getTest() {
        record TestCase(Function<MatrixAddress, Long> matrixAddressConverter, MatrixAddress maxAddress) {
        }
        ImmutableSet<TestCase> cases = ImmutableSet.<TestCase>builder()
                .add(new TestCase(
                        a -> 50 * a.getY() + a.getX(),
                        new MatrixAddress(50, 50)
                ))
                .build();
        cases.forEach(testCase -> {
            Matrix<Long> matrix = new Matrix<>(testCase.maxAddress, testCase.matrixAddressConverter);
            new View<>(testCase.maxAddress.getOrigin(), testCase.maxAddress)
                    .getAllContainedAddresses()
                    .forEach(matrixAddress -> Truth.assertThat(matrix.get(matrixAddress))
                            .isEqualTo(testCase.matrixAddressConverter.apply(matrixAddress)));
        }
        );
    }

}