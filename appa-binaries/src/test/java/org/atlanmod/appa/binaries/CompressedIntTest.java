package org.atlanmod.appa.binaries;

import org.atlanmod.appa.io.CompressedInts;
import org.junit.jupiter.api.Test;

public class CompressedIntTest {

    @Test
    void testOneByteIntReadWrite() {

        byte[] bytes = CompressedInts.toBytes((55));

    }
}
