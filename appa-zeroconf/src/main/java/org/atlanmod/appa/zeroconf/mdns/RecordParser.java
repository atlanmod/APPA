package org.atlanmod.appa.zeroconf.mdns;

import org.atlanmod.appa.zeroconf.io.ByteArrayBuffer;
import org.atlanmod.appa.zeroconf.names.HostName;

import java.text.ParseException;

public interface RecordParser<T extends Record> {
    T parse(NameArray name, ByteArrayBuffer buffer) throws ParseException;
}
