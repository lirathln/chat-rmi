package model;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientIF extends Remote {
	
	void retrieveMessage(Notification message) throws RemoteException;
	void notifyClients(Notification notification) throws RemoteException;
	String getUsername() throws Exception;
	SerializableColor getColor() throws Exception;
	
}