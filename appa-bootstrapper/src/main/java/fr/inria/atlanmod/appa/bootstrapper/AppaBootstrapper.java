package fr.inria.atlanmod.appa.bootstrapper;

import fr.inria.atlanmod.appa.core.Application;
import fr.inria.atlanmod.appa.core.Factory;

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
}
