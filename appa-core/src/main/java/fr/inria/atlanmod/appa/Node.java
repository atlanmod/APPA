package fr.inria.atlanmod.appa;


import fr.inria.atlanmod.appa.base.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Node {
    private List<Service> services = new ArrayList<Service>();
    private ExecutorService executor = Executors.newFixedThreadPool(10);


    public final void start() {
        this.beforeStarting();
        this.startDiscovery();
        this.startBroker();
        this.startDHT();
        this.startNaming();
        this.afterStarting();
    }

    protected void execute(Service s) {
        s.start();
        services.add(s);
        executor.execute(s);
    }

    protected void beforeStarting(){}
    protected void startDiscovery(){}
    protected void startBroker(){}
    protected void startDHT(){}
    protected void startNaming(){}
    protected void afterStarting(){}

    public void shutdown() {
        executor.shutdown();
    }

}
