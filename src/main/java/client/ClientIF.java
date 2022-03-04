package client;

import java.rmi.Remote;
import java.rmi.RemoteException;

import server.Message;

public interface ClientIF extends Remote {

	String username = "";
	String color = "";
	
	void retrieveMessage(Message message) throws RemoteException;
	public String getName() throws Exception;
	public void setName(String name) throws Exception;
	
}