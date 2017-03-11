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

package fr.inria.atlanmod.appa.base;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Logger;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public abstract class Application {

    @SuppressWarnings("JavaDoc")
    private static final Logger logger = Logger.getGlobal();

    private final Registry registry;

    private final MessagingService messaging;

    private final List<Service> services = new ArrayList<>();

    public Application(Factory factory) {
        registry = factory.createRegistry();
        messaging = factory.createMessaging();

        addService(registry);
        addService(messaging);
    }

    public MessagingService getMessagingService() {
        return messaging;
    }

    public Registry getRegistry() {
        return registry;
    }

    protected void addService(Service service) {
        services.add(service);
    }

    public void start() {
        logger.info(String.format("starting with %d services", services.size()));
        assert registry != null;

        for (Service each : services) {
            each.start();
            try {
                registry.publish(each);
            }
            catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        ListIterator<Service> it = services.listIterator(services.size());
        Service each;

        while (it.hasPrevious()) {
            each = it.previous();
            each.stop();
            try {
                registry.unpublish(each);
            }
            catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
    }
}
