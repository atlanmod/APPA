package fr.inria.atlanmod.appa.rmi;

import fr.inria.atlanmod.appa.base.Service;
import fr.inria.atlanmod.appa.base.ZeroconfService;
import fr.inria.atlanmod.appa.datatypes.RamdomId;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.Objects;


public abstract class RMIRegistry implements Service, ZeroconfService {
    public static final String NAME = "rmiregistry";

    public static final String TYPE = "_jrmp._tcp.local.";
    public static final int PORT = Registry.REGISTRY_PORT;


    protected Registry registry;

    @Override
    public RamdomId id() {
        return null;
    }

    @Override
    public String type() {
        return TYPE;
    }

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public int port() {
        return PORT;
    }

    public void rebind(String name, Remote object) {
        assert Objects.nonNull(registry);

        try {
            registry.rebind(name,object);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public Remote lookup(String str) {
        Remote result = null;
        int times =0;

        while (times < 5 && result == null) {
            try {
                result = registry.lookup(str);

            } catch (NotBoundException ex) {
            } catch (AccessException ex) {
            } catch (RemoteException ex) {
            }
            times++;
            if (result == null) {
                try {
                    Thread.sleep(300*times);
                } catch (InterruptedException e) {
                }
            }

        }
        return result;
    }
}
