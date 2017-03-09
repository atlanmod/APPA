package fr.inria.atlanmod.appa.rmi;

import fr.inria.atlanmod.appa.datatypes.RamdomId;
import fr.inria.atlanmod.appa.exception.ObjectNotFoundException;
import fr.inria.atlanmod.appa.exception.OperationFailedException;
import fr.inria.atlanmod.appa.rmi.RMIRegistry;
import fr.inria.atlanmod.appa.rmi.RemoteMap;
import fr.inria.atlanmod.appa.service.DHTService;

import java.rmi.RemoteException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class DHTServiceFake implements DHTService<String,String> {
    private RemoteMap<String,String> map;

    public DHTServiceFake(RMIRegistry registry) {
        map = (RemoteMap<String,String>) registry.lookup(DHTService.NAME);
    }

    @Override
    public void put(String key, String value) throws OperationFailedException {
        try {
            map.put(key,value);
        } catch (RemoteException e) {
            throw new OperationFailedException(e);
        }

    }

    @Override
    public Future<String> get(String key) throws OperationFailedException, ObjectNotFoundException {
        try {
            return new GetValue(map.get(key));
        } catch (RemoteException e) {
            throw new OperationFailedException(e);
        }
    }

    @Override
    public RamdomId id() {
        return null;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public int port() {
        return 0;
    }

    @Override
    public void run() {

    }

    private static class GetValue implements Future<String>{
        private String value;

        public GetValue(String value) {
            this.value = value;
        }

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            return false;
        }

        @Override
        public boolean isCancelled() {
            return false;
        }

        @Override
        public boolean isDone() {
            return false;
        }

        @Override
        public String get() throws InterruptedException, ExecutionException {
            return value;
        }

        @Override
        public String get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return value;
        }
    }
}
