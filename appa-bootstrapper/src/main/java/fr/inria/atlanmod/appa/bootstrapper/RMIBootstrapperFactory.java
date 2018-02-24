package fr.inria.atlanmod.appa.bootstrapper;

import fr.inria.atlanmod.appa.core.DHT;
import fr.inria.atlanmod.appa.core.Factory;
import fr.inria.atlanmod.appa.core.NamingService;
import fr.inria.atlanmod.appa.core.RegistryService;
import fr.inria.atlanmod.appa.datatypes.ConnectionDescription;
import fr.inria.atlanmod.appa.datatypes.Id;
import fr.inria.atlanmod.appa.datatypes.ServiceDescription;
import fr.inria.atlanmod.appa.datatypes.StringId;
import fr.inria.atlanmod.appa.rmi.RMIRegistry;
import fr.inria.atlanmod.appa.service.dht.DHTRegistry;
import fr.inria.atlanmod.appa.service.dht.DHTService;
import fr.inria.atlanmod.appa.service.rmi.*;
import fr.inria.atlanmod.appa.service.zeroconf.ZeroconfRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created on 05/04/2017.
 *
 * @author AtlanMod team.
 */
public class RMIBootstrapperFactory implements Factory {

    private Registry registry;
    private ZeroconfRegistry zeroconf = new ZeroconfRegistry();
    private Logger logger = LoggerFactory.getLogger(RMIBootstrapperFactory.class);

    public RMIBootstrapperFactory() throws RemoteException {
        registry = LocateRegistry.createRegistry(RMIRegistry.PORT);
        Id id = new StringId(RMIRegistry.NAME);
        ConnectionDescription cd = new ConnectionDescription(RMIRegistry.PORT);
        ServiceDescription sd = new ServiceDescription(cd, id);

        sd.protocol(RMIRegistry.PROTOCOL);
        zeroconf.start();
        zeroconf.publish(sd);
    }

    @Nonnull
    @Override
    public RegistryService createRegistry() {
        RegistryService result;
        RemoteDHT<Id, ServiceDescription> dht = new RemoteDHTService<>();
        try {
            RemoteDHT<Id, ServiceDescription> exported =
                    (RemoteDHT<Id, ServiceDescription>) UnicastRemoteObject.exportObject(dht, 0);
            registry.rebind(DHTService.NAME, exported);
            DHT<Id, ServiceDescription> adapter = new RemoteDHTAdapter(exported);
            result = new DHTRegistry(adapter);
        } catch (RemoteException e) {
            logger.warn("Remote error", e);
            result = null;
        }
        return result;
    }

    @Nonnull
    @Override
    public NamingService createNaming() {
        NamingService result;
        try {
            RemoteNaming naming = (RemoteNaming) UnicastRemoteObject.exportObject(new RemoteNamingService(), 0);
            registry.rebind(NamingService.NAME, naming);
            result = new RemoteNamingServiceAdapter(naming);
        }
        catch (RemoteException e) {
            logger.warn("RMI error", e);
            result = null;
        }
        return result;

    }

}
