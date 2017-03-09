package fr.inria.atlanmod.appa.rmi;

import fr.inria.atlanmod.appa.Node;
import fr.inria.atlanmod.appa.exception.OperationFailedException;
import fr.inria.atlanmod.appa.service.DHTService;
import fr.inria.atlanmod.appa.service.NamingService;
import fr.inria.atlanmod.appa.service.registry.JmdnsJavaDiscoveryService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class RMIBootstrapper extends Node {

    private RMIRegistryService rmiService;
    private JmdnsJavaDiscoveryService discovery;

    public RMIBootstrapper() {

    }

    @Override
    protected void startDiscovery() {
        discovery = new JmdnsJavaDiscoveryService();
        this.execute(discovery);
    }

    @Override
    protected void startBroker() {
        rmiService = new RMIRegistryService();
        this.execute(rmiService);
        try {
            discovery.publish(rmiService);
        } catch (OperationFailedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void startDHT() {
        try {
            RemoteMap map = (RemoteMap) UnicastRemoteObject.exportObject(new RemoteMapServer<String,String>(), 0);
            rmiService.rebind(DHTService.NAME, map);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void startNaming() {
        try {
            RemoteNaming naming = (RemoteNaming) UnicastRemoteObject.exportObject(new RemoteNamingServer(),0);
            rmiService.rebind(NamingService.NAME, naming);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        RMIBootstrapper instance = new RMIBootstrapper();
        instance.start();
    }

}
