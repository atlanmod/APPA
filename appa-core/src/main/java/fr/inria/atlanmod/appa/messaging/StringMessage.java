/*
 * Created on 31 juil. 07
 *
 */
package fr.inria.atlanmod.appa.messaging;

import fr.inria.atlanmod.appa.datatypes.RamdomId;

public class StringMessage extends AbstractMessage {

    /**
     *
     */
    private static final long serialVersionUID = -5411841115008367992L;
    private String msg;

    public StringMessage(String str) {
        super(new RamdomId());
        msg = str;
    }

    @Override
    public String toString() {
        return "Message: " + msg;
    }

}
