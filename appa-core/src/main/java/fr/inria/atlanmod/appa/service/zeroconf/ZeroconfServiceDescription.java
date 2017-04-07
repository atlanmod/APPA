package fr.inria.atlanmod.appa.service.zeroconf;

import javax.annotation.Nonnull;

/**
 * Created on 04/04/2017.
 *
 * @author AtlanMod team.
 */
public class ZeroconfServiceDescription implements ZeroconfService {

    public static final String TYPE = "_jrmp._tcp.local.";

    private final String name;
    private final int port;

    public ZeroconfServiceDescription(String name, int port) {
        this.name = name;
        this.port = port;
    }

    @Nonnull
    @Override
    public String name() {
        return name;
    }

    @Nonnull
    @Override
    public String type() {
        return TYPE;
    }

    @Override
    public int port() {
        return port;
    }
}
