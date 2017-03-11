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

package fr.inria.atlanmod.appa.remote;

import fr.inria.atlanmod.appa.base.Peer;
import fr.inria.atlanmod.appa.datatypes.RamdomId;

import java.net.InetAddress;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class RemotePeer implements Peer {

    private RamdomId id;

    private InetAddress addr;

    private long port;

    public RemotePeer(InetAddress addr, long port) {
        this.addr = addr;
        this.port = port;
    }

    public RemotePeer(RamdomId id) {
        this.id = id;
    }

    @Nonnull
    @Override
    public RamdomId getId() {
        return id;
    }

    @Override
    public long getBasePort() {
        return port;
    }

    @Nonnull
    @Override
    public InetAddress getIp() {
        return addr;
    }
}
