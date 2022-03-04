package client;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import server.Message;
import server.ServerIF;

public class Client extends UnicastRemoteObject implements ClientIF {

	private static final long serialVersionUID = 8052292164470109415L;
	private ServerIF server;
	private String name = null;
	private PropertyChangeSupport pcs;


	public Client(String name, ServerIF server, PropertyChangeListener observer) throws RemoteException, Exception {
		setName(name);
		setServer(server);
		getServer().registerClient(this);
		getPcs().addPropertyChangeListener(observer);
	}

	public void retrieveMessage(Message message) throws RemoteException {
		System.out.println(message.getUser() + " : " + message.getContent());
		getPcs().firePropertyChange("newMessage", null, message);
	}

	public ServerIF getServer() { return server; }

	public void setServer(ServerIF server) { this.server = server; }

	@Override
	public String getName() { return name; }
	
	@Override
	public void setName(String name) { this.name = name; }

	public PropertyChangeSupport getPcs() {
		if (pcs == null)
			pcs = new  PropertyChangeSupport(this);
		return pcs;
	}

	public void setPcs(PropertyChangeSupport pcs) { this.pcs = pcs; }
	
}