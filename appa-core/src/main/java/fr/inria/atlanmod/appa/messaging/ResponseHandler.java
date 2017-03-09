package fr.inria.atlanmod.appa.messaging;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import fr.inria.atlanmod.appa.base.Message;

public class ResponseHandler {
	private byte[] rsp = null;

	public synchronized boolean handleResponse(byte[] rsp) {
		this.rsp = rsp;
		this.notify();
		return true;
	}

	public synchronized Message waitForResponse() {
	    Message m = null;
		while(this.rsp == null) {
			try {
				this.wait();
			} catch (InterruptedException e) {
			}
		}

		ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(new ByteArrayInputStream(rsp));
            m = (Message) ois.readObject();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

		return m;

	}
}
