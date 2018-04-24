package org.atlanmod.appa.zeroconf.mdns;

import org.atlanmod.appa.zeroconf.io.ByteArrayBuffer;
import org.atlanmod.appa.zeroconf.io.UnsignedInt;

import java.text.ParseException;

public class Answer  {
    private final Record record;

    public Answer(Record record) {
        this.record = record;
    }

    public static Answer fromByteBuffer(ByteArrayBuffer buffer) throws ParseException {
        Record record = Record.fromByteBuffer(buffer);

        return new Answer(record);
    }
}
