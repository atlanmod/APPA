/*
 * Created on 4 ao�t 07
 *
 */
package fr.inria.atlanmod.appa.messaging.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import fr.inria.atlanmod.appa.messaging.ResponseHandler;

class Register extends Handler {

    private byte[] data;
    private ResponseHandler handler;
    private SocketChannel socket;

    public Register(MessagingServer ms, SocketChannel sc, byte[] bytes, ResponseHandler rh) {
        super(ms);
        assert sc != null;

        socket = sc;
        data = bytes;
        handler = rh;
    }

    public void register() {
        //logger.info(String.format("[%d] Register::register()", port));
        try {
            socket.register(this.getSelector(), SelectionKey.OP_CONNECT, this);
        } catch (ClosedChannelException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void run(SelectionKey key) throws IOException {
        assert key != null;

        socket.finishConnect();
        key.attach(new Sender(messaging, key, ByteBuffer
                .wrap(data), handler));

    }
}