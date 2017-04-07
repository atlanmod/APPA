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
import fr.inria.atlanmod.appa.activemq.ArtemisBroker;
import fr.inria.atlanmod.appa.datatypes.ConnectionDescription;
import fr.inria.atlanmod.appa.pubsub.PubSub;
import fr.inria.atlanmod.appa.service.dht.DHTService;
import fr.inria.atlanmod.appa.core.NamingService;
import fr.inria.atlanmod.appa.service.rmi.*;
import fr.inria.atlanmod.appa.service.zeroconf.ZeroconfRegistry;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class RMIBootstrapper extends Node {

    private RMIRegistry rmiService;
    private ZeroconfRegistry discovery;

    public static void main(String[] args) {
        RMIBootstrapper instance = new RMIBootstrapper();
        instance.start();
    }

    @Override
    protected void startDiscovery() {
        discovery = new ZeroconfRegistry();
        this.execute(discovery);
    }

    @Override
    protected void startBroker() {
        /*
        rmiService = new RMIRegistry();
        this.execute(rmiService);
        try {
            discovery.publish(rmiService);
        }
        catch (RuntimeException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    protected void startDHT() {
        try {
            RemoteDHT<?, ?> map = (RemoteDHT<?, ?>) UnicastRemoteObject.exportObject(new RemoteDHTService<String, String>(), 0);
            rmiService.rebind(DHTService.NAME, map);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void startNaming() {
        try {
            RemoteNaming naming = (RemoteNaming) UnicastRemoteObject.exportObject(new RemoteNamingService(), 0);
            rmiService.rebind(NamingService.NAME, naming);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    protected void afterStarting() {
        this.startJMSServer();
    }

    private void startJMSServer() {
        ConnectionDescription cd = new ConnectionDescription(PubSub.PORT);
        ArtemisBroker server = new ArtemisBroker(cd);
        this.execute(server);
    }
}
