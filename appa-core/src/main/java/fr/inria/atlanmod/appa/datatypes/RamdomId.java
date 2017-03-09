/*
 * Created on 28 juin 07
 *
 */
package fr.inria.atlanmod.appa.datatypes;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class RamdomId implements Serializable, Id {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private UUID id;

    public RamdomId() {
        id = UUID.randomUUID();
    }

    @Override
    public long toLong() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
