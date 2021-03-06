package org.atlanmod.appa.io;

import org.atlanmod.commons.Preconditions;

import java.nio.ByteBuffer;
import java.util.Objects;

/**
 * The `UnsignedInt` class allows the representation of unsigned 32-bit values.
 * It wraps a value of the primitive getType `long` in an object.
 * An object of getType `UnsignedInt` contains a single field whose getType is `long`.
 */
public class UnsignedInt extends Number implements Comparable<UnsignedInt>, WritableOnByteBuffer {
    public final static long MIN_VALUE = 0;
    public final static long MAX_VALUE = 0xffffffffL;
    private final static long UNSIGNED_INT_MASK = 0xFFFFFFFFL;
    ;
    private final long value;

    private UnsignedInt(long value) {
        this.value = value;
    }

    /**
     * Wraps an `int` value into a `UnsignedInt`.
     *
     * @param value
     * @return
     */
    public static UnsignedInt fromInt(int value) {
        return new UnsignedInt(value & UNSIGNED_INT_MASK);
    }


    public static UnsignedInt fromLong(long value) {
        Preconditions.checkArgument(value >= MIN_VALUE && value <= MAX_VALUE);

        long unsigned = value & UNSIGNED_INT_MASK;
        return new UnsignedInt(unsigned);
    }

    /**
     * Compares this object to the specified object.
     * The result is `true` if and only if the argument is not null and is an `UnsignedInt` object that contains
     * the same `long` value as this object.
     *
     * @param obj the object to compare with.
     * @return `true` if the objects are the same; `false` otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UnsignedInt that = (UnsignedInt) obj;
        return value == that.value;
    }

    /**
     * Returns a hash code for this `UnsignedInt`.
     *
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public int intValue() {
        return (int) value;
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

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public int compareTo(UnsignedInt other) {
        return (this.value < other.value ? -1 : (this.value == other.value ? 0 : 1));
    }

    @Override
    public void writeOn(ByteBuffer buffer) {
        for (int i = (Integer.BYTES - 1); i >= 0; i--) {
            buffer.put((byte) (value >>> i * Byte.SIZE));
        }
    }
}
