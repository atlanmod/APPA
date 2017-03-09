/*
 * Created on 4 juil. 07
 *
 */
package fr.inria.atlanmod.appa.service.dht.mock;

import fr.inria.atlanmod.appa.base.DHT;
import fr.inria.atlanmod.appa.exception.ObjectNotFoundException;
import fr.inria.atlanmod.appa.exception.OperationFailedException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

public class LocalDHT implements DHT<String,Serializable> {

    private static Logger logger = Logger.getLogger("appa.dht.mock");

    private long delay;
    private Map<String, Serializable> map = new HashMap<String, Serializable>();

    public LocalDHT(long l) {
        delay = l;
    }

    public Future<Serializable> get(String key) throws OperationFailedException,
            ObjectNotFoundException {
        delay();

        if (map.containsKey(key)) {
            return new ReturnValue<Serializable>(map.get(key)) ;
        } else {
            throw new ObjectNotFoundException();
        }
    }

    public void put(String key, Serializable value) throws OperationFailedException {
        logger.info(String.format("Putting [%s] = %s", key, value));

        delay();
        map.put(key, value);
   }

    private synchronized void delay() throws OperationFailedException {
        try {
            this.wait(delay);
        } catch (InterruptedException e) {
            throw new OperationFailedException();
        }
    }

    class ReturnValue<V> implements Future {
        private V value;

        public ReturnValue(V v) {
            value =v;
        }

        @Override
        public boolean isDone() {
            return true;
        }

        @Override
        public Object get() throws InterruptedException, ExecutionException {
            return value;
        }

        @Override
        public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return value;
        }

        @Override
        public boolean isCancelled() {
            return false;
        }

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            return false;
        }


    }


}
