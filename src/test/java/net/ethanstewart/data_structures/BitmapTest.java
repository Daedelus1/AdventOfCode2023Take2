package net.ethanstewart.data_structures;

import com.google.common.truth.Truth;
import org.junit.Test;

public class BitmapTest {

    @Test
    public void setBitTest() {
        final int NUM_BITS = 1000;
        MutableBitmap bitmap = new MutableBitmap(NUM_BITS);
        for (int i = 0; i < NUM_BITS; i++) {
            Truth.assertThat(bitmap.getBit(i)).isFalse();
            bitmap.setBit(i, false);
            Truth.assertThat(bitmap.getBit(i)).isFalse();
            bitmap.setBit(i, true);
            Truth.assertThat(bitmap.getBit(i)).isTrue();
            for (int j = 0; j < NUM_BITS; j++) {
                if (j == i) {
                    continue;
                }
                Truth.assertThat(bitmap.getBit(j)).isFalse();
            }
            bitmap.setBit(i, false);
            Truth.assertThat(bitmap.getBit(i)).isFalse();
        }
    }

}
