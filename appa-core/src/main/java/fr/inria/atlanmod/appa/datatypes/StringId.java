package fr.inria.atlanmod.appa.datatypes;

import java.util.Objects;

/**
 * Created on 04/04/2017.
 *
 * @author AtlanMod team.
 */
public class StringId implements Id {

    private final String id;

    public StringId(String id) {
        this.id = id;
    }

    @Override
    public long toLong() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringId stringId = (StringId) o;
        return Objects.equals(id, stringId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
