package org.atlanmod.appa;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.BitSet;

public class Main {

    public static void main(String[] args) {

        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[]{1,2,3, 0, 0, 0, 0, 0, 1, 122, 123});
        BitSet bitset1 = BitSet.valueOf(byteBuffer);

        // print the sets
        BitSet bitset2 = new BitSet(8);
        bitset2.set(0);
        System.out.println(Arrays.toString(bitset2.toByteArray()));

    }
}