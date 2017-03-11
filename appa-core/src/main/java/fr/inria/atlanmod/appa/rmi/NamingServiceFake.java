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
import fr.inria.atlanmod.appa.service.NamingService;

import java.rmi.RemoteException;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * A Naming Service that uses RMI.
 */
@ParametersAreNonnullByDefault
public class NamingServiceFake implements NamingService {

    private RemoteNaming naming;

    public NamingServiceFake(RMIRegistry registry) {
        naming = (RemoteNaming) registry.lookup(NamingService.NAME);
    }

    @Override
    public Id register(ConnectionDescription description) {
        try {
            return naming.register(description);
        }
        catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ConnectionDescription lookup(Id id) {
        try {
            return naming.lookup(id);
        }
        catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
