/*
 * Created on 26 juil. 07
 *
 */
package fr.inria.atlanmod.appa.base;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Logger;

import fr.inria.atlanmod.appa.exception.OperationFailedException;

public abstract class Application {

    private Logger logger = Logger.global;

    private List<Service> services = new ArrayList<Service>();

    private Registry registry;

    private MessagingService messaging;

    public Application(Factory factory) {
        registry = factory.createRegistry();
        messaging = factory.createMessaging();

        assert registry != null : "Registry service was not created correctly";
        assert messaging != null : "Messaging service was not created correctly";

        this.addService(registry);
        this.addService(messaging);
    }

    public MessagingService getMessagingService() {
        return messaging;
    }

    public Registry getRegistry() {
        return registry;
    }

    protected void addService (Service s) {
        assert s != null : "Cannot add a null service";

        services.add(s);
    }

    public void start() {
        logger.info(String.format("starting with %d services", services.size()));
        assert registry != null;

        for (Service each : services) {
            each.start();
            try {
                registry.publish(each);
            } catch (OperationFailedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void stop() {

        ListIterator<Service> it = services.listIterator(services.size());
        Service each;

        while (it.hasPrevious()) {
            each = it.previous();
            each.stop();
            try {
                registry.unpublish(each);
            } catch (OperationFailedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


}
