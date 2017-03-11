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

package fr.inria.atlanmod.appa.service;

import fr.inria.atlanmod.appa.base.Service;
import fr.inria.atlanmod.appa.datatypes.ConnectionDescription;
import fr.inria.atlanmod.appa.datatypes.RamdomId;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * The abstract implementation of a {@link Service}.
 */
@ParametersAreNonnullByDefault
public abstract class AbstractService implements Service {

    private final RamdomId id;

    private final ConnectionDescription description;

    public AbstractService(RamdomId id, ConnectionDescription description) {
        this.id = id;
        this.description = description;
    }

    @Nonnull
    @Override
    public RamdomId id() {
        return id;
    }

    public ConnectionDescription getConnectionDescription() {
        return description;
    }
}