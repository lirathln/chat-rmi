package model;

import java.rmi.Remote;
import java.rmi.RemoteException;

//import javafx.scene.paint.Color;

public interface ClientIF extends Remote {
	
	void retrieveMessage(Message message) throws RemoteException;
	void notifyClients(Notification notification) throws RemoteException;
	String getUsername() throws Exception;
	SerializableColor getColor() throws Exception;
	
}