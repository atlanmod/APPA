/*
 * Copyright (c) 2016-2017 Atlanmod INRIA LINA Mines Nantes.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Atlanmod INRIA LINA Mines Nantes - initial API and implementation
 */

package fr.inria.atlanmod.appa.service.registry;

import fr.inria.atlanmod.appa.base.ZeroconfService;

import net.posick.mDNS.Lookup;
import net.posick.mDNS.MulticastDNSService;
import net.posick.mDNS.ServiceInstance;
import net.posick.mDNS.ServiceName;

import org.xbill.DNS.Name;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class MdnsjavaDiscoveryService {

    /**
     * Service time to live
     */
    private final static long SERVICE_TTL = MulticastDNSService.DEFAULT_SRV_TTL;

    private InetAddress host;

    public MdnsjavaDiscoveryService() {
        try {
            host = InetAddress.getLocalHost();
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        new MdnsjavaDiscoveryService().publish(new ZeroconfService() {
            @Nonnull
            @Override
            public String name() {
                return "registry";
            }

            @Nonnull
            @Override
            public String type() {
                return "_lala._tcp";
            }

            @Nonnegative
            @Override
            public int port() {
                return 99;
            }
        });

        Thread.sleep(30000);

        Lookup lookup;
        try {
            lookup = new Lookup(MulticastDNSService.DEFAULT_REGISTRATION_DOMAIN_NAME,
                    MulticastDNSService.REGISTRATION_DOMAIN_NAME,
                    MulticastDNSService.DEFAULT_BROWSE_DOMAIN_NAME,
                    MulticastDNSService.BROWSE_DOMAIN_NAME,
                    MulticastDNSService.SERVICES_NAME,
                    MulticastDNSService.LEGACY_BROWSE_DOMAIN_NAME);

            Lookup.Domain[] domains = lookup.lookupDomains();

            for (Lookup.Domain domain : domains) {
                System.out.println(domain);
            }

            lookup.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void publish(ZeroconfService s) {
        ServiceInstance registeredService = null;
        try {
            int priority = 0;
            int weight = 0;

            ServiceName serviceName = new ServiceName(s.type() + ".local.");
            Name hostname = new Name("testhost.local.");
            InetAddress[] addresses = {host};
            String[] txtValues = new String[]{""};

            MulticastDNSService mDNSService = new MulticastDNSService();
            ServiceInstance service = new ServiceInstance(serviceName, priority, weight, s.port(), hostname,
                    addresses, txtValues);
            registeredService = mDNSService.register(service);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        if (registeredService != null) {
            System.out.println("Services Successfully Registered: \n\t" + registeredService);
        }
        else {
            System.err.println("Services Registration Failed!");
        }
    }

}
