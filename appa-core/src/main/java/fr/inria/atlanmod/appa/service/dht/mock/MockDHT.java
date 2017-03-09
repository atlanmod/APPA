package fr.inria.atlanmod.appa.service.dht.mock;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MockDHT extends Remote {
        public void put(String k, Serializable v) throws RemoteException;
        public Serializable get(String k) throws RemoteException;
}
