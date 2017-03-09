/*
 * Created on 13 juin 2005
 *
 */
package fr.inria.atlanmod.appa.messaging;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import fr.inria.atlanmod.appa.base.Message;
import fr.inria.atlanmod.appa.datatypes.RamdomId;

/**
 * @author sunye
 *
 */
public abstract class AbstractMessage implements Message {

    private RamdomId serviceId;

    public AbstractMessage(RamdomId sid) {
        serviceId = sid;
    }

    public RamdomId getServiceId() {
        return serviceId;
    }

    public byte[] toByteArray() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return baos.toByteArray();
    }

}
