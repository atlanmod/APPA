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

class Reader extends Handler  {
    private static final Logger logger = Logger.global;
    private static int MAXIN = 8192;

    private final SelectionKey sk;
    private ByteBuffer input = ByteBuffer.allocate(MAXIN);
    private Object sender;
    private ResponseHandler handler;

    public Reader(MessagingServer ms, SelectionKey key, ResponseHandler rh) throws IOException {
        super(ms);
        assert key != null;

        sk = key;
        handler = rh;
        sender = sk.attachment();
        sk.interestOps(SelectionKey.OP_READ);
        sk.attach(this);
        sk.selector().wakeup();
    }

    @Override
    public void run(SelectionKey key) throws IOException {
        int numRead;

        SocketChannel socket = (SocketChannel) key.channel();
        this.input.clear();
        numRead = socket.read(this.input);

        logger.info("Reading " + numRead + " bytes");

        if (numRead == -1) {
            // Remote entity shut the socket down cleanly. Do the
            // same from our end and cancel the channel.
            sk.channel().close();
            sk.cancel();
        } else if (numRead >0 ) {
            byte[] rspData = new byte[numRead];
            System.arraycopy(input.array(), 0, rspData, 0, numRead);
            process(rspData);
        }

        sk.channel().close();
    }


    private void process(byte[] data) {
        if (data.length < 5)
            logger.info("Message size smaller than 5 !");
        handler.handleResponse(data);
        //schedule(new ArrivingMessageAction(messaging, socket, data));
    }


}
