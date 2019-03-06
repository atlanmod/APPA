package org.atlanmod.appa.io;

import java.nio.ByteBuffer;

public interface WritableOnByteBuffer {

    void writeOn(ByteBuffer buffer);
}
