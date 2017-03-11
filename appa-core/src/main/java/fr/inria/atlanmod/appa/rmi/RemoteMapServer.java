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

import java.rmi.RemoteException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class RemoteMapServer<K, V> implements RemoteMap<K, V> {

    private final Map<K, V> map = new ConcurrentHashMap<>();

    @Override
    public void put(K key, V value) throws RemoteException {
        System.out.printf("adding value: " + value);
        map.put(key, value);
    }

    @Override
    public V get(K key) throws RemoteException {
        return map.get(key);
    }
}
