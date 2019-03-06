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

package org.atlanmod.appa.rmi;

import org.atlanmod.appa.Node;
import org.atlanmod.appa.core.NamingService;
import org.atlanmod.appa.datatypes.ConnectionDescription;
import org.atlanmod.appa.datatypes.Id;
import org.atlanmod.appa.datatypes.ServiceDescription;
import org.atlanmod.appa.datatypes.StringId;
import org.atlanmod.appa.service.rmi.RemoteNamingServiceAdapter;
import org.atlanmod.appa.service.zeroconf.ZeroconfRegistry;

import javax.annotation.ParametersAreNonnullByDefault;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutionException;

@ParametersAreNonnullByDefault
public class RMINode extends Node {

    private ZeroconfRegistry discovery;
    private RMIRegistry registry;
    //private DHTService<String, String> dht;
    private NamingService naming;

    public RMINode() {
        super();
    }

    public static void main(String[] args) throws UnknownHostException {
        RMINode node = new RMINode();
        node.start();
        //node.dht.put("Hello", "Man");

        Id id = node.naming.register(new ConnectionDescription(InetAddress.getLocalHost(), 250));
        System.out.printf("Received id: " + id);

        ConnectionDescription cd = node.naming.lookup(id);
        System.out.printf("Received CD for id: " + cd);
    }

    @Override
    protected void startDiscovery() {
        discovery = new ZeroconfRegistry();
        this.execute(discovery);
    }

    /*
    @Override
    protected void startDHT() {
        dht = new DHTServiceFake(registry);
    }
    */

    @Override
    protected void startBroker() {
        Id id = new StringId(RMIRegistry.NAME);
        ServiceDescription connection;
        try {
            connection = discovery.locate(id).get();
            registry = new RMIRegistry(connection);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void startNaming() {
        naming = new RemoteNamingServiceAdapter(registry);
    }
}
