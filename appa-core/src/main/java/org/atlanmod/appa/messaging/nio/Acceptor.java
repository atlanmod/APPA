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

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.logging.Logger;

@ParametersAreNonnullByDefault
class Acceptor extends AbstractHandler {

    @SuppressWarnings("JavaDoc")
    private static final Logger logger = Logger.getLogger("appa.messaging");

    public Acceptor(MessagingServer messagingServer) {
        super(messagingServer);
    }

    @Override
    public void handle(SelectionKey key) throws IOException {
        SocketChannel socket = messagingServer.getServerSocket().accept();

        if (socket != null) {
            new Receiver(messagingServer, socket);
        } else {
            logger.warning("Acceptor could not create socket");
        }
    }
}
