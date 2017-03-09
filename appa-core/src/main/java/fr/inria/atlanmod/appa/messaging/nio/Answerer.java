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

class Answerer extends Handler {
    private static final Logger logger = Logger.getLogger("appa.messaging");
    private ByteBuffer output;
    private Object receiver;

    public Answerer(MessagingServer ms, SocketChannel sc, ByteBuffer bb)
            throws IOException {
        super(ms);
        assert bb != null;

        SelectionKey sk = sc.keyFor(getSelector());
        output = bb;

        receiver = sk.attachment();
        sk.interestOps(SelectionKey.OP_WRITE);
        sk.attach(this);
        getSelector().wakeup();
    }

    @Override
    public void run(SelectionKey key) throws IOException {
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
