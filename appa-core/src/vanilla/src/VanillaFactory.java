/*
 * Created on 2 ao�t 07
 *
 */

import java.net.InetSocketAddress;

import fr.inria.appa.base.DHT;
import fr.inria.appa.base.Factory;
import fr.inria.appa.base.MessagingService;
import fr.inria.appa.base.Registry;
import fr.inria.appa.datatypes.ConnectionDescription;
import fr.inria.appa.datatypes.Id;
import fr.inria.appa.kernel.Schedule;
import fr.inria.appa.messaging.MessagingServiceImpl;
import fr.inria.appa.messaging.Server;
import fr.inria.appa.messaging.nio.MessagingServer;
import fr.inria.appa.service.registry.DHTRegistry;

public class VanillaFactory implements Factory {

    private int port;
    private DHT dht;
    private Schedule schedule;

    public VanillaFactory(DHT dht, int port) {
        schedule = new Schedule(3);
        new Thread(schedule).start();
        this.dht = dht;
        this.port = port;
    }

    public MessagingService createMessaging() {
    
        ConnectionDescription cd = new ConnectionDescription(port+58);
        Server s = new MessagingServer(cd.getSocketAddress(), schedule);
        Id id = new Id();
        MessagingService ms = new MessagingServiceImpl(id, cd, s);
        schedule.execute(s);
        return ms;
    }

    public Registry createRegistry() {
        ConnectionDescription cd = new ConnectionDescription(port+62);
        Id id = new Id();
        return new DHTRegistry(id, cd, dht);
    }

}
