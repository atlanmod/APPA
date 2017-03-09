/*
 * Created on 2 aot 07
 *
 */
package fr.inria.atlanmod.appa.base;

import java.io.Serializable;
import java.util.concurrent.Future;

import fr.inria.atlanmod.appa.exception.ObjectNotFoundException;
import fr.inria.atlanmod.appa.exception.OperationFailedException;

public interface DHT<K,V extends Serializable> {

    /**
     * Stores an object in the Hashtable
     */
    void put(K key,  V value) throws OperationFailedException;

    /**
     * Gets an object from the Hashtable
     * @throws ObjectNotFoundException
     * @throws OperationFailedException
     */
    Future<V> get(K key) throws OperationFailedException, ObjectNotFoundException;

}
