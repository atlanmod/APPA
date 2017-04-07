package fr.inria.atlanmod.appa.service.zeroconf;

import fr.inria.atlanmod.appa.datatypes.Id;
import fr.inria.atlanmod.appa.datatypes.RamdomId;
import fr.inria.atlanmod.appa.datatypes.ServiceDescription;
import fr.inria.atlanmod.appa.datatypes.StringId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created on 05/04/2017.
 *
 * @author AtlanMod team.
 */
public class ZeroconfRegistryTest {

    ZeroconfRegistry registry = new ZeroconfRegistry();

    @Before
    public void setUp() throws Exception {
        registry.start();
    }

    @After
    public void tearDown() throws Exception {
        registry.stop();
    }

    @Test
    public void publish() throws Exception {
        Id id = new StringId("id1");
        InetSocketAddress socketAddress = new InetSocketAddress(InetAddress.getLocalHost(), 9090);
        ServiceDescription sd1 = new ServiceDescription(socketAddress, id, "daap");
        System.out.println("will publish service");
        registry.publish(sd1);
        System.out.println("will locate service");
        ServiceDescription sd2 = registry.locate(id).get();

        assertThat(sd1).isEqualTo(sd2);

    }

    @Test
    public void unpublish() throws Exception {

    }

    @Test
    public void locate() throws Exception {

    }

    @Test
    public void description() throws Exception {

    }

    @Test
    public void start() throws Exception {

    }

    @Test
    public void stop() throws Exception {

    }

    @Test
    public void run() throws Exception {

    }

}