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

package org.atlanmod.appa.messaging.nio;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.nio.channels.Selector;

/**
 * Sender <-> Reader
 * <p>
 * Receiver <-> Answerer
 */
@ParametersAreNonnullByDefault
public abstract class AbstractHandler implements Handler {

    @Nonnull
    protected final MessagingServer messagingServer;

    public AbstractHandler(MessagingServer messagingServer) {
        this.messagingServer = messagingServer;
    }

    protected Selector getSelector() {
        return messagingServer.getSelector();
    }

    protected void schedule(Runnable r) {
        messagingServer.schedule(r);
    }
}
