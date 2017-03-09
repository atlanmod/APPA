/*
 * Created on 2 ao�t 07
 *
 */
package fr.inria.atlanmod.appa.messaging;

import java.net.InetSocketAddress;

public interface Server extends Runnable {

    public ResponseHandler send(byte[] data , InetSocketAddress addr);

}