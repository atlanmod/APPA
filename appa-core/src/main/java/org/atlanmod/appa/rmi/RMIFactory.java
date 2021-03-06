package org.atlanmod.appa.rmi;

import org.atlanmod.appa.core.Factory;
import org.atlanmod.appa.core.NamingService;
import org.atlanmod.appa.core.RegistryService;
import org.atlanmod.appa.datatypes.Id;
import org.atlanmod.appa.datatypes.ServiceDescription;
import org.atlanmod.appa.datatypes.StringId;
import org.atlanmod.appa.service.rmi.RemoteNamingServiceAdapter;
import org.atlanmod.appa.service.zeroconf.ZeroconfRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutionException;

/**
 * Created on 06/04/2017.
 *
 * @author AtlanMod team.
 */
public class RMIFactory implements Factory {

    private ZeroconfRegistry discovery;
    private RMIRegistry registry;
    private Logger logger = LoggerFactory.getLogger(RMIFactory.class);

    public RMIFactory() {
        discovery = new ZeroconfRegistry();
        discovery.start();
        registry = this.locateRMIRegistry();
    }

    @Nonnull
    @Override
    public RegistryService createRegistry() {
        RegistryService result;
        /*
        RemoteDHT<Id, ServiceDescription> dht = (RemoteDHT<Id, ServiceDescription>) registry.lookup(DHTService.NAME);
        DHT<Id, ServiceDescription> adapter = new RemoteDHTAdapter(dht);
        result = new DHTRegistry(adapter);

        return result;
        */
        return null;
    }

    @Nonnull
    @Override
    public NamingService createNaming() {
        return new RemoteNamingServiceAdapter(registry);
    }


    private RMIRegistry locateRMIRegistry() {
        Id id = new StringId(RMIRegistry.NAME);
        ServiceDescription connection;
        try {
            logger.info("Waiting for RMI broker service in Zeroconf.");
            connection = discovery.locate(id).get();
            return new RMIRegistry(connection);
        } catch (InterruptedException | ExecutionException | RemoteException e) {
            logger.error("Error when waiting for RMI Broker", e);
            System.exit(1);
            return null;
        }
    }
}
