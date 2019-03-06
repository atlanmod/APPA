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

package org.atlanmod.appa.test.doubles;

import org.atlanmod.appa.core.DHT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@ParametersAreNonnullByDefault
public class FakeDHT<K extends Serializable, V extends Serializable> implements DHT<K, V> {


    private static Logger logger = LoggerFactory.getLogger(FakeDHT.class);

    private final long delay;

    private final Map<K, V> map = Collections.synchronizedMap(new HashMap<K, V>());

    public FakeDHT(long delay) {
        this.delay = delay;
    }

    public FakeDHT() {
        this.delay = 0;
    }

    @Override
    public void put(K key, V value) {
        logger.info("Putting [{}] = {}", key, value);

        delay();
        map.put(key, value);
    }

    @Override
    public V get(K key) {
        assert Objects.nonNull(key);

        logger.info("Getting value at {}", key);
        delay();

        if (map.containsKey(key)) {
            return map.get(key);
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public void remove(K key) {
        assert nonNull(key);

        logger.info("Removing value at {}", key);
        delay();

        V value = map.remove(key);
        if (isNull(value)) {
            throw new NoSuchElementException();
        }
    }

    private synchronized void delay() {
        try {
            this.wait(delay);
        } catch (InterruptedException e) {
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
