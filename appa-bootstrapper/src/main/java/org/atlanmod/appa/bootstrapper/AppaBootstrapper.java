package org.atlanmod.appa.bootstrapper;

import org.atlanmod.appa.activemq.ArtemisBroker;
import org.atlanmod.appa.core.Application;
import org.atlanmod.appa.core.Factory;
import org.atlanmod.appa.datatypes.ConnectionDescription;
import org.atlanmod.appa.datatypes.ServiceDescription;
import org.atlanmod.appa.datatypes.StringId;
import org.atlanmod.appa.pubsub.PublishSubscribe;

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