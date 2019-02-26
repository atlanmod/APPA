package fr.inria.atlanmod.appa.test.doubles;

import fr.inria.atlanmod.appa.core.NamingService;
import fr.inria.atlanmod.appa.datatypes.ConnectionDescription;
import fr.inria.atlanmod.appa.datatypes.Id;
import fr.inria.atlanmod.appa.datatypes.RamdomId;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 06/04/2017.
 *
 * @author AtlanMod team.
 */
public class FakeNaming implements NamingService {

    private Map<Id, ConnectionDescription> map = new HashMap<>();

    @Override

    public Id register(ConnectionDescription connection) {
        Id id = new RamdomId();
        map.put(id, connection);

        return id;
    }

    @Override
    public ConnectionDescription lookup(Id id) {
        return map.get(id);
    }
}
