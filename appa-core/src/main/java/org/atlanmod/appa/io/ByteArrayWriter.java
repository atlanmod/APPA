package org.atlanmod.appa.io;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;

public class ByteArrayWriter {
    private final ByteBuffer buffer;

    public ByteArrayWriter(ByteBuffer buffer) {
        this.buffer = buffer;
    }

    public ByteArrayWriter put(byte[] bytes) {
        buffer.put(bytes);
        return this;
    }


    public ByteArrayWriter writeUnsignedShort(UnsignedShort value) {
        return this.writeShort(value.shortValue());
    }

    public ByteArrayWriter writeShort(Short value) {
        return this.writeShort(value.shortValue());
    }

    private ByteArrayWriter writeShort(short value) {
        for (int i = (Short.BYTES - 1); i >= 0; i--) {
            buffer.put((byte) (value >>> i * Byte.SIZE));
        }

        return this;
    }

    public ByteArrayWriter writeUnsignedByte(UnsignedByte value) {
        return this.writeByte(value.byteValue());
    }

    public ByteArrayWriter writeByte(Byte value) {
        return this.writeByte(value.byteValue());
    }

    private ByteArrayWriter writeByte(byte value) {
        buffer.put(value);
        return this;
    }

    public ByteArrayWriter writeUnsignedInt(UnsignedInt value) {
        return this.writeInt(value.intValue());
    }

    public ByteArrayWriter writeInteger(Integer value) {
        return writeInt(value.intValue());
    }

    private ByteArrayWriter writeInt(int value) {
        for (int i = (Integer.BYTES - 1); i >= 0; i--) {
            buffer.put((byte) (value >>> i * Byte.SIZE));
        }

        return this;
    }

    public ByteArrayWriter writeLong(Long value) {
        return writeLong(value.longValue());
    }

    private ByteArrayWriter writeLong(long value) {
        for (int i = (Long.BYTES - 1); i >= 0; i--) {
            buffer.put((byte) (value >>> i * Byte.SIZE));
        }

        return this;
    }

    /**
     * Writes a label encoded in UTF8.
     *
     * @param label the label to write
     * @return This ByteArrayWriter
     */
    public ByteArrayWriter writeString(final String label) {
        assert label.length() < UnsignedByte.MAX_VALUE;

        byte[] arrayLabel = label.getBytes(StandardCharsets.UTF_8);
        UnsignedByte length = UnsignedByte.fromInt(arrayLabel.length);
        this.writeUnsignedByte(length);

        for (byte each : arrayLabel) {
            buffer.put(each);
        }
        return this;
    }

    public ByteArrayWriter writeFloat(final Float value) {
        return this.writeFloat(value.floatValue());
    }

    private ByteArrayWriter writeFloat(final float value) {
        int representation = Float.floatToIntBits(value);
        return this.writeInt(representation);
    }

    public ByteArrayWriter writeDouble(final Double value) {
        return this.writeDouble(value.doubleValue());
    }

    private ByteArrayWriter writeDouble(final double value) {
        long representation = Double.doubleToLongBits(value);

        return this.writeLong(representation);
    }

    public ByteArrayWriter writeBoolean(final Boolean value) {
        return this.writeBoolean(value.booleanValue());
    }

    private ByteArrayWriter writeBoolean(final boolean value) {
        return this.writeByte((byte) (value ? 1 : 0));
    }

    public <T extends Number> ByteArrayWriter write(final T value, Function<T, byte[]> mapper) {
        byte[] bytes = mapper.apply(value);
        this.buffer.put(bytes);

        return this;
    }

    public byte[] array() {
        return buffer.array();
    }


}
