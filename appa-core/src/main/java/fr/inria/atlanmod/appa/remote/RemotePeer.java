package fr.inria.atlanmod.appa.remote;

import java.net.InetAddress;

import fr.inria.atlanmod.appa.datatypes.RamdomId;
import fr.inria.atlanmod.appa.base.Peer;

public class RemotePeer implements Peer {

	private RamdomId id;
	private InetAddress addr;
	private long port;
	

	public RemotePeer(InetAddress addr, long port) {

		this.addr=addr;
		this.port=port;



	}

	public RemotePeer(RamdomId id) {

		this.id=id;

	}

	public long getBasePort() {
		// TODO Auto-generated method stub
		return port;
	}

	public InetAddress getIP() {
		// TODO Auto-generated method stub
		return addr;
	}

	public RamdomId getId() {
		// TODO Auto-generated method stub
		return id;
	}

}
