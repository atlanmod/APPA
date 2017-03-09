package fr.inria.mock;

import fr.inria.atlanmod.appa.rmi.RemoteMap;

import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
	public static void main(String args[]){
		try{
			Registry registry=LocateRegistry.getRegistry(InetAddress.getLocalHost().getHostAddress());
			RemoteMap mock=(RemoteMap) registry.lookup("DHTService") ;
			mock.put("a", "toto");
			mock.put("b", "halo");
			mock.put("a", "tota");
			System.out.println("-----> " + mock.get("a"));
		}
		catch (Exception e){
			System.out.println(e);
			
		}
	}

}