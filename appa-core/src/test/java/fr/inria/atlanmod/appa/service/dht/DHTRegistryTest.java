package fr.inria.atlanmod.appa.service.dht;

import fr.inria.atlanmod.appa.core.RegistryService;
import fr.inria.atlanmod.appa.datatypes.Id;
import fr.inria.atlanmod.appa.datatypes.RamdomId;
import fr.inria.atlanmod.appa.datatypes.ServiceDescription;
import fr.inria.atlanmod.appa.test.doubles.FakeDHT;
import org.junit.Before;
import org.junit.Test;

import java.net.*;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created on 04/04/2017.
 *
 * @author AtlanMod team.
 */
public class DHTRegistryTest {

    private RegistryService registry = new DHTRegistry(new FakeDHT<Id, ServiceDescription>(1));

    ServiceDescription sd1;
    public DHTRegistryTest() throws URISyntaxException, UnknownHostException {
        InetSocketAddress socketAddress = new InetSocketAddress(InetAddress.getLocalHost(), 8080);
        sd1 = new ServiceDescription(socketAddress, new RamdomId(), "rmi");
    }


    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void publish() throws Exception {
        registry.publish(sd1);
        ServiceDescription sd2 = registry.locate(sd1.id()).get();
        assertThat(sd2).isEqualTo(sd1);

    }


    @Test(expected = NoSuchElementException.class)
    public void locate() throws Exception {
        registry.locate(new RamdomId());

    }

    @Test(expected = NoSuchElementException.class)
    public void unpublish() throws Exception {
        registry.unpublish(sd1);
        registry.locate(sd1.id());
    }

}