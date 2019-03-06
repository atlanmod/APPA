package org.atlanmod.appa.io;

public class CompressedInts {

    public static byte[] toBytes(int value) {
        if (value <= 0x3F) {
            return new byte[]{(byte) value};
        } else if (value <= 0x3FFF) {
            return new byte[]{(byte) (value >> 8 | 0x40), (byte) (value & 0xFF)};
        } else if (value <= 0x3FFFFF) {
            return new byte[]{(byte) (value >> 16 | 0x80), (byte) (value >> 8 & 0xFF), (byte) (value & 0xFF)};
        } else if (value <= 0x3FFFFFFF) {
            return new byte[]{(byte) (value >> 24 | 0xC0),
                    (byte) (value >> 16 & 0xFF),
                    (byte) (value >> 8 & 0xFF),
                    (byte) (value & 0xFF)};
        }
        else return new byte[0];
    }

    /*
    // One more byte version
    public static byte[] toBytes(int value) {
        if (value <= 0xEF) {
            return new byte[1];
        } else if (value <= 0x4000) {
            return new byte[2];
        } else if (value <= 0x200000) {
            return new byte[3];
        } else if (value <= 0x10000000) {
            return new byte[4];
        } else {
            return  new byte[5];
        }
    }
    */
}
