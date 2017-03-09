/*
 * Created on 1 juil. 07
 *
 */
package fr.inria.atlanmod.appa.base;

import java.net.InetAddress;

import fr.inria.atlanmod.appa.datatypes.RamdomId;

public interface Peer {

    public RamdomId getId();

    public long getBasePort();

    public InetAddress getIP();

}
