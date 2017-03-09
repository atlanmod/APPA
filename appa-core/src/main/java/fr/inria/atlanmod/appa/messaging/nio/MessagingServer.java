package fr.inria.atlanmod.appa.messaging.nio;

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
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import fr.inria.atlanmod.appa.kernel.Schedule;
import fr.inria.atlanmod.appa.messaging.ResponseHandler;
import fr.inria.atlanmod.appa.messaging.Server;
/**

 * @author sunye
 *
 */
public class MessagingServer implements Runnable, Server {

    private static final Logger logger = Logger.global;


    /**
     * Queue a channel registration since the caller is not the selecting thread.
     * Otherwise, the register() method will block.
     */
    private List<Register> pending = Collections.synchronizedList(new LinkedList<Register>());

    private Map<InetSocketAddress, SocketChannel> openConnections = new HashMap<InetSocketAddress, SocketChannel>();
	/**
	 * The channel on which we'll accept connections
	 */
	private ServerSocketChannel serverSocket;

	/**
	 * The selector we'll be monitoring
	 */
	private Selector selector;


	private Schedule schedule;

	/**
	 * Create a new selector
	 * Bind the server socket to the specified address and port
	 * Register the server socket channel, indicating an interest in accepting new connections
	 * @param isa
	 * @param schedule
	 * @throws IOException
	 */
	public MessagingServer(InetSocketAddress isa, Schedule scd) {

	    try {
            selector = createSelector();
            serverSocket = ServerSocketChannel.open();
            serverSocket.configureBlocking(false);
            serverSocket.socket().bind(isa);
            SelectionKey sk = serverSocket.register(selector, SelectionKey.OP_ACCEPT);
            sk.attach(new Acceptor(this));
            schedule = scd;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

	}


    protected Selector createSelector() throws IOException {
        return Selector.open();
    }


	/*
     * (non-Javadoc)
     *
     * @see Server#send(byte[],
     *      java.net.InetSocketAddress)
     *
     * Queue a channel registration since the caller is not the selecting
     * thread. As part of the registration we'll register an interest in
     * connection events. These are raised when a channel is ready to complete
     * connection establishment.
     */
	public ResponseHandler send(byte[] data, InetSocketAddress isa) {

        assert data != null;
        assert isa != null;

        ResponseHandler handler = new ResponseHandler();
        SocketChannel socket;
        try {
//             if (openConnections.containsKey(isa)) {
//             socket = openConnections.get(isa);
//             SelectionKey key = socket.keyFor(selector);
//             key.attach(new Sender(this, key, ByteBuffer.wrap(data),
//             handler));
//
//             } else {
            socket = SocketChannel.open();
//             openConnections.put(isa, socket);
            socket.configureBlocking(false);
            socket.connect(isa);

            synchronized (pending) {
                pending.add(new Register(this, socket, data, handler));
            }

            selector.wakeup();
//                       }

        } catch (ClosedByInterruptException e) {
            // If another thread interrupts the current thread while the
            // connect operation is in progress, thereby closing the channel
            // and setting the current thread's interrupt status
        } catch (AsynchronousCloseException e) {
            // If another thread closes this channel while the connect operation
            // is in progress
        } catch (UnresolvedAddressException e) {
            // If the given remote address is not fully resolved
        } catch (UnsupportedAddressTypeException e) {
            // If the type of the given remote address is not supported
        } catch (SecurityException e) {
            // If a security manager has been installed and it does not permit
            // access to the given remote endpoint
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return handler;
    }

	public void answer(SocketChannel socket, ByteBuffer data) throws IOException {
	    //logger.info("Answering: " + data);

	    assert socket != null;
	    assert data != null;

        new Answerer(this, socket, data);
	}


	public void run() {
        logger.info(String.format("NIO MessagingServer running on %s",
                serverSocket.socket().getLocalSocketAddress()));
        try {
            while (!Thread.interrupted()) {

                synchronized(pending) {
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
        } catch (IOException e) {
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
            Handler h = (Handler) (key.attachment());
            if (h != null)
                h.run(key);
            else
                logger.warning("Selection key without attachment");
        } catch (NotYetConnectedException e) {
            // The channel is not yet connected.
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
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
}
