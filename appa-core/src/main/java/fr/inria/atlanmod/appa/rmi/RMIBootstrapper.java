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

import fr.inria.atlanmod.appa.Node;
import fr.inria.atlanmod.appa.service.DHTService;
import fr.inria.atlanmod.appa.service.NamingService;
import fr.inria.atlanmod.appa.service.registry.JmdnsJavaDiscoveryService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class RMIBootstrapper extends Node {

    private RMIRegistryService rmiService;
    private JmdnsJavaDiscoveryService discovery;

    public static void main(String[] args) {
        RMIBootstrapper instance = new RMIBootstrapper();
        instance.start();
    }

    @Override
    protected void startDiscovery() {
        discovery = new JmdnsJavaDiscoveryService();
        this.execute(discovery);
    }

    @Override
    protected void startBroker() {
        rmiService = new RMIRegistryService();
        this.execute(rmiService);
        try {
            discovery.publish(rmiService);
        }
        catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void startDHT() {
        try {
            RemoteMap<?, ?> map = (RemoteMap<?, ?>) UnicastRemoteObject.exportObject(new RemoteMapServer<String, String>(), 0);
            rmiService.rebind(DHTService.NAME, map);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void startNaming() {
        try {
            RemoteNaming naming = (RemoteNaming) UnicastRemoteObject.exportObject(new RemoteNamingServer(), 0);
            rmiService.rebind(NamingService.NAME, naming);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
