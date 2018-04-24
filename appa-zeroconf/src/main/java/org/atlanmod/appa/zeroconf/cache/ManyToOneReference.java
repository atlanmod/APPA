package org.atlanmod.appa.zeroconf.cache;

import java.util.Objects;

public abstract class ManyToOneReference<C, T> {
    protected C container;
    private T reference;

    public ManyToOneReference(C container) {
        this.container = container;
    }


    public void set(T reference) {
        if (Objects.nonNull(this.reference)) {
            opposite(reference).basicRemove(container);
        }
        this.basicSet(reference);
        opposite(reference).basicAdd(container);
    }

    public void basicSet(T reference) {
        this.reference = reference;
    }

    public void unset() {
        if (Objects.nonNull(reference)) {
            opposite(reference).basicRemove(container);
        }
        reference = null;
    }

    public abstract OneToManyReference<T, C> opposite(T opposite);

}
