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

package fr.inria.atlanmod.appa.service.dht;

import fr.inria.atlanmod.appa.core.DHT;
import fr.inria.atlanmod.appa.core.RegistryService;
import fr.inria.atlanmod.appa.datatypes.Id;
import fr.inria.atlanmod.appa.datatypes.ServiceDescription;
import fr.inria.atlanmod.appa.datatypes.StringId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@ParametersAreNonnullByDefault
public class DHTRegistry  implements RegistryService {

    @SuppressWarnings("JavaDoc")
    private Logger logger = LoggerFactory.getLogger(DHTRegistry.class);

    private final Id id = new StringId(RegistryService.NAME);

    private final DHT<Id, ServiceDescription> dht;

    public DHTRegistry(DHT<Id,ServiceDescription> dht ) {
        this.dht = dht;
    }

    @Override
    public void publish(ServiceDescription description) {
        logger.info("DHTRegistry::publishing({})",description);
        dht.put(description.id(), description);
    }

    @Override
    public void unpublish(ServiceDescription service) {

    }

    @Override
    public Future<ServiceDescription> locate(Id serviceId) {
        logger.info("DHTRegistry::locate({})",serviceId);
        ServiceDescription sd =  dht.get(serviceId);
        return new ReturnValue<>(sd);
    }

    @Nonnull
    @Override
    public ServiceDescription description() {
        return null;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void run() {

    }

    @ParametersAreNonnullByDefault
    class ReturnValue<V1> implements Future<V1> {

        private V1 value;

        public ReturnValue(V1 v) {
            value = v;
        }

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            return false;
        }

        @Override
        public boolean isCancelled() {
            return false;
        }

        @Override
        public boolean isDone() {
            return true;
        }

        @Override
        public V1 get() throws InterruptedException, ExecutionException {
            return value;
        }

        @Override
        public V1 get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return value;
        }
    }
}
