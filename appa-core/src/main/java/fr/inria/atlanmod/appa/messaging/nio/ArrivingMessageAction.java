package fr.inria.atlanmod.appa.messaging.nio;
import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

import fr.inria.atlanmod.appa.base.Action;
import fr.inria.atlanmod.appa.base.Message;

class ArrivingMessageAction implements Action {

    private static final Logger logger = Logger.getLogger("appa.messaging");

	private MessagingServer server;
	private SocketChannel socket;
	private byte[] data;

	private int cc = 0;

	public ArrivingMessageAction(MessagingServer server, SocketChannel socket, byte[] data) {
	    //logger.info("Creating message action");
	    assert server != null;
	    assert socket != null;
	    assert data != null;

		this.server = server;
		this.socket = socket;
		this.data = data;
	}

    public void run() {
        // Message m;
        Collection<Message> messages = new ArrayList<Message>(5);

        Integer size = data.length;
        logger.info("Message arrived containing : " + size + " bytes");
        try {
            ObjectInputStream ois = new ObjectInputStream(
                    new ByteArrayInputStream(data));
             do {
                messages.add((Message) ois.readObject());
            } while (ois.available() > 0);

        } catch (EOFException e) {
            logger.warning(String.format(
                    "Could not interpret message of size: %d.", data.length));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //logger.info(String.format("Read %d objects", messages.size()));
        for (Message each : messages) {
            try {
                this.server.answer(socket, ByteBuffer.wrap(each.toByteArray()));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }



    @Override
    public String toString() {
        return "Arriving Message Action";
    }
}