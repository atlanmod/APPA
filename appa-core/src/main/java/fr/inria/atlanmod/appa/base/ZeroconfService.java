package fr.inria.atlanmod.appa.base;


public interface ZeroconfService {

    /**
     * The name of the service
     * @return
     */
    public String name();

    /**
     * Type service type, used as a DNS-SD entry
     * @return
     */
    public String type();

    /**
     * The service port
     * @return
     */
    public int port();
}
