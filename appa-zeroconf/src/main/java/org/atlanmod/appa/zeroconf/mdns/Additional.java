package org.atlanmod.appa.zeroconf.mdns;

import org.atlanmod.appa.zeroconf.io.ByteArrayBuffer;

import java.text.ParseException;

public class Additional {
    private final Record record;

    public Additional(Record record) {
        this.record = record;
    }

    public static Additional fromByteBuffer(ByteArrayBuffer buffer) throws ParseException {
        Record record = Record.fromByteBuffer(buffer);

        return new Additional(record);
    }
}
