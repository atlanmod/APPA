package fr.inria.atlanmod.appa.service.registry;

import fr.inria.atlanmod.appa.base.Service;
import fr.inria.atlanmod.appa.base.ZeroconfService;
import fr.inria.atlanmod.appa.datatypes.ConnectionDescription;
import fr.inria.atlanmod.appa.datatypes.RamdomId;
import fr.inria.atlanmod.appa.exception.OperationFailedException;
import fr.inria.atlanmod.appa.rmi.RMIRegistryService;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class JmdnsJavaDiscoveryService implements Service {

    private JmDNS zeroconf;
    private Map<String, ConnectionDescription> services = Collections.synchronizedMap(new HashMap<String,ConnectionDescription>());

    public JmdnsJavaDiscoveryService() {

    }

    @Override
    public RamdomId id() {
        return null;
    }

    @Override
    public void start() {
        try {
            this.zeroconf = JmDNS.create(InetAddress.getLocalHost());
            zeroconf.addServiceListener("_jrmp._tcp.local.", new SampleListener());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {

    }

    @Override
    public int port() {
        return 0;
    }





    public void publish(ZeroconfService s) throws OperationFailedException {
        ServiceInfo serviceInfo = ServiceInfo.create(s.type(), s.name(), s.port(), "");
        try {
            zeroconf.registerService(serviceInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Future<ConnectionDescription> locate(String name) {
        return new Locate(name);
    }

    @Override
    public void run() {

    }


    private class Locate implements Future<ConnectionDescription> {
        private String name;


        public Locate(String name) {
            this.name = name;
        }

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            return false;
        }

        @Override
        public boolean isCancelled() {
            return false;
        }

        @Override
        public boolean isDone() {
            return false;
        }

        @Override
        public ConnectionDescription get() throws InterruptedException, ExecutionException {
            ConnectionDescription description =  services.get(name);
            while (description == null) {
                synchronized (services) {
                    services.wait();
                }
                description =  services.get(name);
            }
            return description;
        }

        @Override
        public ConnectionDescription get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return null;
        }
    }

    private class SampleListener implements ServiceListener {
        @Override
        public void serviceAdded(ServiceEvent event) {
            System.out.println("Service added: " + event.getInfo());
        }

        @Override
        public void serviceRemoved(ServiceEvent event) {
            ServiceInfo info = event.getInfo();
            String name = info.getName();
            services.remove(name);

            System.out.println("Service removed: " + event.getInfo());
        }

        @Override
        public void serviceResolved(ServiceEvent event) {
            System.out.println("Service resolved: " + event.getInfo());
            ServiceInfo info = event.getInfo();
            InetAddress address = info.getInetAddresses()[0];
            int port = info.getPort();

            ConnectionDescription description = new ConnectionDescription(address,port);
            services.put(info.getName(),description);
            synchronized (services) {
                services.notifyAll();
            }



        }
    }

    public static void main(String[] args) throws InterruptedException, OperationFailedException {

        new JmdnsJavaDiscoveryService().publish(new RMIRegistryService());
            Thread.sleep(30000);
    }
}
