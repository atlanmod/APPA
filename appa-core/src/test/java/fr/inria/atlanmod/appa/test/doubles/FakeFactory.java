package fr.inria.atlanmod.appa.test.doubles;

import fr.inria.atlanmod.appa.core.*;
import fr.inria.atlanmod.appa.datatypes.Id;
import fr.inria.atlanmod.appa.datatypes.ServiceDescription;
import fr.inria.atlanmod.appa.service.dht.DHTRegistry;

import javax.annotation.Nonnull;

/**
 * Created on 04/04/2017.
 *
 * @author AtlanMod team.
 */
public class FakeFactory implements Factory {

    private  DHT<Id,ServiceDescription> dht = new FakeDHT<>();

    @Nonnull
    @Override
    public RegistryService createRegistry() {
       RegistryService registry = new DHTRegistry(dht);
       return registry;
    }

    @Nonnull
    @Override
    public NamingService createNaming() {
        return new FakeNaming();
    }


}
