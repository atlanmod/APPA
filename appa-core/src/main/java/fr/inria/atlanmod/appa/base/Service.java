/*
 * Created on 28 juin 07
 *
 */
package fr.inria.atlanmod.appa.base;

import fr.inria.atlanmod.appa.datatypes.RamdomId;

public interface Service extends Runnable {

    /**
     *
     * @return this service unique identification
     */
    public RamdomId id();

    /**
     * Starts this service
     */
    public void start();

    /**
     * Stops this service
     */
    public void stop();

    /**
     *
     * @return  the port the service is listening to.
     */
    public int port();

}
