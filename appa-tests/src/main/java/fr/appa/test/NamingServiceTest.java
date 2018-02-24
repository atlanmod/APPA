package fr.appa.test;

import fr.inria.atlanmod.appa.activemq.ArtemisBroker;
import fr.inria.atlanmod.appa.activemq.PublishSubscribeService;
import fr.inria.atlanmod.appa.bootstrapper.AppaBootstrapper;
import fr.inria.atlanmod.appa.bootstrapper.RMIBootstrapperFactory;
import fr.inria.atlanmod.appa.core.NamingService;
import fr.inria.atlanmod.appa.datatypes.ConnectionDescription;
import fr.inria.atlanmod.appa.datatypes.Id;
import fr.inria.atlanmod.appa.node.AppaNode;
import fr.inria.atlanmod.appa.pubsub.Consumer;
import fr.inria.atlanmod.appa.pubsub.Producer;
import fr.inria.atlanmod.appa.pubsub.PublishSubscribe;
import fr.inria.atlanmod.appa.rmi.RMIFactory;
import fr.inria.atlanmod.appa.tester.parser.AfterClass;
import fr.inria.atlanmod.appa.tester.parser.SetId;
import fr.inria.atlanmod.appa.tester.parser.TestStep;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import static fr.inria.atlanmod.appa.tester.tester.Assert.assertEquals;


public class NamingServiceTest {


    private AppaBootstrapper bootstrapper;
    private AppaNode node;
    private int testerID;
    private PublishSubscribeService pubsub;

    private Id id;
    private ConnectionDescription cd;

    @SetId
    public void setId(int i) {
        testerID = i;
    }


    @TestStep(range = "1", order = 1)
    public void startBootstrapper() throws RemoteException {
        bootstrapper = new AppaBootstrapper(new RMIBootstrapperFactory());
        bootstrapper.start();
    }


    @TestStep(range = "*", order = 1)
    public void startNode() throws RemoteException, NotBoundException {
        node = new AppaNode(new RMIFactory());
        node.start();
    }

    @TestStep(range = "*", order = 3)
    public void register() throws RemoteException {
        NamingService naming = node.getNaming();
        id = naming.register(new ConnectionDescription(80+ testerID));
    }

    @TestStep(range = "*", order = 4)
    public void lookup()  throws RemoteException {
        NamingService naming = node.getNaming();
        cd = naming.lookup(id);
    }

    @TestStep(range = "*", order = 5)
    public void check() throws RemoteException {
        assertEquals(new ConnectionDescription(80+testerID),cd);

    }

    @TestStep(range = "1", order = 6)
    public void startArtemisBroker(){
        ConnectionDescription cd = new ConnectionDescription(PublishSubscribe.PORT);;
        ArtemisBroker server = new ArtemisBroker(cd);
        server.start();
    }

    @TestStep(range = "*", order = 7)
    public void startPubSub() {
        ConnectionDescription description = new ConnectionDescription(PublishSubscribe.PORT);
        PublishSubscribeService pubsub = new PublishSubscribeService(description);
        pubsub.start();
    }

    @TestStep(range = "3", order = 8)
     public void createProducer() {
        Producer producer = pubsub.createTopic("neoemf");
        for (int i = 0; i < 100; i++) {
            producer.send("Element-" + i);
        }
    }

    @TestStep(range = "*", order = 8)
     public void createConsumers() {
        Consumer consumer = pubsub.consumeTopic("noemf");
        for (int i = 0; i < 10; i++) {
            String str = (String) consumer.receive(1000);
            System.out.println(str);
        }
    }
    
    @AfterClass
    public void end() {

    }

}
