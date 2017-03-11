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

package fr.inria.atlanmod.appa;

import fr.inria.atlanmod.appa.base.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class Node {

    private List<Service> services = new ArrayList<>();
    private ExecutorService executor = Executors.newFixedThreadPool(10);

    public final void start() {
        this.beforeStarting();
        this.startDiscovery();
        this.startBroker();
        this.startDHT();
        this.startNaming();
        this.afterStarting();
    }

    protected void execute(Service s) {
        s.start();
        services.add(s);
        executor.execute(s);
    }

    protected void beforeStarting() {}

    protected void startDiscovery() {}

    protected void startBroker() {}

    protected void startDHT() {}

    protected void startNaming() {}

    protected void afterStarting() {}

    public void shutdown() {
        executor.shutdown();
    }
}
