package org.atlanmod.appa.io;


import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;

public class ByteArrayReader {
    private final ByteBuffer bytes;

    public ByteArrayReader(ByteBuffer bytes) {
        this.bytes = bytes;
    }

    public Byte readByte() {
        return Byte.valueOf(get());
    }

    public Character readChar() {
        char value = (char) ((readByte() << 8) & 0xFF00 | readByte() & 0xFF);
        return Character.valueOf(value);
    }

    public Short readShort() {
        byte high = get();
        byte low = get();
        short value = (short) ((high << 8) & 0xFF00 | low & 0xFF);

        return Short.valueOf(value);
    }

    public Integer readInteger() {
        int value = (get() << 24) | (get() << 16) & 0xFF0000 | (get() << 8) & 0xFF00 | get() & 0xFF;

        return Integer.valueOf(value);
    }

    public int readCompressedInt() {
        int size = bytes.get(bytes.position()) >> 6 & 0x3;
        byte[] compressed = new byte[size +1];
        bytes.get(compressed);
        return Bytes.toCompressedInt(compressed);
    }

    public Long readLong() {
        long value = 0;
        for (int i = 0; i < Long.BYTES; i++) {
            value <<= Byte.SIZE;
            value |= (get() & 0xFF);
        }

        return Long.valueOf(value);
    }

    public UnsignedByte readUnsignedByte() {
        return UnsignedByte.fromByte(get());
    }

    public UnsignedInt readUnsignedInt() {
        return UnsignedInt.fromInt(this.readInteger());
    }

    public UnsignedShort readUnsignedShort() {
        return UnsignedShort.fromShort(this.readShort());
    }

    /**
     * Reads a label of a given number of characters, encoded in UTF8.
     *
     * @return A String representing the label.
     */
    public String readString() {
        UnsignedByte length = this.readUnsignedByte();
        byte[] labelBuffer = new byte[length.intValue()];
        for (int i = 0; i < labelBuffer.length; i++) {
            labelBuffer[i] = get();
        }

        String newLabel = new String(labelBuffer, StandardCharsets.UTF_8);

        return newLabel;
    }

    public Float readFload() {
        Integer representation = this.readInteger();
        return Float.intBitsToFloat(representation);
    }

    public Double readDouble() {
        Long representation = this.readLong();
        return Double.longBitsToDouble(representation);
    }

    public Boolean readBoolean() {
        return Boolean.valueOf(this.readByte() != 0);
    }

    public <T extends Number> T read(final Function<byte[], T> mapper, final int length) {
        byte[] destination = new byte[length];
        return mapper.apply(this.get(destination));
    }

    public byte[] get(byte[] destination) {
        for (int i = 0; i < destination.length; i++) {
            destination[i] = this.get();
        }
        return destination;
    }


    private byte get() {
        //assert position < bytes.limit();

        return bytes.get();
    }

}

/*
	public boolean readBoolean() throws IOException {
		return readByte() != 0;
	}

	public char readChar() throws IOException {
		return (char) ((readByte() << 8) & 0xFF00 | readByte() & 0xFF);
	}

	public short readShort() throws IOException {
		return (short) ((readByte() << 8) & 0xFF00 | readByte() & 0xFF);
	}

	public int readInt() throws IOException {
		return (readByte() << 24) | (readByte() << 16) & 0xFF0000 | (readByte() << 8) & 0xFF00 | readByte() & 0xFF;
	}


 */