/*
 * Created on 26 juil. 07
 *
 */
package fr.inria.atlanmod.appa.service.registry;

import java.util.logging.Logger;

import fr.inria.atlanmod.appa.base.DHT;
import fr.inria.atlanmod.appa.base.Registry;
import fr.inria.atlanmod.appa.base.Service;
import fr.inria.atlanmod.appa.datatypes.ConnectionDescription;
import fr.inria.atlanmod.appa.datatypes.RamdomId;
import fr.inria.atlanmod.appa.exception.ObjectNotFoundException;
import fr.inria.atlanmod.appa.exception.OperationFailedException;
import fr.inria.atlanmod.appa.service.AbstractService;

public class DHTRegistry extends AbstractService implements Registry {

    private Logger logger = Logger.global;

    DHT dht;

    public DHTRegistry(RamdomId id, ConnectionDescription cd, DHT service) {
        super(id, cd);
        assert service != null;
        dht = service;
    }

    @Override
    public void run() {

    }

    public ConnectionDescription locate(RamdomId id) throws OperationFailedException, ObjectNotFoundException {
        ConnectionDescription cd = (ConnectionDescription) dht.get(id.toString());
        return cd;

    }

    public void publish(Service s) throws OperationFailedException {
        logger.info("publishing");
        //dht.put(s.id().toString(), s.getConnectionDescription());
   }

    public void unpublish(Service s) {
        // TODO Auto-generated method stub

    }

    public void start() {
        // TODO Auto-generated method stub

    }

    public void stop() {
        // TODO Auto-generated method stub

    }

    @Override
    public int port() {
        return 0;
    }

    public ConnectionDescription getConnexionDescription() {
        // TODO Auto-generated method stub
        return null;
    }

}
