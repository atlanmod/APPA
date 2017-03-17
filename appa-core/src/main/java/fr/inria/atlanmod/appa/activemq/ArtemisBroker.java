package fr.inria.atlanmod.appa.activemq;

import fr.inria.atlanmod.appa.core.Service;
import fr.inria.atlanmod.appa.datatypes.ConnectionDescription;
import fr.inria.atlanmod.appa.datatypes.Id;
import org.apache.activemq.artemis.api.core.TransportConfiguration;
import org.apache.activemq.artemis.core.config.Configuration;
import org.apache.activemq.artemis.core.config.impl.ConfigurationImpl;
import org.apache.activemq.artemis.core.remoting.impl.netty.NettyAcceptorFactory;
import org.apache.activemq.artemis.core.remoting.impl.netty.NettyConnectorFactory;
import org.apache.activemq.artemis.core.remoting.impl.netty.TransportConstants;
import org.apache.activemq.artemis.core.server.embedded.EmbeddedActiveMQ;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 14/03/2017.
 *
 * @author AtlanMod team.
 */
public class ArtemisBroker implements Service  {
    private final ConnectionDescription description;
    private EmbeddedActiveMQ server;

    public ArtemisBroker(ConnectionDescription description) {
        this.description = description;
    }

    @Nonnull
    @Override
    public Id id() {
        return null;
    }

    public void start() {

    }

    @Override
    public void stop() {
        try {
            server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int port() {
        return description.port();
    }

    public void run() {


        Map<String, Object> transportParams = new HashMap<String, Object>();
        transportParams.put(TransportConstants.HOST_PROP_NAME, description.ip().getHostName());
        transportParams.put(TransportConstants.PORT_PROP_NAME, description.port());

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
            e.printStackTrace();
        }

        System.out.println("Started Embedded ActiveMQ Server");
    }

}
