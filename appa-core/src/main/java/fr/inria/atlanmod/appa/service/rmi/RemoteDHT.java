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

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.concurrent.Future;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface RemoteDHT<K, V> extends Remote {

    void put(K key, V value) throws RemoteException;

    V get(K key) throws RemoteException;

    void remove(K key) throws RemoteException;
}
