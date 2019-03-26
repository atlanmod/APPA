package org.atlanmod.appa.binaries;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
class ConvertersTest {

    @Test
    void merge() {
        byte[] a = {1 , 2, 3};
        byte[] b = {4, 5, 6};
        byte[] c = {7, 8, 9};

        byte[] expected = {1 , 2, 3, 4, 5, 6, 7, 8, 9};

        byte[] actual = Converters.merge(a, b, c);

        assertThat(actual).isEqualTo(expected);
    }
}