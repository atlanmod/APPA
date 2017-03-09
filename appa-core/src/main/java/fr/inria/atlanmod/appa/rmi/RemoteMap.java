package fr.inria.atlanmod.appa.rmi;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteMap<K,V> extends Remote {
        public void put(K key, V value) throws RemoteException;
        public V get(K key) throws RemoteException;
}
