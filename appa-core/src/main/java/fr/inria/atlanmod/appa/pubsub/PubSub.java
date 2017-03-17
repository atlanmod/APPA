package fr.inria.atlanmod.appa.pubsub;

import fr.inria.atlanmod.appa.core.Service;

/**
 * Created on 15/03/2017.
 *
 * @author AtlanMod team.
 */
public interface PubSub  {
    int PORT = 6446;

    Producer createTopic(String string);
    Consumer consumeTopic(String string);
}
