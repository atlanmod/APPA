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

package fr.inria.atlanmod.appa.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

@ParametersAreNonnullByDefault
public abstract class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    private final RegistryService registry;
    private final List<Service> services = new ArrayList<>();
    private MessagingService messaging;
    private NamingService naming;

    public Application(Factory factory) {
        registry = factory.createRegistry();
        naming = factory.createNaming();
        //messaging = factory.createMessaging();

        //addService(registry);
        //addService(messaging);
    }

    public MessagingService getMessagingService() {
        return messaging;
    }

    public RegistryService getRegistry() {
        return registry;
    }

    public NamingService getNaming() {
        return naming;
    }

    protected void addService(Service service) {
        services.add(service);
    }

    public void start() {
        assert registry != null;
        this.beforeStart();

        logger.info(String.format("Appa starting with %d services", services.size()));
        for (Service each : services) {
            each.start();
            this.getRegistry().publish(each.description());
        }
        this.afterStart();
    }

    public void stop() {
        ListIterator<Service> it = services.listIterator(services.size());
        Service each;

        while (it.hasPrevious()) {
            each = it.previous();
            each.stop();
            try {
                //registry.unpublish(each);
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
    }

    protected void beforeStart() {
    }

    ;

    protected void afterStart() {
    }

    ;
}
