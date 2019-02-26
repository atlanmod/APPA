package fr.inria.atlanmod.appa.test.doubles;

import fr.inria.atlanmod.appa.core.DHT;
import fr.inria.atlanmod.appa.core.Factory;
import fr.inria.atlanmod.appa.core.NamingService;
import fr.inria.atlanmod.appa.core.RegistryService;
import fr.inria.atlanmod.appa.datatypes.Id;
import fr.inria.atlanmod.appa.datatypes.ServiceDescription;

import javax.annotation.Nonnull;

/**
 * Created on 04/04/2017.
 *
 * @author AtlanMod team.
 */
public class FakeFactory implements Factory {


    private DHT<Id, ServiceDescription> dht = new FakeDHT<>();

    @Nonnull
    @Override
    public RegistryService createRegistry() {
        /*
       RegistryService registry = new DHTRegistry(dht);
       return registry;
       */

        return null;
    }

    @Nonnull
    @Override
    public NamingService createNaming() {
        return new FakeNaming();
    }


}
