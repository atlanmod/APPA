/*
 * Created on 26 juil. 07
 *
 */
package fr.inria.atlanmod.appa.base;

import fr.inria.atlanmod.appa.datatypes.ConnectionDescription;
import fr.inria.atlanmod.appa.messaging.ResponseHandler;

public interface MessagingService extends Service {
    public ResponseHandler send(Message msg, ConnectionDescription cd);
}
