package fr.inria.atlanmod.appa.datatypes;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created on 15/03/2017.
 *
 * @author AtlanMod team.
 */
public class ServiceDescription implements Serializable {

    /**
     * This service's identifier.
     */
    private final Id id;

    /**
     * This service connection connection
     */
    private final ConnectionDescription connection;


    /**
     * Creates a Service Descriptiuon from a Connection Description and an Id.
     * @param cd The ConnectionDescription allowing clients to connect to the service
     * @param id The service identification.
     */
    public ServiceDescription(ConnectionDescription cd, Id id) {
        this.id = id;
        this.connection =cd;
    }



    /**
     * Returns the Id related to the service.
     */
    public Id id() {
        return id;
    }


    public ConnectionDescription connection() {
        return connection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceDescription that = (ServiceDescription) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(connection, that.connection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, connection);
    }

    private String protocol;
    public void protocol(String str) {
        protocol = str;
    }
    public String protocol() {
        return protocol;
    }

}
