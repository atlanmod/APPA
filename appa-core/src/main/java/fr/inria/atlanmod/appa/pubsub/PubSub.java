package fr.inria.atlanmod.appa.pubsub;


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
