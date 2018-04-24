package org.atlanmod.appa.zeroconf.mdns;

import org.atlanmod.appa.zeroconf.io.ByteArrayBuffer;
import org.atlanmod.appa.zeroconf.io.UnsignedInt;
import org.atlanmod.appa.zeroconf.names.HostName;

import java.text.ParseException;

public class NormalRecord extends Record {

    protected final QClass qclass;
    protected final UnsignedInt ttl;

    NormalRecord(NameArray name, QClass qclass, UnsignedInt ttl) {
        super(name);
        this.qclass = qclass;
        this.ttl = ttl;
    }

    public UnsignedInt ttl() {
        return this.ttl;
    }

    public QClass qclass() {
        return qclass;
    }

    @Override
    public String toString() {
        return "{" +
                "names=" + names +
                ", qclass=" + qclass +
                ", ttl=" + ttl +
                '}';
    }

    protected static abstract class NormalRecordParser<T extends NormalRecord> implements RecordParser<T> {

        protected NameArray name;
        protected QClass qclass;
        protected UnsignedInt ttl;
        protected int dataLength;

        @Override
        public T parse(NameArray name, ByteArrayBuffer buffer) throws ParseException {
            this.name = name;
            this.parseFixedPart(buffer);
            this.parseVariablePart(buffer);
            return build();
        }

        protected void parseFixedPart(ByteArrayBuffer buffer) throws ParseException {
            qclass = QClass.fromByteBuffer(buffer);
            ttl = buffer.getUnsignedInt();
            dataLength = buffer.getUnsignedShort().toInt();
        }

        protected void parseVariablePart(ByteArrayBuffer buffer) throws ParseException {

        }

        abstract protected T build();
    }
}