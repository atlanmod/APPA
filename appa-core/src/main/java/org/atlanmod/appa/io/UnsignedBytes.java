package org.atlanmod.appa.io;

import javax.annotation.Nonnull;

public class UnsignedBytes {

    @Nonnull
    public static byte[] toBytes(UnsignedByte value) {
        byte[] bytes = new byte[Byte.BYTES];
        bytes[0]= value.byteValue();

        return bytes;

    }
}
