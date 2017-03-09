package fr.inria.atlanmod.appa.rmi;

import fr.inria.atlanmod.appa.datatypes.ConnectionDescription;
import fr.inria.atlanmod.appa.datatypes.Id;
import fr.inria.atlanmod.appa.exception.OperationFailedException;
import fr.inria.atlanmod.appa.service.NamingService;

import java.rmi.RemoteException;

/**
 * A Naming Service that uses RMI.
 */
public class NamingServiceFake implements NamingService {
    private RemoteNaming naming;

    public NamingServiceFake(RMIRegistry registry) {
        naming = (RemoteNaming) registry.lookup(NamingService.NAME);
    }

    @Override
    public Id register(ConnectionDescription description) throws OperationFailedException {
        try {
            return naming.register(description);
        } catch (RemoteException e) {
           throw new OperationFailedException(e);
        }
    }

    @Override
    public ConnectionDescription lookup(Id id) throws OperationFailedException {
        try {
            return naming.lookup(id);
        } catch (RemoteException e) {
            throw new OperationFailedException(e);
        }
    }
}
