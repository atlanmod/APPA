package fr.inria.mock;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by sunye on 07/03/2017.
 */
public abstract class RMIService {
    private Registry registry;

    public RMIService(String brokerAdress) {
        try {
            registry = LocateRegistry.getRegistry(brokerAdress);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public Registry registry() {
        return registry;
    }
}
