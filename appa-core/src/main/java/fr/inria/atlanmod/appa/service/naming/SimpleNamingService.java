package fr.inria.atlanmod.appa.service.naming;

import fr.inria.atlanmod.appa.core.NamingService;
import fr.inria.atlanmod.appa.datatypes.ConnectionDescription;
import fr.inria.atlanmod.appa.datatypes.Id;
import fr.inria.atlanmod.appa.datatypes.LongId;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created on 05/04/2017.
 *
 * @author AtlanMod team.
 */
public class SimpleNamingService implements NamingService {

    /**
     * Mapping containing all registered nodes
     */
    private final Map<Id, ConnectionDescription> map = new ConcurrentHashMap<>();

    /**
     * Last registered node integer identifier.
     */
    private final AtomicLong lastValue = new AtomicLong(0);

    /**
     * Registers a node and returns a new identifier for the node.
     *
     * @param connection
     * @return
     */
    @Override
    public Id register(ConnectionDescription connection)  {
        LongId newId = new LongId(lastValue.incrementAndGet());
        map.put(newId, connection);
        return newId;
    }

    /**
     * Returns a connection connection for a given node identifier.
     * @param id
     * @return
     */
    @Override
    public ConnectionDescription lookup(Id id)  {
        return map.get(id);
    }

}
