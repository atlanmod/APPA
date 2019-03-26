package org.atlanmod.appa.io;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class CompressedIntTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 0x3F})
    void testOneByteCompressedIntEncoding() {
        int expected = 12;
        byte[] bytes = CompressedInts.toBytes(expected);
        int actual = Bytes.toCompressedInt(bytes);

        assertThat(bytes).hasSize(1);
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(ints = {0x40, 0x3FFF})
    void testTwoBytesCompressedIntEncoding() {
        int expected =  16383;
        byte[] bytes = CompressedInts.toBytes(expected);

        int actual = Bytes.toCompressedInt(bytes);
        assertThat(bytes).hasSize(2);
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(ints = {0x4000, 0x3FFFFF})
    void testThreeBytesCompressedIntEncoding(int expected) {
        byte[] bytes = CompressedInts.toBytes(expected);

        int actual = Bytes.toCompressedInt(bytes);
        assertThat(bytes).hasSize(3);
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(ints = {0x400000, 0x3FFFFFFF})
    void testFourBytesCompressedIntEncoding(int expected) {
        byte[] bytes = CompressedInts.toBytes(expected);

        int actual = Bytes.toCompressedInt(bytes);
        assertThat(bytes).hasSize(4);
        assertThat(actual).isEqualTo(expected);
    }

}