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

import fr.inria.atlanmod.appa.datatypes.ServiceDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.ParametersAreNonnullByDefault;
import java.net.InetSocketAddress;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Objects;

@ParametersAreNonnullByDefault
public class RMIRegistry {

    public static final String NAME = "rmiregistry";
    public static final String PROTOCOL = "jrmp";
    public static final int PORT = Registry.REGISTRY_PORT;
    public static final int TIMES = 5;

    /**
     * RMI registry (broker)
     */
    private Registry registry;

    private ServiceDescription description;

    private Logger logger = LoggerFactory.getLogger(RMIRegistry.class);

    public RMIRegistry(ServiceDescription connection) throws RemoteException {
        InetSocketAddress socketAddress = connection.connection().socket();
        registry = LocateRegistry.getRegistry(socketAddress.getHostName(), socketAddress.getPort());
        logger.info("rmi registry found: " + connection);
    }

    public RMIRegistry() throws RemoteException {
        registry = LocateRegistry.createRegistry(RMIRegistry.PORT);
    }

    public void rebind(String name, Remote object) {
        assert Objects.nonNull(registry);

        try {
            registry.rebind(name, object);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    // TODO Use https://github.com/jhalterman/failsafe for try-catch-retry
    public Remote lookup(String name) {
        Remote result = null;
        int times = 0;

        while (times < TIMES && result == null) {
            try {
                result = registry.lookup(name);
            }
            catch (NotBoundException | RemoteException e) {
                logger.warn("RMI could not bind object", e);
            }
            times++;

            if (result == null) {
                try {
                    Thread.sleep(300 * times);
                }
                catch (InterruptedException ignored) {
                }
            }
        }
        return result;
    }
}
