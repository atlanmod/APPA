package fr.inria.atlanmod.appa.datatypes;

import java.net.InetSocketAddress;
import java.util.Objects;

/**
 * Created on 15/03/2017.
 *
 * @author AtlanMod team.
 */
public class ServiceDescription extends ConnectionDescription {

    /**
     * This service's identifier.
     */
    private final Id id;

    /**
     * This service protocol
     */
    private final String protocol;


    public ServiceDescription(InetSocketAddress ip, Id id, String protocol) {
        super(ip);
        this.id = id;
        this.protocol = protocol;
    }

    public ServiceDescription(int port, Id id, String protocol){
        super(port);
        this.id = id;
        this.protocol = protocol;
    }


    /**
     * Returns the Id related to the service.
     */
    public Id id() {
        return id;
    }

    public String protocol() {
        return protocol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceDescription that = (ServiceDescription) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(protocol, that.protocol) &&
                Objects.equals(ip(), that.ip());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, protocol);
    }
}
