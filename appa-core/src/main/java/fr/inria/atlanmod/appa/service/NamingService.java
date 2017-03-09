package fr.inria.atlanmod.appa.service;

import fr.inria.atlanmod.appa.datatypes.ConnectionDescription;
import fr.inria.atlanmod.appa.datatypes.Id;
import fr.inria.atlanmod.appa.exception.OperationFailedException;

public interface NamingService {

    String NAME = "NamingService";

    Id register(ConnectionDescription description) throws OperationFailedException;

    ConnectionDescription lookup(Id id) throws OperationFailedException;
}
