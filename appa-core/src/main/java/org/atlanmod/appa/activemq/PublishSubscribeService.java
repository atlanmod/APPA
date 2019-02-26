package fr.inria.atlanmod.appa.activemq;


import fr.inria.atlanmod.appa.core.Service;
import fr.inria.atlanmod.appa.datatypes.ConnectionDescription;
import fr.inria.atlanmod.appa.datatypes.ServiceDescription;
import fr.inria.atlanmod.appa.pubsub.Consumer;
import fr.inria.atlanmod.appa.pubsub.Producer;
import fr.inria.atlanmod.appa.pubsub.PublishSubscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;


/**
 * Created on 10/03/2017.
 *
 * @author AtlanMod team.
 */
public class PublishSubscribeService implements PublishSubscribe, Service {
    private static Logger logger = LoggerFactory.getLogger(PublishSubscribeService.class);
//    private Topic topic;
//    private Connection connection;
//    private Session session;
    private final ConnectionDescription description;


    public PublishSubscribeService(ConnectionDescription description) {
        this.description = description;
    }


    public Producer createTopic(String string) {
        return null;
/*        MessageProducer producer = null;
        try {
            topic = session.createTopic(string);
             producer = session.createProducer(topic);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            producer.setTimeToLive(10000);
        } catch (JMSException e) {
            logger.error("JMS error when creating topic producer", e);
        }

        return nonNull(producer) ? new ActiveMQProducer(producer) : null;*/
    }

    public Consumer consumeTopic(String string) {
        /*
        MessageConsumer consumer = null;
        try {
            topic = session.createTopic(string);
            consumer = session.createDurableConsumer(topic, "str");
        } catch (JMSException e) {
            logger.error("JMS error when creating topic consumer", e);
        }

        return nonNull(consumer) ? new ActiveMQConsumer(consumer) : null ;
        */
        return null;
    }

    @Nonnull
    @Override
    public ServiceDescription description() {
        return null;
    }

    @Override
    public void start() {
        // Create a ConnectionFactory
        String url = "tcp://" + description.toString();
        //String url = "tcp://127.0.0.1:6446";

        System.out.println("url: " + url);

        /*
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);

        try {
            connection = connectionFactory.createConnection();
            connection.setClientID("AAA");
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        */

    }

    @Override
    public void stop() {
        /*
        try {
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        */
    }


/*
    class ActiveMQProducer implements Producer {
        private final MessageProducer producer;

        public ActiveMQProducer(MessageProducer producer) {
            this.producer = producer;

        }

        @Override
        public void send(Serializable serializable) {
            try {
                ObjectMessage message = session.createObjectMessage(serializable);
                producer.send(message);
            } catch (JMSException e) {
                e.printStackTrace();
            }

        }
    }

    class ActiveMQConsumer implements Consumer {
        private final MessageConsumer consumer;

        public ActiveMQConsumer(MessageConsumer consumer) {
            this.consumer = consumer;
        }

        @Override
        public Serializable receive(int timeout) {
            try {
                ObjectMessage message = (ObjectMessage) consumer.receive(timeout);
                return message.getObject();
            } catch (JMSException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static void main(String[] args) throws JMSException, UnknownHostException {


        ConnectionDescription description = new ConnectionDescription(InetAddress.getByName("127.0.0.1"),PublishSubscribe.PORT);
        PublishSubscribeService service = new PublishSubscribeService(description);
        service.start();


        Producer producer = service.createTopic("neoemf");
        Consumer consumer = service.consumeTopic("neoemf");

        producer.send(description);

        ConnectionDescription retour = (ConnectionDescription) consumer.receive(1000);
        System.out.println("Received: "+retour);
        service.stop();
    }
    */
}
