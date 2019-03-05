package org.atlanmod.appa.binaries;

import org.atlanmod.commons.primitive.Strings;

public class Header {

    public Header() {
    }

    public byte[] toBytes() {
        return Strings.toBytes("Atlanmod");
    }
}
