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

import fr.inria.atlanmod.appa.base.Service;
import fr.inria.atlanmod.appa.base.ZeroconfService;
import fr.inria.atlanmod.appa.datatypes.ConnectionDescription;
import fr.inria.atlanmod.appa.datatypes.RamdomId;
import fr.inria.atlanmod.appa.rmi.RMIRegistryService;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;

@ParametersAreNonnullByDefault
public class JmdnsJavaDiscoveryService implements Service {

    private final Map<String, ConnectionDescription> services = new ConcurrentHashMap<>();

    private JmDNS zeroconf;

    public static void main(String[] args) throws InterruptedException {
        new JmdnsJavaDiscoveryService().publish(new RMIRegistryService());
        Thread.sleep(30000);
    }

    @Nonnull
    @Override
    public RamdomId id() {
        return null;
    }

    @Override
    public void start() {
        try {
            zeroconf = JmDNS.create(InetAddress.getLocalHost());
            zeroconf.addServiceListener("_jrmp._tcp.local.", new SampleListener());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
    }

    @Nonnegative
    @Override
    public int port() {
        return 0;
    }

    public void publish(ZeroconfService s) {
        ServiceInfo serviceInfo = ServiceInfo.create(s.type(), s.name(), s.port(), "");
        try {
            zeroconf.registerService(serviceInfo);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Future<ConnectionDescription> locate(String name) {
        return new Locate(name);
    }

    @Override
    public void run() {
    }

    @ParametersAreNonnullByDefault
    private class Locate implements Future<ConnectionDescription> {

        private final String name;

        public Locate(String name) {
            this.name = name;
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
        public ConnectionDescription get() throws InterruptedException, ExecutionException {
            ConnectionDescription description = services.get(name);
            while (description == null) {
                synchronized (services) {
                    services.wait();
                }
                description = services.get(name);
            }
            return description;
        }

        @Override
        public ConnectionDescription get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return null;
        }
    }

    @ParametersAreNonnullByDefault
    private class SampleListener implements ServiceListener {

        @Override
        public void serviceAdded(ServiceEvent event) {
            System.out.println("Service added: " + event.getInfo());
        }

        @Override
        public void serviceRemoved(ServiceEvent event) {
            ServiceInfo info = event.getInfo();
            String name = info.getName();
            services.remove(name);

            System.out.println("Service removed: " + event.getInfo());
        }

        @Override
        public void serviceResolved(ServiceEvent event) {
            System.out.println("Service resolved: " + event.getInfo());
            ServiceInfo info = event.getInfo();
            InetAddress address = info.getInetAddresses()[0];
            int port = info.getPort();

            ConnectionDescription description = new ConnectionDescription(address, port);
            services.put(info.getName(), description);
            synchronized (services) {
                services.notifyAll();
            }
        }
    }
}
