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
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

@ParametersAreNonnullByDefault
class Register extends AbstractHandler {

    private byte[] data;

    private ResponseHandler responseHandler;

    private SocketChannel socket;

    public Register(MessagingServer messagingServer, SocketChannel socket, byte[] data, ResponseHandler responseHandler) {
        super(messagingServer);
        this.socket = socket;
        this.data = data;
        this.responseHandler = responseHandler;
    }

    public void register() {
        //logger.info(String.format("[%d] Register::register()", port));
        try {
            socket.register(this.getSelector(), SelectionKey.OP_CONNECT, this);
        } catch (ClosedChannelException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handle(SelectionKey key) throws IOException {
        socket.finishConnect();

        key.attach(new Sender(messagingServer, key, ByteBuffer.wrap(data), responseHandler));
    }
}