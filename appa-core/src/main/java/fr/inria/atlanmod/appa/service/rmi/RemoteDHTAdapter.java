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

import fr.inria.atlanmod.appa.core.DHT;
import fr.inria.atlanmod.appa.core.Peer;
import fr.inria.atlanmod.appa.datatypes.ConnectionDescription;
import fr.inria.atlanmod.appa.datatypes.Id;
import fr.inria.atlanmod.appa.datatypes.ServiceDescription;
import fr.inria.atlanmod.appa.rmi.RMIRegistry;
import fr.inria.atlanmod.appa.service.dht.DHTService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.Future;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class RemoteDHTAdapter<K extends Serializable, V extends Serializable> implements DHTService<K, V> {

    private RemoteDHT<K,V> dht;
    private Logger logger = LoggerFactory.getLogger(RemoteDHTAdapter.class);

    public RemoteDHTAdapter(RMIRegistry registry) {
        dht = (RemoteDHT<K, V>) registry.lookup(DHTService.NAME);
    }

    public RemoteDHTAdapter(RemoteDHT<K,V> dht) {
        this.dht = dht;
    }

    public void put(K key, V value) {
        try {
            dht.put(key, value);
        } catch (RemoteException e) {
            logger.error("Remote error on RemoteDHTAdapter", e);
        }
    }

    @Override
    public V get(K key) {
        V result;
        try {
            result =  dht.get(key);
        } catch (RemoteException e) {
            logger.error("Remote error on RemoteDHTAdapter", e);
            result = null;
        }
        return result;
    }


    @Override
    public void remove(K key) {
        try {
            dht.remove(key);
        } catch (RemoteException e) {
            logger.error("Remote error on RemoteDHTAdapter", e);
        }
    }
}
