/*
 * Copyright (c) 2016-2017 Atlanmod INRIA LINA Mines Nantes.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Atlanmod INRIA LINA Mines Nantes - initial API and implementation
 */

package fr.inria.atlanmod.appa.datatypes;

import javax.annotation.Nonnull;

public final class LongId implements Id {

    @SuppressWarnings("JavaDoc")
    private static final long serialVersionUID = -421037044740380272L;

    private final long id;

    public LongId(long id) {
        this.id = id;
    }

    @Override
    public long toLong() {
        return id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LongId longId = (LongId) o;
        return id == longId.id;
    }

    @Nonnull
    @Override
    public String toString() {
        return Long.toString(id);
    }
}
