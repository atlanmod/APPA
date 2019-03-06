package org.atlanmod.appa.activemq;

import org.atlanmod.appa.core.Service;
import org.atlanmod.appa.datatypes.ConnectionDescription;
import org.atlanmod.appa.datatypes.ServiceDescription;
import org.atlanmod.appa.datatypes.StringId;
import org.atlanmod.appa.pubsub.PublishSubscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;

/**
 * Created on 14/03/2017.
 *
 * @author AtlanMod team.
 */
public class ArtemisBroker implements Service {
    private final static Logger logger = LoggerFactory.getLogger(ArtemisBroker.class);
    //private EmbeddedActiveMQ server;
    private final ServiceDescription description;

    public ArtemisBroker(ServiceDescription description) {
        this.description = description;
    }

    public static void main(String[] args) {
        ConnectionDescription cd = new ConnectionDescription(PublishSubscribe.PORT);
        ServiceDescription sd = new ServiceDescription(cd, new StringId("ActiveMQBroker"));
        sd.protocol("tcp");
        ArtemisBroker server = new ArtemisBroker(sd);
        server.start();
    }

    @Nonnull
    @Override
    public ServiceDescription description() {
        return this.description;
    }

    public void start() {
        /*
        Map<String, Object> transportParams = new HashMap<String, Object>();
        transportParams.put(TransportConstants.HOST_PROP_NAME, description.connection().socket().getHostName());
        transportParams.put(TransportConstants.PORT_PROP_NAME, description.connection().port());

        // Step 1. Create ActiveMQ Artemis core configuration, and set the properties accordingly
        Configuration configuration = new ConfigurationImpl()
                .setPersistenceEnabled(false)
                .setJournalDirectory("target/data/journal")
                .setSecurityEnabled(false)
                .addAcceptorConfiguration(new TransportConfiguration(NettyAcceptorFactory.class.getName(),transportParams))
                .addConnectorConfiguration("connector", new TransportConfiguration(NettyConnectorFactory.class.getName(), transportParams));

        try {
            server = new EmbeddedActiveMQ()
                    .setConfiguration(configuration)
                    .start();
        } catch (Exception e) {
            logger.error("Error when creating Embedded Active MQ", e);
            e.printStackTrace();
        }

        logger.info("Started Embedded ActiveMQ Server");
        */
    }

    @Override
    public void stop() {
        /*
        try {
            server.stop();
        } catch (Exception e) {
            logger.error("Error when stopping Embedded Active MQ", e);
        }
        */
    }

}
