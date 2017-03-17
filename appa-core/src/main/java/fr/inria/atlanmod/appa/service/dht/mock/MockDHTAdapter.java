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

package fr.inria.atlanmod.appa.service.dht.mock;

import fr.inria.atlanmod.appa.core.Peer;
import fr.inria.atlanmod.appa.datatypes.ConnectionDescription;
import fr.inria.atlanmod.appa.datatypes.Id;
import fr.inria.atlanmod.appa.service.DHTService;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Future;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class MockDHTAdapter<K, V extends Serializable> implements DHTService<K, V> {

    private MockDHT dht;

    private Registry registry;

    public MockDHTAdapter(String registryAdress) {
        try {
            registry = LocateRegistry.getRegistry(registryAdress);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    // implémentations des méthodes de l'interface DHTService
    public void put(String key, Serializable value) {
        try {
            dht.put(key, value);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public Set<Serializable> get(String key) {
        Set<Serializable> value = null;
        try {
            Serializable s = dht.get(key);
            value = new HashSet<>();
            value.add(s);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
        return value;
    }

    public Peer lookup(String key) {
        return null;
    }

    public void create(Peer localPeer) {
    }

    public Peer direcSuccessor() {
        return null;
    }

    public boolean isInterval(String key) {
        return false;
    }

    public void join(Peer localPeer, Peer bootstrapPeer) {
    }

    public void leave() {
    }

    public Peer predecessor() {
        return null;
    }

    public Peer lookup(Serializable key) {
        return null;
    }

    @Nonnull
    public Id id() {
        return null;
    }

    /**
     * Méthode permettant la liaison au serveur RMI
     */
    public void start() {
        try {
            dht = (MockDHT) registry.lookup("DHTService");
        }
        catch (Exception e) {
            System.out.println("Liaison échouée");
        }
    }

    /**
     * Méthode permettant de suspendre la liaison avec le serveur RMI
     */
    public void stop() {
        try {
            registry.unbind("DHTService");
        }
        catch (Exception e) {

            System.out.println("Suppression de la liaison échouée");
        }
    }

    @Nonnegative
    @Override
    public int port() {
        return 0;
    }

    public ConnectionDescription getConnection() {
        return null;
    }

    public String printEntries() {
        return null;
    }

    @Override
    public void put(Object key, Serializable value) {
    }

    @Override
    public Future<V> get(Object key) {
        return null;
    }

    @Override
    public void run() {
    }
}
