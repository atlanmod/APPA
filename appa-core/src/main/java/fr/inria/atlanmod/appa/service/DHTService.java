package fr.inria.atlanmod.appa.service;


import java.io.Serializable;
import java.util.Set;

import fr.inria.atlanmod.appa.base.DHT;
import fr.inria.atlanmod.appa.base.Peer;
import fr.inria.atlanmod.appa.base.Service;
import fr.inria.atlanmod.appa.exception.ObjectNotFoundException;
import fr.inria.atlanmod.appa.exception.OperationFailedException;


/**
 * The interface defining the Distributed Hash Table (DHT) service.
 * @author  sunye
 */
public interface DHTService<K,V extends Serializable> extends DHT<K,V>, Service {

    /**
     * Service name
     */
     String NAME = "DHTService";

 }

