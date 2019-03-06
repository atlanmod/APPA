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

package org.atlanmod.appa.messaging.nio;

import org.atlanmod.appa.core.Action;
import org.atlanmod.appa.core.Message;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

@ParametersAreNonnullByDefault
class ArrivingMessageAction implements Action {

    @SuppressWarnings("JavaDoc")
    private static final Logger logger = Logger.getLogger("appa.messaging");

    private MessagingServer server;

    private SocketChannel socket;

    private byte[] data;

    public ArrivingMessageAction(MessagingServer server, SocketChannel socket, byte[] data) {
        this.server = server;
        this.socket = socket;
        this.data = data;
    }

    @Override
    public void run() {
        // Message m;
        Collection<Message> messages = new ArrayList<>(5);

        Integer size = data.length;

        logger.info("Message arrived containing : " + size + " bytes");

        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
            do {
                messages.add((Message) ois.readObject());
            }
            while (ois.available() > 0);
        } catch (EOFException e) {
            logger.warning(String.format("Could not interpret message of size: %d.", data.length));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //logger.info(String.format("Read %d objects", messages.size()));
        for (Message message : messages) {
            try {
                server.answer(socket, ByteBuffer.wrap(message.toByteArray()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return "Arriving Message Action";
    }
}