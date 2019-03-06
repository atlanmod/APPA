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

import org.atlanmod.appa.messaging.ResponseHandler;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.logging.Logger;

@ParametersAreNonnullByDefault
class Sender extends AbstractHandler {

    @SuppressWarnings("JavaDoc")
    private static final Logger logger = Logger.getLogger("appa.messaging");

    private final SelectionKey key;

    private ByteBuffer byteBuffer;

    private ResponseHandler responseHandler;

    public Sender(MessagingServer messagingServer, SelectionKey key, ByteBuffer byteBuffer, ResponseHandler responseHandler) throws IOException {
        super(messagingServer);
        this.byteBuffer = byteBuffer;
        this.key = key;
        this.responseHandler = responseHandler;
        this.key.interestOps(SelectionKey.OP_WRITE);
        this.key.attach(this);

        getSelector().wakeup();
    }

    @Override
    public void handle(SelectionKey key) throws IOException {
        Integer size = byteBuffer.array().length;
        logger.info("Sending " + size + " bytes");

        SocketChannel socket = (SocketChannel) key.channel();
        socket.write(byteBuffer);
        new Reader(messagingServer, this.key, responseHandler);
    }
}
