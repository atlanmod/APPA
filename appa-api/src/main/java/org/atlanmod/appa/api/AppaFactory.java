package org.atlanmod.appa.api;

import javax.inject.Inject;

public class AppaFactory {

    @Inject
    private DHT dht;

    public DHT createDHT() {
        return dht;
    }
}
