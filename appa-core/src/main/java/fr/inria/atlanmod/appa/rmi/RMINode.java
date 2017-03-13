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
import fr.inria.atlanmod.appa.service.DHTService;
import fr.inria.atlanmod.appa.service.NamingService;
import fr.inria.atlanmod.appa.service.registry.JmdnsJavaDiscoveryService;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class RMINode extends Node {

    private JmdnsJavaDiscoveryService discovery;
    private RMIRegistryClient registry;
    private DHTService<String, String> dht;
    private NamingService naming;

    public RMINode() {
        super();
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

    @Override
    protected void startDiscovery() {
        discovery = new JmdnsJavaDiscoveryService();
        this.execute(discovery);
    }

    @Override
    protected void startBroker() {
        ConnectionDescription connection;
        try {
            connection = discovery.locate(RMIRegistryService.NAME).get();
            registry = new RMIRegistryClient(connection);
        }
        catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void startDHT() {
        dht = new DHTServiceFake(registry);
    }

    @Override
    protected void startNaming() {
        naming = new NamingServiceFake(registry);
    }
}
