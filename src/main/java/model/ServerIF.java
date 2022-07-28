package model;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerIF extends Remote {
	
	void registerClient(ClientIF client) throws RemoteException;
	void removeClient(int id) throws RemoteException;
	void broadcastMessage(Message message) throws RemoteException, Exception;
	void serverBroadcast(Notification notification) throws RemoteException, Exception;;
	boolean checkUsernames(String name) throws Exception;
	
}