package fr.inria.atlanmod.appa.datatypes;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import static org.assertj.core.api.Assertions.*;

/**
 * Created on 27/04/2017.
 *
 * @author AtlanMod team.
 */
public class ConnectionDescriptionTest {

    ConnectionDescription cd1,cd2,cd3,cd4;

    @Before
    public void setup() throws UnknownHostException {
        InetAddress addr = InetAddress.getLocalHost();

        cd1 = new ConnectionDescription(13);
        cd2 = new ConnectionDescription(addr, 13);
        cd3 = new ConnectionDescription(addr, 31);
        cd4 = new ConnectionDescription(31);

    }

    @Test
    public void testAddress() throws Exception {
        assertThat(cd1.address()).isEqualTo(InetAddress.getLocalHost());
        assertThat(cd2.address()).isEqualTo(cd4.address());
    }

    @Test
    public void testPort() throws Exception {
        assertThat(cd1.port()).isEqualTo(cd2.port());
        assertThat(cd3.port()).isEqualTo(cd4.port());
    }

    @Test
    public void testToString() throws Exception {
    }

    @Test
    public void testEquals() throws Exception {
        assertThat(cd1).isEqualTo(cd2);
        assertThat(cd3).isEqualTo(cd4);
    }

    @Test
    public void testSerialization() throws Exception {
        ByteArrayOutputStream oStream = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(oStream);
        oos.writeObject(cd1);
        ByteArrayInputStream iStream = new ByteArrayInputStream(oStream.toByteArray()); ObjectInputStream ois = new ObjectInputStream(iStream);
        ConnectionDescription found = (ConnectionDescription) ois.readObject();

        assertThat(found).isEqualTo(cd1);
    }


}