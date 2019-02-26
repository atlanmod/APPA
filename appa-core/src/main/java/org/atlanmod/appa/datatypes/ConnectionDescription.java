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

import javax.annotation.Nonnegative;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Objects;

public class ConnectionDescription implements Serializable {

    @SuppressWarnings("JavaDoc")
    private static final long serialVersionUID = -5648760003002377742L;

    /**
     * The IP address used for this connection.
     */
    private final InetSocketAddress socket;

    /**
     * Constructs a new {@code ConnectionDescription} with the given {@code socket}.
     *
     * @param socket the described IP address
     */
    public ConnectionDescription(InetSocketAddress socket) {
        this.socket = socket;
    }

    /**
     * Constructs a new {@code ConnectionDescription} with the given {@code ipAddress}, on the given {@code port}.
     *
     * @param ipAddress the IP address
     * @param port      the port
     * @throws IllegalArgumentException if the port parameter is outside the specified range of valid port values, i.e.,
     *                                  if {@code port < 0 || port > 65536}
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
        this.socket = new InetSocketAddress(localhost, port);
    }


    /**
     * Returns the IP socket address used for this connection.
     *
     * @return the IP socket address
     */
    public InetSocketAddress socket() {
        return socket;
    }

    /**
     * Returns the IP address used for this connection.
     *
     * @return the IP address
     */
    public InetAddress address() {
        return socket.getAddress();
    }

    /**
     * Returns the port used for this service.
     *
     * @return an int, the port number.
     */
    public int port() {
        return socket.getPort();
    }

    @Override
    public String toString() {
        return String.format("%s:%d", socket.getHostName(), socket.getPort());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConnectionDescription that = (ConnectionDescription) o;
        return Objects.equals(socket, that.socket);
    }

    @Override
    public int hashCode() {
        return Objects.hash(socket);
    }

}
