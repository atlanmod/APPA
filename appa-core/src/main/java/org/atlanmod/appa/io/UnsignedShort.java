package org.atlanmod.appa.io;

import org.atlanmod.commons.Preconditions;

import java.nio.ByteBuffer;
import java.util.Objects;

/**
 * The `UnsignedShort` class allows the representation of unsigned 16-bit values.
 * It wraps a value of the primitive getType `int` in an object.
 * An object of getType `UnsignedShort` contains a single field whose getType is `int`.
 */
public class UnsignedShort extends Number implements Comparable<UnsignedShort>, WritableOnByteBuffer {
    public final static int MIN_VALUE = 0;
    public final static int MAX_VALUE = 0xFFFF;
    private final static int UNSIGNED_SHORT_MASK = 0xFFFF;
    protected final int value;

    protected UnsignedShort(int value) {
        this.value = value;
    }

    public static UnsignedShort fromShort(short value) {
        return new UnsignedShort(value & UNSIGNED_SHORT_MASK);
    }

    public static UnsignedShort fromInt(int value) {
        Preconditions.checkArgument(value >= MIN_VALUE && value <= MAX_VALUE);

        int unsigned = value & UNSIGNED_SHORT_MASK;
        return new UnsignedShort(unsigned);
    }

    public int intValue() {
        return value;
    }

    @Override
    public long longValue() {
        return value;
    }

    @Override
    public float floatValue() {
        return (float) value;
    }

    @Override
    public double doubleValue() {
        return (double) value;
    }

    /**
     * Returns the value of this `UnsignedShort` as a `short`.
     * May result in a negative value.
     */
    @Override
    public short shortValue() {
        return (short) value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnsignedShort that = (UnsignedShort) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {

        return Objects.hash(value);
    }

    @Override
    public int compareTo(UnsignedShort other) {
        return (this.value < other.value ? -1 : (this.value == other.value ? 0 : 1));
    }

    public boolean isLessThan(UnsignedShort other) {
        return value < other.value;
    }

    public boolean isZero() {
        return value == 0;
    }

    @Override
    public void writeOn(ByteBuffer buffer) {
        for (int i = (Short.BYTES - 1); i >= 0; i--) {
            buffer.put((byte) (value >>> i * Byte.SIZE));
        }
    }
}
