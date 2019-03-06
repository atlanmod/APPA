package org.atlanmod.appa.node;

import org.atlanmod.appa.core.Application;
import org.atlanmod.appa.core.Factory;
import org.atlanmod.appa.core.RegistryService;
import org.atlanmod.appa.datatypes.ConnectionDescription;
import org.atlanmod.appa.datatypes.Id;
import org.atlanmod.appa.datatypes.RamdomId;
import org.atlanmod.appa.datatypes.ServiceDescription;
import org.atlanmod.appa.rmi.RMIFactory;

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
