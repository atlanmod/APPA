package org.atlanmod.appa.io;

import javax.annotation.Nonnull;

public class UnsignedShorts {

    @Nonnull
    public static byte[] toBytes(UnsignedShort uShort) {
        byte[] bytes = new byte[Short.BYTES];
        short value = uShort.shortValue();
        final int length = Short.BYTES - 1;
        for (int i = length; i >= 0; i--) {
            bytes[i] = (byte) (value >>> i * Byte.SIZE);
        }

        return bytes;
    }
}
