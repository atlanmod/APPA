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

package fr.inria.atlanmod.appa.messaging.nio;

import fr.inria.atlanmod.appa.messaging.ResponseHandler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.logging.Logger;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
class Reader extends AbstractHandler {

    @SuppressWarnings("JavaDoc")
    private static final Logger logger = Logger.getLogger("appa.messaging");

    private static final int MAXIN = 8192;

    private final SelectionKey selectionKey;
    private final ByteBuffer input = ByteBuffer.allocate(MAXIN);

    private final Object sender;
    private final ResponseHandler handler;

    public Reader(MessagingServer messagingServer, SelectionKey selectionKey, ResponseHandler handler) throws IOException {
        super(messagingServer);

        this.selectionKey = selectionKey;
        this.handler = handler;
        this.sender = this.selectionKey.attachment();
        this.selectionKey.interestOps(SelectionKey.OP_READ);
        this.selectionKey.attach(this);
        this.selectionKey.selector().wakeup();
    }

    @Override
    public void handle(SelectionKey key) throws IOException {
        int numRead;

        SocketChannel socket = (SocketChannel) key.channel();
        this.input.clear();
        numRead = socket.read(this.input);

        logger.info("Reading " + numRead + " bytes");

        if (numRead == -1) {
            // Remote entity shut the socket down cleanly. Do the
            // same from our end and cancel the channel.
            selectionKey.channel().close();
            selectionKey.cancel();
        }
        else if (numRead > 0) {
            byte[] rspData = new byte[numRead];
            System.arraycopy(input.array(), 0, rspData, 0, numRead);
            process(rspData);
        }

        selectionKey.channel().close();
    }

    private void process(byte[] data) {
        if (data.length < 5) {
            logger.info("Message size smaller than 5 !");
        }
        handler.handleResponse(data);
        //schedule(new ArrivingMessageAction(messaging, socket, data));
    }
}
