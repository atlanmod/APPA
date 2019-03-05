package org.atlanmod.appa.binaries;

import org.atlanmod.appa.datatypes.Id;
import org.atlanmod.commons.primitive.Ints;

import java.util.Objects;

public class IntegerId implements Id {

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
        return Ints.toBytes(id);
    }
}
