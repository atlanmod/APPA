import fr.inria.atlanmod.appa.core.Application;
import fr.inria.atlanmod.appa.core.DHT;
import fr.inria.atlanmod.appa.messaging.ResponseHandler;
import fr.inria.atlanmod.appa.test.doubles.FakeDHT;

public class VanillaRun {

    Application peer1;
    Application peer2;
    DHT dht = new FakeDHT(100);

    public VanillaRun() {
        peer1 = new VanillaApplication(new VanillaFactory(dht, 1100));
        peer2 = new VanillaApplication(new VanillaFactory(dht, 1200));
    }

    public static void main(String[] argv) {
        VanillaRun vanilla = new VanillaRun();
        vanilla.run();
        vanilla.send();

    }

    public synchronized void run() {
        peer1.start();
        peer2.start();
    }

    public void send() {
        int size = 10;
        ResponseHandler[] handlers = new ResponseHandler[size];


        //ResponseHandler h1 = peer1.getMessagingService().send(new StringMessage("Hello"), peer2.getMessagingService().getConnectionDescription());
        //ResponseHandler h2 = peer2.getMessagingService().send(new StringMessage("World"), peer1.getMessagingService().getConnectionDescription());

        //System.out.println("The answer 1 is " + h1.waitForResponse());
        //System.out.println("The answer 2 is " + h2.waitForResponse());

        for (int i = 0; i < handlers.length; i++) {
            //handlers[i] = peer1.getMessagingService().send(new StringMessage(new Integer(i).toString()), peer2.getMessagingService().getConnectionDescription());
        }

        for (int i = 0; i < handlers.length; i++) {
            System.out.println("The answer is: " + handlers[i].waitForResponse());
        }
    }


}
