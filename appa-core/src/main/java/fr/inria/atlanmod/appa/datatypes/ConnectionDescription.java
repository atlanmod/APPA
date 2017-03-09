/*
 * Created on 27 juil. 07
 *
 */
package fr.inria.atlanmod.appa.datatypes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class ConnectionDescription implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private InetSocketAddress socketAddress;

    public ConnectionDescription(InetSocketAddress addr) {
        socketAddress = addr;
    }

    public ConnectionDescription(int port) {
        this(new InetSocketAddress("localhost", port));
    }

    public ConnectionDescription(InetAddress addr, int port) {
        this(new InetSocketAddress(addr, port));
    }

    public InetSocketAddress getSocketAddress() {
        return socketAddress;
    }

    @Override
    public String toString() {
        return String.format("%s:%d", socketAddress.getHostName(), socketAddress.getPort());
    }

    public static void main(String[] argv) {

        try {
            InetSocketAddress addr = new InetSocketAddress("localhost", 22);
            ConnectionDescription cd = new ConnectionDescription(addr);
            ByteArrayOutputStream oStream = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(oStream);
            oos.writeObject(cd);
            oos.close();

            ByteArrayInputStream iStream = new ByteArrayInputStream(oStream.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(iStream);
            ConnectionDescription xcd = (ConnectionDescription) ois.readObject();
            ois.close();

            System.out.println(xcd.getSocketAddress());

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
}
