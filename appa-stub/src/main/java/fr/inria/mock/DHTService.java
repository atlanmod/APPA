package fr.inria.mock;

import fr.inria.atlanmod.appa.base.DHT;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class DHTService<K,V extends Serializable> extends RMIService implements DHT<K,V> {
	
    private Map<K,V> data = new HashMap<K,V>();
    
    public DHTService(String brokerAdress) {
        super(brokerAdress);
/*
			try {
				// enregistre DHTService dans RMI
				RemoteMap stub = (RemoteMap) UnicastRemoteObject.exportObject(this, 0);
				//trouve le service de resolution de nom de RMI
				//registry = LocateRegistry.getRegistry(brokerAdress);
				registry = LocateRegistry.createRegistry(1099);
				//enregistre la référence dans le service de résolution
	            registry.rebind("DHTService",stub);
			} catch (RemoteException e) {
			
				System.out.println("l'objet n'a pas pu être dans le registre RMI");
			}    */
    }

    public void start() {
        registry();
    }


    public void put(K k, V v) {
    	data.put(k,v);
    }
    
    public Future<V> get(K k)  {
      
    	//Serializable s=data.get(k);
    	return null
				;
    }
   
    
    public static void main(String args[]) {
            //le registre RMI torunera dans le serveur
            @SuppressWarnings("unused")
			DHTService dht;
			try {
				dht = new DHTService(InetAddress.getLocalHost().getHostAddress());
				
			} catch (UnknownHostException e) {
				
				e.printStackTrace();
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


