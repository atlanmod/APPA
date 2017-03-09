package fr.inria.mock;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TimestampServer extends Remote {
    long generateTimestamp() throws RemoteException;

    long lastTimestamp() throws RemoteException;
}
