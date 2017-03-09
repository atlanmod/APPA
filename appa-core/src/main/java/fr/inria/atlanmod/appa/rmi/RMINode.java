package fr.inria.atlanmod.appa.rmi;

import fr.inria.atlanmod.appa.Node;
import fr.inria.atlanmod.appa.datatypes.ConnectionDescription;
import fr.inria.atlanmod.appa.datatypes.Id;
import fr.inria.atlanmod.appa.datatypes.LongId;
import fr.inria.atlanmod.appa.exception.OperationFailedException;
import fr.inria.atlanmod.appa.service.DHTService;
import fr.inria.atlanmod.appa.service.NamingService;
import fr.inria.atlanmod.appa.service.registry.JmdnsJavaDiscoveryService;

import java.util.concurrent.ExecutionException;

public class RMINode extends Node {
    private JmdnsJavaDiscoveryService discovery;
    private RMIRegistryClient registry;
    private DHTService<String,String> dht;
    private NamingService naming;


    public RMINode() {
        super();
    }

    @Override
    protected void startDiscovery() {
        discovery = new JmdnsJavaDiscoveryService();
        this.execute(discovery);
    }

    @Override
    protected void startBroker() {
        ConnectionDescription description = null;
        try {
            description = discovery.locate(RMIRegistryService.NAME).get();
            registry = new RMIRegistryClient(description);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void startDHT() {
        dht = new DHTServiceFake(registry);
    }

    @Override
    protected void startNaming() {
        naming = new NamingServiceFake(registry);
    }


    public static void main(String[] args) throws OperationFailedException {
        RMINode node = new RMINode();
        node.start();
        node.dht.put("Hello", "Man");

        Id id = node.naming.register(new ConnectionDescription(250));
        System.out.printf("Received id: " + id);

        ConnectionDescription cd = node.naming.lookup(id);
        System.out.printf("Received CD for id: "+cd);

    }
}
