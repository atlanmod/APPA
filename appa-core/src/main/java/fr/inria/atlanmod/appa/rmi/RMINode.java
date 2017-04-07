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
import fr.inria.atlanmod.appa.datatypes.ConnectionDescription;
import fr.inria.atlanmod.appa.datatypes.Id;
import fr.inria.atlanmod.appa.datatypes.ServiceDescription;
import fr.inria.atlanmod.appa.datatypes.StringId;
import fr.inria.atlanmod.appa.service.dht.DHTService;
import fr.inria.atlanmod.appa.core.NamingService;
import fr.inria.atlanmod.appa.service.rmi.RemoteNamingServiceAdapter;
import fr.inria.atlanmod.appa.service.zeroconf.ZeroconfRegistry;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutionException;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class RMINode extends Node {

    private ZeroconfRegistry discovery;
    private RMIRegistry registry;
    private DHTService<String, String> dht;
    private NamingService naming;

    public RMINode() {
        super();
    }



    @Override
    protected void startDiscovery() {
        discovery = new ZeroconfRegistry();
        this.execute(discovery);
    }

    @Override
    protected void startBroker() {
        Id id = new StringId(RMIRegistry.NAME);
        ServiceDescription connection;
        try {
            connection = discovery.locate(id).get();
            registry = new RMIRegistry(connection);
        }
        catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void startDHT() {
        dht = new DHTServiceFake(registry);
    }

    @Override
    protected void startNaming() {
        naming = new RemoteNamingServiceAdapter(registry);
    }

    public static void main(String[] args) throws UnknownHostException {
        RMINode node = new RMINode();
        node.start();
        node.dht.put("Hello", "Man");

        Id id = node.naming.register(new ConnectionDescription(InetAddress.getLocalHost(), 250));
        System.out.printf("Received id: " + id);

        ConnectionDescription cd = node.naming.lookup(id);
        System.out.printf("Received CD for id: " + cd);
    }
}
