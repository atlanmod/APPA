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

package org.atlanmod.appa.service;

import org.atlanmod.appa.core.Service;
import org.atlanmod.appa.datatypes.ConnectionDescription;
import org.atlanmod.appa.datatypes.Id;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * The abstract implementation of a {@link Service}.
 */
@ParametersAreNonnullByDefault
public abstract class AbstractService implements Service {

    private final Id id;

    private final ConnectionDescription connection;

    public AbstractService(Id id, ConnectionDescription connection) {
        this.id = id;
        this.connection = connection;
    }


    public ConnectionDescription getConnection() {
        return connection;
    }
}