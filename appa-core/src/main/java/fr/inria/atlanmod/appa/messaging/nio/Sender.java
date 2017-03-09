/*
 * Created on 1 ao�t 07
 *
 */
package fr.inria.atlanmod.appa.messaging.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.logging.Logger;

import fr.inria.atlanmod.appa.messaging.ResponseHandler;

class Sender extends Handler {

    private final SelectionKey sk;
    private ByteBuffer output;
    private static final Logger logger = Logger.global;
    private ResponseHandler handler;

    public Sender(MessagingServer ms, SelectionKey key,
            ByteBuffer bb, ResponseHandler rh) throws IOException {
        super(ms);
        assert key != null;
        assert bb != null;

        output = bb;
        sk = key;
        handler = rh;
        sk.interestOps(SelectionKey.OP_WRITE);
        sk.attach(this);
        getSelector().wakeup();
    }

    @Override
    public void run(SelectionKey key) throws IOException {

        Integer size = output.array().length;
        logger.info("Sending " + size + " bytes");

        SocketChannel socket = (SocketChannel) key.channel();
        socket.write(output);
        new Reader(messaging, sk, handler);

    }

}
