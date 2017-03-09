package fr.inria.atlanmod.appa.rmi;


import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import fr.inria.atlanmod.appa.datatypes.ConnectionDescription;
import fr.inria.atlanmod.appa.datatypes.Id;
import fr.inria.atlanmod.appa.datatypes.LongId;

import java.rmi.RemoteException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class RemoteNamingServer implements RemoteNaming {

    private Map<Id,ConnectionDescription> map = Collections.synchronizedMap(new HashMap<>());
    private AtomicLong lastValue = new AtomicLong(0);

    @Override
    public Id register(ConnectionDescription description) throws RemoteException {
        LongId newId = new LongId(lastValue.incrementAndGet());
        map.put(newId, description);

        return newId;
    }

    @Override
    public ConnectionDescription lookup(Id id) throws RemoteException {
        ConnectionDescription cd = map.get(id);
        return cd;
    }
}
