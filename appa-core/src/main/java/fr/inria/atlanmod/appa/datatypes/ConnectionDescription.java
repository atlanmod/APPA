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
import java.net.UnknownHostException;
import java.util.Objects;

import javax.annotation.Nonnegative;

public class ConnectionDescription implements Serializable {

    @SuppressWarnings("JavaDoc")
    private static final long serialVersionUID = -5648760003002377742L;

    /**
     * The IP address used for this connection.
     */
    private final InetSocketAddress ip;

    /**
     * Constructs a new {@code ConnectionDescription} with the given {@code ip}.
     *
     * @param ip the described IP address
     */
    public ConnectionDescription(InetSocketAddress ip) {
        this.ip = ip;
    }

    /**
     * Constructs a new {@code ConnectionDescription} with the given {@code ipAddress}, on the given {@code port}.
     *
     * @param ipAddress the IP address
     * @param port      the port
     *
     * @throws IllegalArgumentException if the port parameter is outside the specified range of valid port values, i.e.,
     * if {@code port < 0 || port > 65536}
     */
    public ConnectionDescription(InetAddress ipAddress, @Nonnegative int port) {
        this(new InetSocketAddress(ipAddress, port));
    }


    public ConnectionDescription(@Nonnegative int port) {
        assert port >= 0 && port <= 65536 : "Invalid port value";
        InetAddress localhost;
        try {
            localhost = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            localhost = InetAddress.getLoopbackAddress();
        }
        this.ip = new InetSocketAddress(localhost, port);
    }



    /**
     * Returns the IP address used for this connection.
     *
     * @return the IP address
     */
    public InetSocketAddress ip() {
        return ip;
    }

    /**
     * Returns the port used for this service.
     *
     * @return an int, the port number.
     */
    public int port() {
        return ip.getPort();
    }

    @Override
    public String toString() {
        return String.format("%s:%d", ip.getHostName(), ip.getPort());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConnectionDescription that = (ConnectionDescription) o;
        return Objects.equals(ip, that.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip);
    }

    public static void main(String[] argv) {
        try {
            InetSocketAddress ip = new InetSocketAddress(InetAddress.getLocalHost(), 22);
            ConnectionDescription cd = new ConnectionDescription(ip);
            ConnectionDescription xcd;

            try (ByteArrayOutputStream oStream = new ByteArrayOutputStream()) {
                try (ObjectOutputStream oos = new ObjectOutputStream(oStream)) {
                    oos.writeObject(cd);
                }

                try (ByteArrayInputStream iStream = new ByteArrayInputStream(oStream.toByteArray()); ObjectInputStream ois = new ObjectInputStream(iStream)) {
                    xcd = (ConnectionDescription) ois.readObject();
                }
            }

            System.out.println(xcd.ip());
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
