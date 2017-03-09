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

class Receiver extends Handler {
    private static final Logger logger = Logger.global;

    private static int MAXIN = 8192;

    private final SelectionKey sk;
    private ByteBuffer input = ByteBuffer.allocate(MAXIN);

    /**
     * Register the new SocketChannel with our Selector,
     * indicating we'd like to be notified when there's data waiting to be read
     * @param sel
     * @param c
     * @throws IOException
     */
    public Receiver(MessagingServer ms, SocketChannel sc) throws IOException {
        super(ms);
        //logger.info("Receiver created");
        sc.configureBlocking(false);
        sk = sc.register(getSelector(), SelectionKey.OP_READ, this);
        getSelector().wakeup();

        assert sk != null;
    }

    @Override
    public void run(SelectionKey key) throws IOException {

        SocketChannel socket = (SocketChannel) key.channel();
        this.input.clear();
        int numRead = socket.read(this.input);

        logger.info("Received: "+ numRead);

        if (numRead == -1) {
            logger.warning("Remote entity shut the socket down cleanly. Do the same from our end and cancel the channel.");
            sk.channel().close();
            sk.cancel();
        } else if (numRead > 0 ) {
            byte[] rspData = new byte[numRead];
            System.arraycopy(input.array(), 0, rspData, 0, numRead);
            process(rspData, socket);
        }



    }


    private void process(byte[] data, SocketChannel socket) {
        schedule(new ArrivingMessageAction(messaging, socket, data));
    }


}
