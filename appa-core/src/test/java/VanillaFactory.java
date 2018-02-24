import fr.inria.atlanmod.appa.core.*;
import fr.inria.atlanmod.appa.datatypes.ConnectionDescription;
import fr.inria.atlanmod.appa.datatypes.Id;
import fr.inria.atlanmod.appa.datatypes.StringId;
import fr.inria.atlanmod.appa.kernel.AsyncScheduler;
import fr.inria.atlanmod.appa.kernel.Scheduler;
import fr.inria.atlanmod.appa.messaging.DefaultMessagingService;
import fr.inria.atlanmod.appa.messaging.Server;
import fr.inria.atlanmod.appa.messaging.nio.MessagingServer;

public class VanillaFactory implements Factory {

    private int port;
    private DHT dht;
    private Scheduler scheduler;

    public VanillaFactory(DHT dht, int port) {
        scheduler = new AsyncScheduler(3);
        new Thread(scheduler).start();
        this.dht = dht;
        this.port = port;
    }

    public MessagingService createMessaging() {

        ConnectionDescription cd = new ConnectionDescription(port + 58);
        Server s = new MessagingServer(cd.socket(), scheduler);
        Id id = new StringId("Messaging");
        MessagingService ms = new DefaultMessagingService(id, cd, s);
        scheduler.schedule(s);
        return ms;
    }

    public RegistryService createRegistry() {
        ConnectionDescription cd = new ConnectionDescription(port + 62);
        //Id id = new Id();
        //return new DHTRegistry(id, cd, dht);
        return null;
    }

    @Override
    public NamingService createNaming() {
        return null;
    }

}
