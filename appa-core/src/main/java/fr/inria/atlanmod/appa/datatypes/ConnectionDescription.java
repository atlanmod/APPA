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

package fr.inria.atlanmod.appa.datatypes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public final class ConnectionDescription implements Serializable {

    @SuppressWarnings("JavaDoc")
    private static final long serialVersionUID = -5648760003002377742L;

    private static final String LOCALHOST = "127.0.0.1";

    private final InetSocketAddress host;

    public ConnectionDescription(InetSocketAddress socketAddress) {
        this.host = socketAddress;
    }

    public ConnectionDescription(int port) {
        this(new InetSocketAddress(LOCALHOST, port));
    }

    public ConnectionDescription(InetAddress address, int port) {
        this(new InetSocketAddress(address, port));
    }

    public static void main(String[] argv) {
        try {
            InetSocketAddress addr = new InetSocketAddress(LOCALHOST, 22);
            ConnectionDescription cd = new ConnectionDescription(addr);

            ByteArrayOutputStream oStream = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(oStream);
            oos.writeObject(cd);
            oos.close();

            ByteArrayInputStream iStream = new ByteArrayInputStream(oStream.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(iStream);
            ConnectionDescription xcd = (ConnectionDescription) ois.readObject();
            ois.close();

            System.out.println(xcd.getHost());
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public InetSocketAddress getHost() {
        return host;
    }

    @Override
    public String toString() {
        return String.format("%s:%d", host.getHostName(), host.getPort());
    }
}
