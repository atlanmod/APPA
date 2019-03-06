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

import org.atlanmod.appa.kernel.AsyncScheduler;
import org.atlanmod.appa.kernel.Scheduler;
import org.atlanmod.appa.messaging.ResponseHandler;
import org.atlanmod.appa.messaging.Server;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

@ParametersAreNonnullByDefault
public class MessagingServer implements Runnable, Server {

    @SuppressWarnings("JavaDoc")
    private static final Logger logger = Logger.getLogger("appa.messaging");

    /**
     * Queue a channel registration since the caller is not the selecting thread.
     * Otherwise, the register() method will block.
     */
    private final BlockingQueue<Register> pending = new LinkedBlockingQueue<>();

    /**
     * The channel on which we'll handle connections.
     */
    private final ServerSocketChannel serverSocket;

    /**
     * The selector we'll be monitoring.
     */
    private final Selector selector;

    private final Scheduler scheduler;

    /**
     * Create a new selector.
     * Bind the server socket to the specified address and port
     * Register the server socket channel, indicating an interest in accepting new connections
     */
    public MessagingServer(InetSocketAddress socketAddress, Scheduler scheduler) {
        try {
            this.selector = createSelector();
            this.serverSocket = ServerSocketChannel.open();
            this.serverSocket.configureBlocking(false);
            this.serverSocket.socket().bind(socketAddress);
            this.scheduler = scheduler;

            SelectionKey selectionKey = serverSocket.register(selector, SelectionKey.OP_ACCEPT);
            selectionKey.attach(new Acceptor(this));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Scheduler s = new AsyncScheduler(3);
        new Thread(s).start();
        InetSocketAddress isa = new InetSocketAddress(1958);
        MessagingServer ms = new MessagingServer(isa, s);
        new Thread(ms).start();
    }

    protected Selector createSelector() throws IOException {
        return Selector.open();
    }

    @Nonnull
    @Override
    public ResponseHandler send(byte[] data, InetSocketAddress isa) {
        ResponseHandler handler = new ResponseHandler();

        try {
            SocketChannel socket = SocketChannel.open();
            socket.configureBlocking(false);
            socket.connect(isa);

            pending.put(new Register(this, socket, data, handler));

            selector.wakeup();
        } catch (UnresolvedAddressException | UnsupportedAddressTypeException | SecurityException | IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return handler;
    }

    public void answer(SocketChannel socket, ByteBuffer data) throws IOException {
        //logger.info("Answering: " + data);
        new Answerer(this, socket, data);
    }

    @Override
    public void run() {
        logger.info(String.format("NIO MessagingServer running on %s", serverSocket.socket().getLocalSocketAddress()));

        try {
            while (!Thread.interrupted()) {
                pending.take().register();

                selector.select();

                Set<SelectionKey> selected = selector.selectedKeys();
                for (SelectionKey each : selected) {
                    dispatch(each);
                }
                selected.clear();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    Selector getSelector() {
        assert selector != null : "Null selector";
        return this.selector;
    }

    ServerSocketChannel getServerSocket() {
        return serverSocket;
    }

    void schedule(Runnable runnable) {
        scheduler.schedule(runnable);
    }

    private void dispatch(SelectionKey key) {
        try {
            Handler h = (Handler) key.attachment();
            if (h != null) {
                h.handle(key);
            } else {
                logger.warning("Selection key without attachment");
            }
        } catch (NotYetConnectedException | IOException e) {
            // The channel is not yet connected.
            e.printStackTrace();
        }
    }
}
