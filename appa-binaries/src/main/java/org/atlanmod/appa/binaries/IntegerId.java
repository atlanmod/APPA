package org.atlanmod.appa.binaries;

import org.atlanmod.appa.io.CompressedInts;

import java.nio.ByteBuffer;
import java.util.Objects;

public class IntegerId implements WritableId {

    private final int id;

    public IntegerId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntegerId)) return false;
        IntegerId integerId = (IntegerId) o;
        return id == integerId.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public long toLong() {
        return id;
    }

    @Override
    public byte[] toBytes() {
        return CompressedInts.toBytes(id);
    }

    @Override
    public void writeOn(ByteBuffer buffer) {
        buffer.put(this.toBytes());
    }

    @Override
    public String toString() {
        return "IntegerId{" + id + '}';
    }
}
