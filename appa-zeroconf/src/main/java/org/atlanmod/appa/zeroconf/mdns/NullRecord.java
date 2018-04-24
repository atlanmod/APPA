package org.atlanmod.appa.zeroconf.mdns;

import org.atlanmod.appa.zeroconf.io.ByteArrayBuffer;
import org.atlanmod.appa.zeroconf.io.UnsignedInt;
import org.atlanmod.appa.zeroconf.names.HostName;

import java.text.ParseException;

/**
 *
 *
 */
public class NullRecord extends Record {


    public NullRecord(NameArray name) {
        super(name);
    }

    @Override
    public String toString() {
        return "NullRecord{" +
                "names=" + names +
                '}';
    }

    public static RecordParser<NullRecord> parser() {
        return new NullRecordParser();
    }

    private static class NullRecordParser implements RecordParser<NullRecord> {

        @Override
        public NullRecord parse(NameArray name, ByteArrayBuffer buffer) throws ParseException {
            QClass qclass = QClass.fromByteBuffer(buffer);
            UnsignedInt ttl = buffer.getUnsignedInt();
            int dataLength = buffer.getUnsignedShort().toInt();
            for (int i = 0; i < dataLength; i++) {
                buffer.getUnsignedByte(); // RDATA
            }

            return new NullRecord(name);
        }
    }
}
