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

package fr.inria.atlanmod.appa.messaging;

import fr.inria.atlanmod.appa.core.Message;
import fr.inria.atlanmod.appa.core.MessagingService;
import fr.inria.atlanmod.appa.datatypes.ConnectionDescription;
import fr.inria.atlanmod.appa.datatypes.RamdomId;
import fr.inria.atlanmod.appa.service.AbstractService;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class DefaultMessagingService extends AbstractService implements MessagingService {

    private final Server server;

    private Thread thread;

    public DefaultMessagingService(RamdomId id, ConnectionDescription description, Server server) {
        super(id, description);
        this.server = server;
    }

    @Override
    public void start() {
        thread = new Thread(server);
    }

    @Override
    public void stop() {
        thread.interrupt();
    }

    @Nonnegative
    @Override
    public int port() {
        return 0;
    }

    @Override
    public void run() {

    }

    @Nonnull
    @Override
    public ResponseHandler send(Message message, ConnectionDescription description) {
        return server.send(message.toByteArray(), description.getHost());
    }
}
