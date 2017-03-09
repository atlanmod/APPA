package fr.inria.atlanmod.appa.service.dht.mock;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Future;


import fr.inria.atlanmod.appa.base.Peer;

import fr.inria.atlanmod.appa.datatypes.ConnectionDescription;
import fr.inria.atlanmod.appa.datatypes.RamdomId;

import fr.inria.atlanmod.appa.exception.ObjectNotFoundException;
import fr.inria.atlanmod.appa.exception.OperationFailedException;
import fr.inria.atlanmod.appa.service.DHTService;

public class MockDHTAdapter implements DHTService {
	private MockDHT dht;
	private Registry registry;
	public MockDHTAdapter(String registryAdress){
		try {
			registry=LocateRegistry.getRegistry(registryAdress);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	// cette méthode permet la liaison au serveur RMI
	public void start(){
		try {
			dht=(MockDHT)registry.lookup("DHTService");
		} catch (Exception e) {

			System.out.println("Liaison échouée");
		}
	}
	// méthode permettant de suspendre la liaison avec le serveur RMI
	public void stop(){
		try {
			registry.unbind("DHTService");
		} catch (Exception e) {

			System.out.println("Suppression de la liaison échouée");
		}
	}

	@Override
	public int port() {
		return 0;
	}

	// implémentations des méthodes de l'interface DHTService
	 public void put(String key, Serializable value) {

	    	try {
				dht.put(key,value);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }

	    public Set<Serializable> get(String key)  {

	    	Serializable s = null;
	    	Set<Serializable> value=null;
			try {
				s = dht.get(key);
				value=new HashSet<Serializable>();
				value.add(s);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	return value;
	    }

		public Peer lookup(String key) throws ObjectNotFoundException {
			// TODO Auto-generated method stub
			return null;
		}
		public void create(Peer localPeer) throws OperationFailedException {
			// TODO Auto-generated method stub
			
		}
		public Peer direcSuccessor() throws OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}
		public boolean isInterval(String key) throws OperationFailedException {
			// TODO Auto-generated method stub
			return false;
		}
		public void join(Peer localPeer, Peer bootstrapPeer) throws OperationFailedException {
			// TODO Auto-generated method stub
			
		}
		public void leave() throws OperationFailedException {
			// TODO Auto-generated method stub
			
		}
		public Peer predecessor() throws OperationFailedException {
			// TODO Auto-generated method stub
			return null;
		}
        public Peer lookup(Serializable key) throws OperationFailedException,
                ObjectNotFoundException {
            // TODO Auto-generated method stub
            return null;
        }

        public RamdomId id() {
            // TODO Auto-generated method stub
            return null;
        }
        public ConnectionDescription getConnectionDescription() {
            // TODO Auto-generated method stub
            return null;
        }
		public String printEntries() {
			// TODO Auto-generated method stub
			return null;
		}


	@Override
	public void put(Object key, Serializable value) throws OperationFailedException {

	}

	@Override
	public Future get(Object key) throws OperationFailedException, ObjectNotFoundException {
		return null;
	}

	@Override
	public void run() {

	}
}
