/*
 * Copyright (c) 2016-2017 Atlanmod INRIA LINA Mines Nantes.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Atlanmod INRIA LINA Mines Nantes - initial API and implementation
 */

package fr.inria.atlanmod.appa.service.registry;

import fr.inria.atlanmod.appa.base.DHT;
import fr.inria.atlanmod.appa.base.Registry;
import fr.inria.atlanmod.appa.base.Service;
import fr.inria.atlanmod.appa.datatypes.ConnectionDescription;
import fr.inria.atlanmod.appa.datatypes.RamdomId;
import fr.inria.atlanmod.appa.service.AbstractService;

import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import javax.annotation.Nonnegative;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class DHTRegistry extends AbstractService implements Registry {

    @SuppressWarnings("JavaDoc")
    private static final Logger logger = Logger.getGlobal();

    private final DHT<String, ConnectionDescription> dht;

    public DHTRegistry(RamdomId id, ConnectionDescription cd, DHT<String, ConnectionDescription> dht) {
        super(id, cd);

        this.dht = dht;
    }

    @Override
    public void run() {
    }

    public ConnectionDescription locate(RamdomId id) throws ExecutionException, InterruptedException {
        return dht.get(id.toString()).get();
    }

    @Override
    public void publish(Service service) {
        logger.info("publishing");
        //dht.put(s.id().toString(), s.getConnectionDescription());
    }

    @Override
    public void unpublish(Service service) {
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
    }

    @Nonnegative
    @Override
    public int port() {
        return 0;
    }

    public ConnectionDescription getConnexionDescription() {
        return null;
    }
}
