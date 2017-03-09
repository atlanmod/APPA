package fr.inria.atlanmod.appa.rmi;


import java.rmi.RemoteException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RemoteMapServer<K,V> implements RemoteMap<K,V> {

    private Map<K,V> map = Collections.synchronizedMap(new HashMap<K,V>());

    @Override
    public void put(K key, V value) throws RemoteException {
        System.out.printf("adding value: "+value);
        map.put(key, value);
    }

    @Override
    public V get(K key) throws RemoteException {
        return map.get(key);
    }
}
