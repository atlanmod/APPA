package org.atlanmod.appa.io;

import org.atlanmod.commons.primitive.Bytes;
import org.atlanmod.commons.primitive.Longs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.nio.ByteBuffer;

import static org.junit.jupiter.api.Assertions.*;

class ReadWriteTest {

    private ByteArrayWriter writer;
    private ByteArrayReader reader;
    private ByteBuffer buffer;

    @BeforeEach
    void setUp() {
        buffer = ByteBuffer.allocate(1000);
        writer = new ByteArrayWriter(buffer);
        reader = new ByteArrayReader(buffer);
    }

    @ValueSource(shorts = {0, 133, Short.MIN_VALUE, Short.MAX_VALUE})
    @ParameterizedTest
    void readWriteByte() {
        Byte expected = Byte.valueOf((byte) 133);
        writer.writeByte(expected);

        buffer.flip();
        Byte actual = reader.readByte();

        assertEquals(expected, actual);
    }

    @ValueSource(shorts = {0, Short.MAX_VALUE, Short.MIN_VALUE, 133})
    @ParameterizedTest
    void readWriteShort(short i) {
        Short expected = Short.valueOf((short) i);
        writer.writeShort(expected);
        buffer.flip();
        Short actual = reader.readShort();

        assertEquals(expected, actual);
    }

    @ValueSource(ints = {0, Integer.MIN_VALUE, Integer.MAX_VALUE, 255})
    @ParameterizedTest
    void readWriteInteger(int i) {
        Integer expected = Integer.valueOf(i);
        writer.writeInteger(expected);
        buffer.flip();
        Integer actual = reader.readInteger();

        assertEquals(expected, actual);
    }

    @ValueSource(longs = {0, Long.MIN_VALUE, Long.MAX_VALUE, 255})
    @ParameterizedTest
    void readWriteLong(long i) {
        Long expected = Long.valueOf(i);
        writer.writeLong(expected);
        buffer.flip();
        Long actual = reader.readLong();

        assertEquals(expected, actual);
    }

    @ValueSource(strings = {"", "hdfkd", "ajefhksdhfksdfhkhsmkfaaa", "ééàà$$€€**"})
    @ParameterizedTest
    void readWriteString(String value) {
        String expected = value;
        writer.writeString(expected);
        buffer.flip();
        String actual = reader.readString();

        assertEquals(expected, actual);
    }


    @ValueSource(floats = {Float.MAX_VALUE, Float.MIN_VALUE, Float.MIN_NORMAL, 0})
    @ParameterizedTest
    void readWriteFloat(float value) {
        Float expected = Float.valueOf(value);
        writer.writeFloat(expected);
        buffer.flip();
        Float actual = reader.readFload();

        assertEquals(expected, actual);
    }

    @ValueSource(doubles = {Double.MAX_VALUE, Double.MIN_VALUE, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0})
    @ParameterizedTest
    void readWriteDouble(double value) {
        Double expected = Double.valueOf(value);
        writer.writeDouble(expected);
        buffer.flip();
        Double actual = reader.readDouble();

        assertEquals(expected, actual);
    }

    @Test
    void readWriteBoolean() {
        writer.writeBoolean(true);
        writer.writeBoolean(false);
        buffer.flip();

        assertTrue(reader.readBoolean());
        assertFalse(reader.readBoolean());

    }

    @Test
    void readWrite() {
        Long expected = Long.valueOf(Long.MAX_VALUE);
        writer.write(expected, Longs::toBytes);
        buffer.flip();
        Long actual = reader.read(Bytes::toLong, Long.BYTES);

        assertEquals(expected, actual);
    }

    @Test
    void readWriteUnsignedByte() {
        UnsignedByte expected = UnsignedByte.fromInt(133);
        writer.write(expected);
        buffer.flip();
        UnsignedByte actual = reader.readUnsignedByte();

        assertEquals(expected, actual);
    }

    @Test
    void readWriteUnsignedInt() {
        UnsignedInt expected = UnsignedInt.fromInt(133);
        writer.write(expected);
        buffer.flip();
        UnsignedInt actual = reader.readUnsignedInt();

        assertEquals(expected, actual);
    }

    @Test
    void readWriteUnsignedShort() {
        UnsignedShort expected = UnsignedShort.fromInt(133);
        writer.write(expected);
        buffer.flip();
        UnsignedShort actual = reader.readUnsignedShort();

        assertEquals(expected, actual);
    }

}