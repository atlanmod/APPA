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

import java.net.InetSocketAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class RMIRegistryClient extends RMIRegistry {

    public RMIRegistryClient(ConnectionDescription connection) {
        InetSocketAddress socketAddress = connection.ip();
        try {
            registry = LocateRegistry.getRegistry(socketAddress.getHostName(),
                    socketAddress.getPort());
            System.out.printf("rmi registry found: " + connection);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
    }

    @Override
    public void run() {
    }
}