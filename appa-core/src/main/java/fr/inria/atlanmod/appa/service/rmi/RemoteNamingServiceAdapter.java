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

package fr.inria.atlanmod.appa.service.rmi;

import fr.inria.atlanmod.appa.datatypes.ConnectionDescription;
import fr.inria.atlanmod.appa.datatypes.Id;
import fr.inria.atlanmod.appa.rmi.RMIRegistry;
import fr.inria.atlanmod.appa.core.NamingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * A Naming Service that uses RMI.
 */
@ParametersAreNonnullByDefault
public class RemoteNamingServiceAdapter implements NamingService {

    private RemoteNaming naming;
    private Logger logger = LoggerFactory.getLogger(RemoteNamingServiceAdapter.class);

    public RemoteNamingServiceAdapter(RMIRegistry registry) {
        naming = (RemoteNaming) registry.lookup(NamingService.NAME);
    }

    public RemoteNamingServiceAdapter(RemoteNaming naming) {
        this.naming = naming;
    }

    @Override
    public Id register(ConnectionDescription connection) {
        assert naming != null : "Null RemoteNaming instance";

        Id result;
        try {
            result = naming.register(connection);
        }
        catch (RemoteException e) {
            logger.error("Remote error on naming service", e);
            result = null;
        }

        return result;
    }

    @Override
    public ConnectionDescription lookup(Id id) {
        assert naming != null : "Null RemoteNaming instance";

        ConnectionDescription result;
        try {
            result = naming.lookup(id);
        }
        catch (RemoteException e) {
            logger.error("Remote error on naming service", e);
            result = null;
        }
        return result;
    }
}
