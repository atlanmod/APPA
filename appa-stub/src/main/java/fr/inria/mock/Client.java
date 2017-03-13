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

import fr.inria.atlanmod.appa.rmi.RemoteMap;

import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class Client {

    @SuppressWarnings("unchecked")
    public static void main(String args[]) {
        try {
            Registry registry = LocateRegistry.getRegistry(InetAddress.getLocalHost().getHostAddress());
            RemoteMap<String, String> mock = (RemoteMap<String, String>) registry.lookup("DHTService");
            mock.put("a", "toto");
            mock.put("b", "halo");
            mock.put("a", "tota");
            System.out.println("-----> " + mock.get("a"));
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }
}