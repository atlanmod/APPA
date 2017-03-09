package fr.inria.atlanmod.appa.rmi;

import fr.inria.atlanmod.appa.datatypes.ConnectionDescription;
import fr.inria.atlanmod.appa.service.DHTService;

import java.net.InetSocketAddress;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * Created by sunye on 08/03/2017.
 */
public class RMIRegistryClient extends RMIRegistry {
    public RMIRegistryClient(ConnectionDescription description) {
        InetSocketAddress socketAddress = description.getSocketAddress();
        try {
            registry = LocateRegistry.getRegistry(socketAddress.getHostName(),
                    socketAddress.getPort());
            System.out.printf("rmi registry found: "+description);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void run() {

    }
}
