import fr.inria.appa.base.Application;
import fr.inria.appa.base.DHT;
import fr.inria.appa.messaging.ResponseHandler;
import fr.inria.appa.messaging.StringMessage;
import fr.inria.appa.service.dht.mock.LocalDHT;

/*
 * Created on 2 ao�t 07
 *
 */

public class VanillaRun {

    Application peer1;
    Application peer2;
    DHT dht = new LocalDHT(100);

    public VanillaRun() {
        peer1 = new VanillaApplication(new VanillaFactory(dht, 1100));
        peer2 = new VanillaApplication(new VanillaFactory(dht, 1200));
     }

    public synchronized void run() {
        peer1.start();
        peer2.start();
    }

    public void send() {
        int size = 10;
        ResponseHandler[] handlers = new ResponseHandler[size];


        ResponseHandler h1 = peer1.getMessagingService().send(new StringMessage("Hello"), peer2.getMessagingService().getConnectionDescription());
        ResponseHandler h2 = peer2.getMessagingService().send(new StringMessage("World"), peer1.getMessagingService().getConnectionDescription());

        System.out.println("The answer 1 is " + h1.waitForResponse());
        System.out.println("The answer 2 is " + h2.waitForResponse());

        for (int i = 0; i < handlers.length; i++) {
            handlers[i] = peer1.getMessagingService().send(new StringMessage(new Integer(i).toString()), peer2.getMessagingService().getConnectionDescription());
        }

        for (int i = 0; i < handlers.length; i++) {
            System.out.println("The answer is: " + handlers[i].waitForResponse());
        }
    }

    public static void main(String[] argv) {
        VanillaRun vanilla = new VanillaRun();
        vanilla.run();
        vanilla.send();

    }


}
