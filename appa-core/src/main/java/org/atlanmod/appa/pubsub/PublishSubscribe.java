package org.atlanmod.appa.pubsub;


/**
 * Created on 15/03/2017.
 *
 * @author AtlanMod team.
 */
public interface PublishSubscribe {
    int PORT = 6446;
    String NAME = "PublishSubscribe";

    Producer createTopic(String string);

    Consumer consumeTopic(String string);

}
