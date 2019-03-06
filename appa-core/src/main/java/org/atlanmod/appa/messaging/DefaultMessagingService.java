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

package org.atlanmod.appa.messaging;

import org.atlanmod.appa.core.Message;
import org.atlanmod.appa.core.MessagingService;
import org.atlanmod.appa.datatypes.ConnectionDescription;
import org.atlanmod.appa.datatypes.Id;
import org.atlanmod.appa.datatypes.ServiceDescription;
import org.atlanmod.appa.service.AbstractService;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class DefaultMessagingService extends AbstractService implements MessagingService {

    private final Server server;

    private Thread thread;

    public DefaultMessagingService(Id id, ConnectionDescription connection, Server server) {
        super(id, connection);
        this.server = server;
    }

    @Nonnull
    @Override
    public ServiceDescription description() {
        return null;
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
    public int port() {
        return 0;
    }


    @Nonnull
    @Override
    public ResponseHandler send(Message message, ConnectionDescription connection) {
        return server.send(message.toByteArray(), connection.socket());
    }
}
