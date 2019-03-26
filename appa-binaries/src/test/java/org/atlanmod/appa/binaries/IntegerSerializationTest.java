package org.atlanmod.appa.binaries;

import org.atlanmod.appa.io.ByteArrayReader;
import org.atlanmod.appa.io.ByteArrayWriter;
import org.atlanmod.appa.io.CompressedInts;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

import java.nio.ByteBuffer;

public class IntegerSerializationTest {

    @ParameterizedTest
    @ValueSource(ints = {0, 40, 0x3FFFFF, 0x400000, 0x3FFFFFFF})
    void testReadWriteSimpleInteger(int expected) {
        ByteBuffer buffer = ByteBuffer.allocate(100);
        ByteArrayWriter writer = new ByteArrayWriter(buffer);

        writer.write(expected, CompressedInts::toBytes);
        ByteArrayReader reader = new ByteArrayReader(ByteBuffer.wrap(buffer.array()));
        int actual = reader.readCompressedInt();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testReadWriteMultipleString() {
        int[] expected = {0, 40, 0x3FFFFF, 0x400000, 0x3FFFFFFF};
        int[] actual = new int[expected.length];
        ByteBuffer buffer = ByteBuffer.allocate(100);
        ByteArrayWriter writer = new ByteArrayWriter(buffer);
        for (int i = 0; i < expected.length; i++) {
            writer.write(expected[i], CompressedInts::toBytes);
        }
        ByteArrayReader reader = new ByteArrayReader(ByteBuffer.wrap(buffer.array()));
        for (int i = 0; i < actual.length; i++) {
            actual[i] = reader.readCompressedInt();
        }

        assertThat(actual).isEqualTo(expected);

    }
}
