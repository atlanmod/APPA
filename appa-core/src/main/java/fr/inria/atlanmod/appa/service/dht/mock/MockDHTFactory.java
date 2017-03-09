package fr.inria.atlanmod.appa.service.dht.mock;

import fr.inria.atlanmod.appa.service.DHTFactory;
import fr.inria.atlanmod.appa.service.DHTService;


// Implémentation de l'interface qui sert d'AbstractFactory

public class MockDHTFactory implements DHTFactory {
	
	private String registryAdress;
	
	public MockDHTFactory(String str){
		registryAdress = str;
	}

	public DHTService createDHTService() {
		return new MockDHTAdapter(registryAdress);
	}

}
