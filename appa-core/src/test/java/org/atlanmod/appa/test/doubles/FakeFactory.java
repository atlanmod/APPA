package org.atlanmod.appa.test.doubles;

import org.atlanmod.appa.core.DHT;
import org.atlanmod.appa.core.Factory;
import org.atlanmod.appa.core.NamingService;
import org.atlanmod.appa.core.RegistryService;
import org.atlanmod.appa.datatypes.Id;
import org.atlanmod.appa.datatypes.ServiceDescription;

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
