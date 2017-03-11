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

package fr.inria.mock;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class KTService implements TimestampServer {

    private int timestamp = 0;
    private AtomicInteger atomic = new AtomicInteger(0);

    public KTService(String brokerAdress) {
        Registry registry;
        try {
            // enregistre DHTService dans RMI
            TimestampServer stub = (TimestampServer) UnicastRemoteObject.exportObject(this, 0);
            //trouve le service de resolution de nom de RMI
            //registry = LocateRegistry.getRegistry(brokerAdress);
            registry = LocateRegistry.createRegistry(1099);
            //enregistre la référence dans le service de résolution
            registry.rebind("KTService", stub);
        }
        catch (RemoteException e) {

            System.out.println("l'objet n'a pas pu être dans le registre RMI");
        }
    }

    public static void main(String args[]) {
        //le registre RMI torunera dans le serveur
        @SuppressWarnings("unused")
        KTService kts;
        try {
            kts = new KTService(InetAddress.getLocalHost().getHostAddress());
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public long generateTimestamp() throws RemoteException {
        timestamp = atomic.incrementAndGet();
        return timestamp;
    }

    public long lastTimestamp() throws RemoteException {
        return timestamp;
    }
}

