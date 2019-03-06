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

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.logging.Logger;

@ParametersAreNonnullByDefault
class Answerer extends AbstractHandler {

    @SuppressWarnings("JavaDoc")
    private static final Logger logger = Logger.getLogger("appa.messaging");

    private final ByteBuffer output;

    private final Object receiver;

    public Answerer(MessagingServer messagingServer, SocketChannel socketChannel, ByteBuffer byteBuffer) throws IOException {
        super(messagingServer);

        SelectionKey sk = socketChannel.keyFor(getSelector());
        output = byteBuffer;

        receiver = sk.attachment();
        sk.interestOps(SelectionKey.OP_WRITE);
        sk.attach(this);
        getSelector().wakeup();
    }

    @Override
    public void handle(SelectionKey key) throws IOException {
        assert receiver != null : "Receiver should not be null";

        Integer size = output.array().length;
        logger.info("Answering " + size + " bytes");

        SocketChannel socket = (SocketChannel) key.channel();

        socket.write(output);
        //key.interestOps(SelectionKey.OP_READ);
        //key.attach(receiver);
        socket.close();
    }
}
