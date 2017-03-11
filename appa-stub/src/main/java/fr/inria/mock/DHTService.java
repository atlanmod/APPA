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

import fr.inria.atlanmod.appa.core.DHT;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class DHTService<K, V extends Serializable> extends RMIService implements DHT<K, V> {

    private Map<K, V> data = new HashMap<>();

    public DHTService(String brokerAdress) {
        super(brokerAdress);

//        try {
//            // Enregistre DHTService dans RMI
//            RemoteMap stub = (RemoteMap) UnicastRemoteObject.exportObject(this, 0);
//
//            // Trouve le service de resolution de nom de RMI
//
//            //registry = LocateRegistry.getRegistry(brokerAdress);
//            Registry registry = LocateRegistry.createRegistry(1099);
//
//            //enregistre la référence dans le service de résolution
//            registry.rebind("DHTService", stub);
//        }
//        catch (RemoteException e) {
//            System.out.println("l'objet n'a pas pu être dans le registre RMI");
//        }
    }

    public static void main(String args[]) {
        //le registre RMI torunera dans le serveur
        @SuppressWarnings("unused")
        DHTService<?, ?> dht;
        try {
            dht = new DHTService(InetAddress.getLocalHost().getHostAddress());
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        registry();
    }

    @Override
    public void put(K k, V v) {
        data.put(k, v);
    }

    @Override
    public Future<V> get(K k) {
        //Serializable s = data.get(k);
        return null;
    }

    @ParametersAreNonnullByDefault
    class ReturnValue<V1> implements Future<V1> {

        private V1 value;

        public ReturnValue(V1 v) {
            value = v;
        }

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            return false;
        }

        @Override
        public boolean isCancelled() {
            return false;
        }

        @Override
        public boolean isDone() {
            return true;
        }

        @Override
        public V1 get() throws InterruptedException, ExecutionException {
            return value;
        }

        @Override
        public V1 get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return value;
        }
    }
}


