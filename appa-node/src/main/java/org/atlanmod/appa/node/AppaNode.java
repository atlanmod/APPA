package fr.inria.atlanmod.appa.node;

import fr.inria.atlanmod.appa.core.Application;
import fr.inria.atlanmod.appa.core.Factory;
import fr.inria.atlanmod.appa.core.RegistryService;
import fr.inria.atlanmod.appa.datatypes.ConnectionDescription;
import fr.inria.atlanmod.appa.datatypes.Id;
import fr.inria.atlanmod.appa.datatypes.RamdomId;
import fr.inria.atlanmod.appa.datatypes.ServiceDescription;
import fr.inria.atlanmod.appa.rmi.RMIFactory;

/**
 * Created on 06/04/2017.
 *
 * @author AtlanMod team.
 */
public class AppaNode extends Application {
    public AppaNode(Factory factory) {
        super(factory);
    }

    public static void main(String[] args) throws Exception {
        Factory factory = new RMIFactory();
        AppaNode node = new AppaNode(factory);
        node.start();

        RegistryService registry = node.getRegistry();

        Id id = new RamdomId();

        ServiceDescription sd = new ServiceDescription(new ConnectionDescription(50), id);
        sd.protocol("http");

        registry.publish(sd);

        ServiceDescription retrieved = registry.locate(id).get();
        System.out.printf("Received SD for id: " + retrieved);

        Id nodeId = node.getNaming().register(new ConnectionDescription(80));
        System.out.println("Got a new identifier: " + nodeId);

    }
}
