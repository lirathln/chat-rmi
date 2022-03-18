package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javafx.scene.paint.Color;

public class Client extends UnicastRemoteObject implements ClientIF {

	private static final long serialVersionUID = 8052292164470109415L;
	private PropertyChangeSupport pcs;
	private ServerIF server;
	private String username;
	private SerializableColor color;


	public Client(String username, Color color, ServerIF server, PropertyChangeListener observer) throws RemoteException, Exception {
		setUsername(username);
		setColor(new SerializableColor(color));
		setServer(server);
		getServer().registerClient(this);
		getPCS().addPropertyChangeListener(observer);
	}
	
	public Client(ServerIF server, PropertyChangeListener observer) throws RemoteException, Exception {
		setUsername("");
		setColor(null);
		setServer(server);
		getServer().registerClient(this);
		getPCS().addPropertyChangeListener(observer);
	}
	
	@Override
	public void notifyClients(Notification notification) throws RemoteException {
		getPCS().firePropertyChange("notification", null, notification);
	}
	
	public void retrieveMessage(Message message) throws RemoteException {
		getPCS().firePropertyChange("newMessage", null, message);
	}
	
	public PropertyChangeSupport getPCS() {
		if (pcs == null)
			pcs = new PropertyChangeSupport(this);
		return pcs;
	}

	public ServerIF getServer() { return server; }

	public void setServer(ServerIF server) { this.server = server; }

	public void setPCS(PropertyChangeSupport pcs) { this.pcs = pcs; }

	public String getUsername() { return username; }
	
	public void setUsername(String username) throws Exception { this.username = username; }
	
	public SerializableColor getColor() { return color; }
	
	public void setColor(SerializableColor color) throws Exception { this.color = color; }
	
}