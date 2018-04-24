package org.atlanmod.appa.zeroconf.mdns;

import org.atlanmod.appa.zeroconf.io.ByteArrayBuffer;
import org.atlanmod.appa.zeroconf.io.UnsignedInt;
import org.atlanmod.appa.zeroconf.names.HostName;

import java.text.ParseException;
import java.util.List;

public class TextRecord extends NormalRecord {
    private List<String> labels;

    private TextRecord(NameArray name, QClass qclass, UnsignedInt ttl, List<String> labels) {
        super(name, qclass, ttl);
        this.labels = labels;
    }

    public static RecordParser<TextRecord> parser() {
        return new TextRecordParser();
    }

    private static class TextRecordParser extends NormalRecordParser<TextRecord> {

        private List<String> labels;

        protected void parseVariablePart(ByteArrayBuffer buffer) throws ParseException {
            labels = buffer.readTextStrings(dataLength);
        }

        @Override
        protected TextRecord build() {
            return new TextRecord(name, qclass, ttl, labels);
        }
    }
}
