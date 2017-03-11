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

package fr.inria.atlanmod.appa.service.dht.mock;

import fr.inria.atlanmod.appa.core.DHT;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class LocalDHT implements DHT<String, Serializable> {

    @SuppressWarnings("JavaDoc")
    private static Logger logger = Logger.getLogger("appa.dht.mock");

    private final long delay;

    private final Map<String, Serializable> map = new HashMap<>();

    public LocalDHT(long delay) {
        this.delay = delay;
    }

    @Override
    public void put(String key, Serializable value) {
        logger.info(String.format("Putting [%s] = %s", key, value));

        delay();
        map.put(key, value);
    }

    @Override
    public Future<Serializable> get(String key) {
        delay();

        if (map.containsKey(key)) {
            return new ReturnValue<>(map.get(key));
        }
        else {
            throw new NoSuchElementException();
        }
    }

    private synchronized void delay() {
        try {
            this.wait(delay);
        }
        catch (InterruptedException e) {
            throw new NoSuchElementException();
        }
    }

    @ParametersAreNonnullByDefault
    private static class ReturnValue<V> implements Future<V> {

        private V value;

        public ReturnValue(V v) {
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
        public V get() {
            return value;
        }

        @Override
        public V get(long timeout, TimeUnit unit) {
            return value;
        }
    }
}
