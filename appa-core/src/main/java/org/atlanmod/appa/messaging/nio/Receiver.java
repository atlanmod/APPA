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
class Receiver extends AbstractHandler {

    @SuppressWarnings("JavaDoc")
    private static final Logger logger = Logger.getLogger("appa.messaging");

    private static int MAXIN = 8192;

    private final SelectionKey sk;
    private ByteBuffer input = ByteBuffer.allocate(MAXIN);

    /**
     * Register the new SocketChannel with our Selector,
     * indicating we'd like to be notified when there's data waiting to be read
     */
    public Receiver(MessagingServer messagingServer, SocketChannel channel) throws IOException {
        super(messagingServer);

        //logger.info("Receiver created");

        channel.configureBlocking(false);
        sk = channel.register(getSelector(), SelectionKey.OP_READ, this);
        getSelector().wakeup();

        assert sk != null;
    }

    @Override
    public void handle(SelectionKey key) throws IOException {

        SocketChannel socket = (SocketChannel) key.channel();
        this.input.clear();
        int numRead = socket.read(this.input);

        logger.info("Received: " + numRead);

        if (numRead == -1) {
            logger.warning("Remote entity shut the socket down cleanly. Do the same from our end and cancel the channel.");
            sk.channel().close();
            sk.cancel();
        } else if (numRead > 0) {
            byte[] rspData = new byte[numRead];
            System.arraycopy(input.array(), 0, rspData, 0, numRead);
            process(rspData, socket);
        }
    }

    private void process(byte[] data, SocketChannel socket) {
        schedule(new ArrivingMessageAction(messagingServer, socket, data));
    }
}
