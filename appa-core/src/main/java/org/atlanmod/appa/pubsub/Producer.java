package org.atlanmod.appa.pubsub;

import java.io.Serializable;

/**
 * Created on 15/03/2017.
 *
 * @author AtlanMod team.
 */
public interface Producer {

    void send(Serializable serializable);
}
