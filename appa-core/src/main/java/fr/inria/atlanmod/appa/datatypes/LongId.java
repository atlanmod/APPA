package fr.inria.atlanmod.appa.datatypes;

import java.io.Serializable;

public class LongId implements Serializable,Id {

    private long id;

    public LongId(long id) {
        this.id = id;
    }

    @Override
    public long toLong() {
        return id;
    }

    @Override
    public String toString() {
        return Long.toString(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LongId longId = (LongId) o;

        return id == longId.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
