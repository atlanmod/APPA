package fr.inria.atlanmod.appa.service.registry;


import fr.inria.atlanmod.appa.base.Service;
import fr.inria.atlanmod.appa.base.ZeroconfService;
import fr.inria.atlanmod.appa.exception.OperationFailedException;
import net.posick.mDNS.Lookup;
import net.posick.mDNS.MulticastDNSService;
import net.posick.mDNS.ServiceInstance;
import net.posick.mDNS.ServiceName;
import org.xbill.DNS.DClass;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.Type;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 */
public class MdnsjavaDiscoveryService {

    /**
     * Service time to live
     */
    private final static long SERVICE_TTL = MulticastDNSService.DEFAULT_SRV_TTL;

    private InetAddress host;


    public MdnsjavaDiscoveryService() {
        try {
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void publish(ZeroconfService s) throws OperationFailedException {
        ServiceInstance registeredService = null;
        try {
            int priority = 0;
            int weight = 0;

            ServiceName serviceName = new ServiceName(s.type() + ".local.");
            Name hostname = new Name("testhost.local.");
            InetAddress[] addresses = {host};
            String[] txtValues = new String[] {""};

            MulticastDNSService mDNSService = new MulticastDNSService();
            ServiceInstance service = new ServiceInstance(serviceName, priority, weight, s.port(), hostname,
                     addresses, txtValues);
            registeredService = mDNSService.register(service);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (registeredService != null)
        {
            System.out.println("Services Successfully Registered: \n\t" + registeredService);
        } else
        {
            System.err.println("Services Registration Failed!");
        }
    }

    public static void main(String[] args) throws OperationFailedException, InterruptedException {

        new MdnsjavaDiscoveryService().publish(new ZeroconfService() {
            @Override
            public String name() {
                return "registry";
            }

            @Override
            public String type() {
                return "_lala._tcp";
            }

            @Override
            public int port() {
                return 99;
            }
        });

        Thread.sleep(30000);

        Lookup lookup = null;
        try {
            lookup = new Lookup(MulticastDNSService.DEFAULT_REGISTRATION_DOMAIN_NAME,
                    MulticastDNSService.REGISTRATION_DOMAIN_NAME,
                    MulticastDNSService.DEFAULT_BROWSE_DOMAIN_NAME,
                    MulticastDNSService.BROWSE_DOMAIN_NAME,
                    MulticastDNSService.SERVICES_NAME,
                    MulticastDNSService.
                            LEGACY_BROWSE_DOMAIN_NAME);

            Lookup.Domain[] domains = lookup.lookupDomains();
            for (Lookup.Domain domain : domains) {
                System.out.println(domain);
            }
            lookup.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
