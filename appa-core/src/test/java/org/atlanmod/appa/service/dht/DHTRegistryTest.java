package org.atlanmod.appa.service.dht;

/**
 * Created on 04/04/2017.
 *
 * @author AtlanMod team.
 */
public class DHTRegistryTest {
    /*

    private RegistryService registry = new DHTRegistry(new FakeDHT<Id, ServiceDescription>(1));

    ServiceDescription sd1;
    public DHTRegistryTest() throws URISyntaxException, UnknownHostException {
        InetSocketAddress socketAddress = new InetSocketAddress(InetAddress.getLocalHost(), 8080);
        sd1 = new ServiceDescription(new ConnectionDescription(socketAddress), new RamdomId());
        sd1.protocol("rmi");
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

    */

}