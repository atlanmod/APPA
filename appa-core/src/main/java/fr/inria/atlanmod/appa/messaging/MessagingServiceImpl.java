/*
 * Created on 2 ao�t 07
 *
 */
package fr.inria.atlanmod.appa.messaging;

import fr.inria.atlanmod.appa.base.Message;
import fr.inria.atlanmod.appa.base.MessagingService;
import fr.inria.atlanmod.appa.datatypes.ConnectionDescription;
import fr.inria.atlanmod.appa.datatypes.RamdomId;
import fr.inria.atlanmod.appa.service.AbstractService;

public class MessagingServiceImpl extends AbstractService implements MessagingService {

    final private Server server;
    private Thread thread;

    public MessagingServiceImpl(RamdomId id, ConnectionDescription cd, Server s) {
        super(id, cd);
        server = s;
    }

    public void start() {
        thread = new Thread(server);
    }

    public void stop() {
       thread.interrupt();
    }

    @Override
    public void run() {

    }

    @Override
    public int port() {
        return 0;
    }

    public ResponseHandler send(Message msg, ConnectionDescription cd) {
        return server.send(msg.toByteArray(), cd.getSocketAddress());

    }


}
