package net.ethanstewart.data_structures.tensors;

import com.google.common.truth.Truth;
import org.junit.Test;

public class MatrixAddressTest {


    @Test
    public void constructorTest() {
        for (int x = 0; x < 1000; x++) {
            for (int y = 0; y < 1000; y++) {
                MatrixAddress address = new MatrixAddress(x, y);
                Truth.assertThat(address.getX()).isEqualTo(x);
                Truth.assertThat(address.getY()).isEqualTo(y);
            }
        }
    }

    @Test
    public void getItemAtDimensionIndexTest() {
        for (int x = 0; x < 1000; x++) {
            for (int y = 0; y < 1000; y++) {
                Truth.assertThat(new MatrixAddress(x, y)
                        .getItemAtDimensionIndex(0)).isEqualTo(x);
                Truth.assertThat(new MatrixAddress(x, y)
                        .getItemAtDimensionIndex(1)).isEqualTo(y);
            }
        }
    }

    @Test
    public void makeNewAddressTest() {
        for (int x = 0; x < 1000; x++) {
            for (int y = 0; y < 1000; y++) {
                Truth.assertThat(new MatrixAddress(
                                (long) (Math.random() * 10000),
                                (long) (Math.random() * 10000)
                        ).makeNewAddress(x, y))
                        .isEqualTo(new MatrixAddress(x, y));

            }
        }
    }

    @Test
    public void getOriginTest() {
        for (int x = 0; x < 1000; x++) {
            for (int y = 0; y < 1000; y++) {
                Truth.assertThat(new MatrixAddress(
                                (long) (Math.random() * 10000),
                                (long) (Math.random() * 10000)
                        ).makeNewAddress(x, y))
                        .isEqualTo(new MatrixAddress(x, y));

            }
        }
    }
}
