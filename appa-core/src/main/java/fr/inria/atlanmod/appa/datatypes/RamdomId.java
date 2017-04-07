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

import java.util.Objects;
import java.util.UUID;

import javax.annotation.Nonnull;

public final class RamdomId implements Id {

    @SuppressWarnings("JavaDoc")
    private static final long serialVersionUID = -812780808539442961L;

    private final UUID id;

    public RamdomId() {
        id = UUID.randomUUID();
    }

    @Override
    public long toLong() {
        return Objects.hash(id);
    }

    @Nonnull
    @Override
    public String toString() {
        return id.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RamdomId ramdomId = (RamdomId) o;
        return Objects.equals(id, ramdomId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
