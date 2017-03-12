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

package fr.inria.atlanmod.appa.rmi;

import fr.inria.atlanmod.appa.datatypes.ConnectionDescription;
import fr.inria.atlanmod.appa.datatypes.Id;
import fr.inria.atlanmod.appa.datatypes.LongId;

import java.rmi.RemoteException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class RemoteNamingServer implements RemoteNaming {

    private final Map<Id, ConnectionDescription> map = new ConcurrentHashMap<>();

    private final AtomicLong lastValue = new AtomicLong(0);

    @Override
    public Id register(ConnectionDescription connection) throws RemoteException {
        LongId newId = new LongId(lastValue.incrementAndGet());
        map.put(newId, connection);
        return newId;
    }

    @Override
    public ConnectionDescription lookup(Id id) throws RemoteException {
        return map.get(id);
    }
}
