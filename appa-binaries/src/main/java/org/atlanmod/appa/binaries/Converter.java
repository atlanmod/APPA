package org.atlanmod.appa.binaries;

import org.atlanmod.appa.io.ByteArrayReader;

public interface Converter {

    byte[] toBytes(Object object);

    Object toObject(ByteArrayReader reader);
}
