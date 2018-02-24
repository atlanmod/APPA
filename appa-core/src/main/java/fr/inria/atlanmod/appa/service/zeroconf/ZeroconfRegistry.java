package fr.inria.atlanmod.appa.service.zeroconf;

import com.google.common.collect.ImmutableMap;
import fr.inria.atlanmod.appa.core.RegistryService;
import fr.inria.atlanmod.appa.datatypes.ConnectionDescription;
import fr.inria.atlanmod.appa.datatypes.Id;
import fr.inria.atlanmod.appa.datatypes.ServiceDescription;
import fr.inria.atlanmod.appa.datatypes.StringId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.jmdns.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created on 04/04/2017.
 *
 * @author AtlanMod team.
 */
public class ZeroconfRegistry implements RegistryService {
    public static final String DOMAIN = "local";
    public static final String PROTOCOL = "tcp";
    public static final String APPLICATION = "appa";


    private static Logger logger = LoggerFactory.getLogger(ZeroconfRegistry.class);

    private final Map<String, ServiceDescription> descriptions = new ConcurrentHashMap<>();

    private JmDNS zeroconf;

    @Override
    public void publish(ServiceDescription service) {

        Map<ServiceInfo.Fields, String> nameMap = ImmutableMap.of(
                ServiceInfo.Fields.Protocol, PROTOCOL,
                ServiceInfo.Fields.Application, APPLICATION,
                ServiceInfo.Fields.Domain, DOMAIN,
                ServiceInfo.Fields.Instance, service.id().toString());

        Map<String,String> properties = ImmutableMap.of("protocol",service.protocol());

        ServiceInfo serviceInfo = ServiceInfo.create(nameMap, service.connection().port(), 0, 0, false, properties);

        try {
            zeroconf.registerService(serviceInfo);
        }
        catch (IOException e) {
            logger.warn("IO error when registering a service", e);
        }
    }

    @Override
    public void unpublish(ServiceDescription service) {

    }

    @Override
    public Future<ServiceDescription> locate(Id id) {
        return new Locate(id.toString());
    }

    @Nonnull
    @Override
    public ServiceDescription description() {
        return null;
    }

    @Override
    public void start() {
        logger.info("Starting Zeroconf registry");
        try {
            //zeroconf = JmDNS.create(InetAddress.getLocalHost());
            zeroconf = JmDNS.create();
            String type = String.format("_%s._%s.%s.",APPLICATION,PROTOCOL,DOMAIN);
            zeroconf.addServiceListener(type, new SampleListener());
        }
        catch (IOException e) {
            logger.error("Could not start Zeroconf registry",e);
        }
    }

    @Override
    public void stop() {
        zeroconf.unregisterAllServices();
    }

    @ParametersAreNonnullByDefault
    private class Locate implements Future<ServiceDescription> {

        private final String name;

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
        public ServiceDescription get() throws InterruptedException, ExecutionException {
            ServiceDescription connection = descriptions.get(name);
            while (connection == null) {
                synchronized (descriptions) {
                    descriptions.wait();
                }
                connection = descriptions.get(name);
            }
            return connection;
        }

        @Override
        public ServiceDescription get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            throw new UnsupportedOperationException("Locate::get(timeout,unit)");
        }
    }

    @ParametersAreNonnullByDefault
    private class SampleListener implements ServiceListener {

        @Override
        public void serviceAdded(ServiceEvent event) {
            logger.info("Service added: {}", event.getInfo());
        }

        @Override
        public void serviceRemoved(ServiceEvent event) {
            logger.info("Service removed: {}", event.getInfo());

            ServiceInfo info = event.getInfo();
            String name = info.getName();
            descriptions.remove(name);

        }

        @Override
        public void serviceResolved(ServiceEvent event) {
            logger.info("Service resolved: {}", event.getInfo());
            ServiceInfo info = event.getInfo();
            InetAddress address = info.getInetAddresses()[0];
            String protocol = info.getPropertyString("protocol");
            Id id = new StringId(info.getName());

            InetSocketAddress socketAddress = new InetSocketAddress(address, info.getPort());

            ConnectionDescription cd = new ConnectionDescription(socketAddress);
            ServiceDescription description = new ServiceDescription(cd, id);
            description.protocol(protocol);

            descriptions.put(info.getName(), description);
            synchronized (descriptions) {
                descriptions.notifyAll();
            }
        }
    }

}
