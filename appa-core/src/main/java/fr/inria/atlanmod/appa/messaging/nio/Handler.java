/*
 * Created on 2 ao�t 07
 *
 */
package fr.inria.atlanmod.appa.messaging.nio;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

/**
 * @author sunye
 *
 *  * Sender <-> Reader
 *
 * Receiver <-> Answerer
 *
 */
public abstract class Handler  {

    protected final MessagingServer messaging;

    public Handler(MessagingServer ms) {
        assert ms != null;

        messaging = ms;
    }

    public abstract void run(SelectionKey key) throws IOException;

    protected Selector getSelector() {
        Selector s = messaging.getSelector();
        return s;
    }

    protected void schedule(Runnable r) {
        messaging.schedule(r);
    }
}
