/*
 * Created on 31 juil. 07
 *
 */
package fr.inria.atlanmod.appa.base;

import java.io.Serializable;

import fr.inria.atlanmod.appa.datatypes.RamdomId;

public interface Message extends Serializable {


    public RamdomId getServiceId();

    public byte[] toByteArray();

}
