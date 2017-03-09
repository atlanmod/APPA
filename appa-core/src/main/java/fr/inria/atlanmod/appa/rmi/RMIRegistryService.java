package fr.inria.atlanmod.appa.rmi;


import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class RMIRegistryService extends RMIRegistry {


    @Override
    public void stop() {

    }

    public void start()  {
        try {
            registry = LocateRegistry.createRegistry(port());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
    }

}
