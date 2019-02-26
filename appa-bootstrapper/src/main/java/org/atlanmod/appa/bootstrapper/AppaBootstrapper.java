package fr.inria.atlanmod.appa.bootstrapper;

import fr.inria.atlanmod.appa.activemq.ArtemisBroker;
import fr.inria.atlanmod.appa.core.Application;
import fr.inria.atlanmod.appa.core.Factory;
import fr.inria.atlanmod.appa.datatypes.ConnectionDescription;
import fr.inria.atlanmod.appa.datatypes.ServiceDescription;
import fr.inria.atlanmod.appa.datatypes.StringId;
import fr.inria.atlanmod.appa.pubsub.PublishSubscribe;

import java.rmi.RemoteException;

/**
 * Created on 05/04/2017.
 *
 * @author AtlanMod team.
 */
public class AppaBootstrapper extends Application {
    public AppaBootstrapper(Factory factory) {
        super(factory);
    }

    public static void main(String[] args) throws RemoteException {
        AppaBootstrapper bootstrapper = new AppaBootstrapper(new RMIBootstrapperFactory());
        bootstrapper.start();
    }

    protected void beforeStart() {
        ConnectionDescription cd = new ConnectionDescription(PublishSubscribe.PORT);
        ServiceDescription sd = new ServiceDescription(cd, new StringId(PublishSubscribe.NAME));
        sd.protocol("tcp");

        ArtemisBroker server = new ArtemisBroker(sd);
        this.addService(server);
    }
}