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

import fr.inria.atlanmod.appa.core.Service;
import fr.inria.atlanmod.appa.core.ZeroconfService;
import fr.inria.atlanmod.appa.datatypes.RamdomId;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.Objects;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public abstract class RMIRegistry implements Service, ZeroconfService {

    public static final String NAME = "rmiregistry";

    public static final String TYPE = "_jrmp._tcp.local.";

    public static final int PORT = Registry.REGISTRY_PORT;

    protected Registry registry;

    @Nonnull
    @Override
    public RamdomId id() {
        return null;
    }

    @Nonnegative
    @Override
    public int port() {
        return PORT;
    }

    @Nonnull
    @Override
    public String name() {
        return NAME;
    }

    @Nonnull
    @Override
    public String type() {
        return TYPE;
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

        while (times < 5 && result == null) {
            try {
                result = registry.lookup(name);
            }
            catch (NotBoundException | RemoteException ignored) {
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
