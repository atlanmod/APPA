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

package org.atlanmod.appa.service.rmi;

import org.atlanmod.appa.core.NamingService;
import org.atlanmod.appa.datatypes.ConnectionDescription;
import org.atlanmod.appa.datatypes.Id;
import org.atlanmod.appa.service.naming.SimpleNamingService;

import javax.annotation.ParametersAreNonnullByDefault;
import java.rmi.RemoteException;

@ParametersAreNonnullByDefault
public class RemoteNamingService implements RemoteNaming {

    private final NamingService service = new SimpleNamingService();

    @Override
    public Id register(ConnectionDescription connection) throws RemoteException {
        return service.register(connection);
    }

    @Override
    public ConnectionDescription lookup(Id id) throws RemoteException {
        return service.lookup(id);
    }
}
