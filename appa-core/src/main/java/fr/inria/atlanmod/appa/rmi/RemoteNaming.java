package fr.inria.atlanmod.appa.rmi;

import fr.inria.atlanmod.appa.datatypes.ConnectionDescription;
import fr.inria.atlanmod.appa.datatypes.Id;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by sunye on 08/03/2017.
 */
public interface RemoteNaming extends Remote {

        Id register(ConnectionDescription description) throws RemoteException;

        ConnectionDescription lookup(Id id) throws RemoteException;
    }

