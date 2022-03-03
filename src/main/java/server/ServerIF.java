package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import client.ClientIF;

public interface ServerIF extends Remote {
	
	void registerClient(ClientIF client) throws RemoteException;
	void broadcastMessage(Message message) throws RemoteException, Exception;
	
}