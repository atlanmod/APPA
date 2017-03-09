/*
 * Created on 4 ao�t 07
 *
 */
package fr.inria.atlanmod.appa.messaging.nio;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.logging.Logger;

class Acceptor extends Handler {
    private static final Logger logger = Logger.global;

    Acceptor(MessagingServer ms) {
        super(ms);
    }

    @Override
    public void run(SelectionKey key) throws IOException {

        SocketChannel socket = messaging.getServerSocket().accept();
        if (socket != null) {
            new Receiver(messaging, socket);
        } else {
            logger.warning("Acceptor could not create socket");
        }

    }
}
