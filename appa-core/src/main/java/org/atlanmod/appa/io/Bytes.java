package org.atlanmod.appa.io;

public class Bytes {

    public static int toCompressedInt(byte[] bytes) {
        int initialByte = bytes[0];
        int code = (initialByte >> 6) & 0x3;
        switch (code) {
            case 0: {
                return initialByte;
            }
            case 1: {
                byte high = (byte) (initialByte & 0x3F);
                byte low = bytes[1];

                return  (high << 8) & 0xFF00 | low & 0xFF;
            }
            case 2: {
                byte high = (byte) (initialByte & 0x3F);
                byte middle = bytes[1];
                byte low = bytes[2];

                return (high << 16) & 0xFF0000 | (middle << 8) & 0xFF00 | low & 0xFF;
            }
            default: {
                byte highest = (byte) (initialByte & 0x3F);
                byte high = bytes[1];
                byte middle = bytes[2];
                byte low = bytes[3];

                return (highest << 24) & 0xFF000000 | (high << 16) & 0xFF0000
                        | (middle << 8) & 0xFF00 | low & 0xFF;
            }
        }
    }
}
