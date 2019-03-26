package org.atlanmod.appa.binaries;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class SimpleBitSetTest {

    @ParameterizedTest
    @ValueSource(ints = {8, 16, 56, 128, 256})
    void set(int size) {
        SimpleBitSet bitSet = new SimpleBitSet(size);
        for (int i = 0; i < size; i++) {
            assertThat(bitSet.get(i)).isFalse();
        }

        for (int i = 0; i < size; i++) {
            bitSet.set(i);
        }

        for (int i = 0; i < size; i++) {
            assertThat(bitSet.get(i)).isTrue();
        }
    }

    @Test
    void get() {
    }
}