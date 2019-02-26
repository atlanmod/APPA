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

import fr.inria.atlanmod.appa.core.Peer;
import fr.inria.atlanmod.appa.datatypes.Id;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.net.InetAddress;

@ParametersAreNonnullByDefault
public class RemotePeer implements Peer {

    private Id id;

    private InetAddress ip;

    private long port;

    public RemotePeer(InetAddress ip, long port) {
        this.ip = ip;
        this.port = port;
    }

    public RemotePeer(Id id) {
        this.id = id;
    }

    @Nonnull
    @Override
    public Id id() {
        return id;
    }

    @Override
    public long getBasePort() {
        return port;
    }

    @Nonnull
    @Override
    public InetAddress getIp() {
        return ip;
    }
}
