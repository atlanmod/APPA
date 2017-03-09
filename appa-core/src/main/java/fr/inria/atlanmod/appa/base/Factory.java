/*
 * Created on 26 juil. 07
 *
 */
package fr.inria.atlanmod.appa.base;

public interface Factory {

    public Registry createRegistry();

    public MessagingService createMessaging();
}
