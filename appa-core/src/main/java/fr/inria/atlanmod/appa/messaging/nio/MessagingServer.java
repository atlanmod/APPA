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

import fr.inria.atlanmod.appa.kernel.Schedule;
import fr.inria.atlanmod.appa.messaging.ResponseHandler;
import fr.inria.atlanmod.appa.messaging.Server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.NotYetConnectedException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.UnresolvedAddressException;
import java.nio.channels.UnsupportedAddressTypeException;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class MessagingServer implements Runnable, Server {

    @SuppressWarnings("JavaDoc")
    private static final Logger logger = Logger.getLogger("appa.messaging");

    /**
     * Queue a channel registration since the caller is not the selecting thread.
     * Otherwise, the register() method will block.
     */
    private final Queue<Register> pending = new LinkedBlockingQueue<>();

    /**
     * The channel on which we'll handle connections.
     */
    private ServerSocketChannel serverSocket;

    /**
     * The selector we'll be monitoring.
     */
    private Selector selector;

    private Schedule schedule;

    /**
     * Create a new selector.
     * Bind the server socket to the specified address and port
     * Register the server socket channel, indicating an interest in accepting new connections
     */
    public MessagingServer(InetSocketAddress socketAddress, Schedule schedule) {
        try {
            selector = createSelector();
            serverSocket = ServerSocketChannel.open();
            serverSocket.configureBlocking(false);
            serverSocket.socket().bind(socketAddress);

            SelectionKey selectionKey = serverSocket.register(selector, SelectionKey.OP_ACCEPT);
            selectionKey.attach(new Acceptor(this));

            this.schedule = schedule;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Schedule s = new Schedule(3);
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
        SocketChannel socket;
        try {
            socket = SocketChannel.open();
            socket.configureBlocking(false);
            socket.connect(isa);

            synchronized (pending) {
                pending.add(new Register(this, socket, data, handler));
            }

            selector.wakeup();
        }
        catch (ClosedByInterruptException e) {
            // If another thread interrupts the current thread while the
            // connect operation is in progress, thereby closing the channel
            // and setting the current thread's interrupt status
        }
        catch (AsynchronousCloseException e) {
            // If another thread closes this channel while the connect operation is in progress
        }
        catch (UnresolvedAddressException e) {
            // If the given remote address is not fully resolved
        }
        catch (UnsupportedAddressTypeException e) {
            // If the type of the given remote address is not supported
        }
        catch (SecurityException e) {
            // If a security manager has been installed and it does not permit access to the given remote endpoint
        }
        catch (IOException e) {
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
        logger.info(String.format("NIO MessagingServer running on %s",
                serverSocket.socket().getLocalSocketAddress()));
        try {
            while (!Thread.interrupted()) {

                synchronized (pending) {
                    for (Register each : pending) {
                        each.register();
                    }
                    pending.clear();
                }

                selector.select();
                Set<SelectionKey> selected = selector.selectedKeys();
                for (SelectionKey each : selected) {
                    dispatch(each);
                }
                selected.clear();
            }
        }
        catch (IOException e) {
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

    void schedule(Runnable r) {
        schedule.schedule(r);
    }

    private void dispatch(SelectionKey key) {
        try {
            Handler h = (Handler) key.attachment();
            if (h != null) {
                h.handle(key);
            }
            else {
                logger.warning("Selection key without attachment");
            }
        }
        catch (NotYetConnectedException | IOException e) {
            // The channel is not yet connected.
            e.printStackTrace();
        }
    }
}
