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

package fr.inria.atlanmod.appa.rmi;

import fr.inria.atlanmod.appa.datatypes.RamdomId;
import fr.inria.atlanmod.appa.service.DHTService;

import java.rmi.RemoteException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class DHTServiceFake implements DHTService<String, String> {

    private final RemoteMap<String, String> map;

    @SuppressWarnings("unchecked")
    public DHTServiceFake(RMIRegistry registry) {
        map = (RemoteMap<String, String>) registry.lookup(DHTService.NAME);
    }

    @Override
    public void put(String key, String value) {
        try {
            map.put(key, value);
        }
        catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Future<String> get(String key) {
        try {
            return new GetValue(map.get(key));
        }
        catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Nonnull
    @Override
    public RamdomId id() {
        return null;
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

    @Override
    public void run() {
    }

    @ParametersAreNonnullByDefault
    private static class GetValue implements Future<String> {

        private String value;

        public GetValue(String value) {
            this.value = value;
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
            return false;
        }

        @Override
        public String get() throws InterruptedException, ExecutionException {
            return value;
        }

        @Override
        public String get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return value;
        }
    }
}
