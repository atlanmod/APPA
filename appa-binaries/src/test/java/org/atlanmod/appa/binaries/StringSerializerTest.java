package org.atlanmod.appa.binaries;

import org.eclipse.emf.common.util.CommonUtil;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StringSerializerTest {

    @Test
    void testWriteString() {
        List<Byte> bytes = new ArrayList<>();

        StringSerializer serializer = new StringSerializer();
        byte[] serialized = serializer.apply("AAAA/BBBB/CCCC/AAAA");


        /*
        for (String each : StringSerializer.DELIMITERS) {
            System.out.println(each);
        }

        for(String each : StringSerializer.INTRINSIC_STRINGS) {
            System.out.println(each);
        }

        */

    }

}