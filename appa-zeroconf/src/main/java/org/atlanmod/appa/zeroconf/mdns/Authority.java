package org.atlanmod.appa.zeroconf.mdns;

import org.atlanmod.appa.zeroconf.io.ByteArrayBuffer;

import java.text.ParseException;

public class Authority {
    private final Record record;

    public Authority(Record record) {
        this.record = record;
    }

    public static Authority fromByteBuffer(ByteArrayBuffer buffer) throws ParseException {
        Record record = Record.fromByteBuffer(buffer);

        return new Authority(record);
    }
}
