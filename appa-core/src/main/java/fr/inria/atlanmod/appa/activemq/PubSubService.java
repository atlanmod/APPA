package fr.inria.atlanmod.appa.activemq;


import fr.inria.atlanmod.appa.core.*;
import fr.inria.atlanmod.appa.datatypes.ConnectionDescription;
import fr.inria.atlanmod.appa.datatypes.Id;
import fr.inria.atlanmod.appa.datatypes.ServiceDescription;
import fr.inria.atlanmod.appa.pubsub.Consumer;
import fr.inria.atlanmod.appa.pubsub.Producer;
import fr.inria.atlanmod.appa.pubsub.PubSub;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.annotation.Nonnull;
import javax.jms.*;
import java.io.Serializable;

import static java.util.Objects.nonNull;


/**
 * Created on 10/03/2017.
 *
 * @author AtlanMod team.
 */
public class PubSubService implements PubSub, Service {
    private final ConnectionDescription description;
    private Topic topic;
    private Connection connection;
    private Session session;

    public PubSubService(ConnectionDescription description) {
        this.description = description;
    }



    public Producer createTopic(String string) {
        MessageProducer producer = null;
        try {
            topic = session.createTopic(string);
             producer = session.createProducer(topic);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            producer.setTimeToLive(10000);
        } catch (JMSException e) {
            e.printStackTrace();
        }

        return nonNull(producer) ? new ActiveMQProducer(producer) : null;
    }

    public Consumer consumeTopic(String string) {
        MessageConsumer consumer = null;
        try {
            topic = session.createTopic(string);
            consumer = session.createDurableConsumer(topic, "str");
        } catch (JMSException e) {
            e.printStackTrace();
        }

        return nonNull(consumer) ? new ActiveMQConsumer(consumer) : null ;
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
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);

        try {
            connection = connectionFactory.createConnection();
            connection.setClientID("AAA");
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void stop() {
        try {
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


    public int port() {
        return description.port();
    }

    @Override
    public void run() {

    }



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

    public static void main(String[] args) throws JMSException {
        ConnectionDescription description = new ConnectionDescription(PubSub.PORT);
        PubSubService service = new PubSubService(description);
        service.start();
        Producer producer = service.createTopic("neoemf");
        Consumer consumer = service.consumeTopic("neoemf");

        producer.send(description);

        ConnectionDescription retour = (ConnectionDescription) consumer.receive(1000);
        System.out.println("Received: "+retour);
        service.stop();
        service.run();
    }
}
