package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServerRMI extends UnicastRemoteObject implements ServerIF {

	private static final long serialVersionUID = 2231544417008493003L;
	private PropertyChangeSupport pcs;
	private static List<ClientIF> clients;

	
	public ServerRMI() throws RemoteException {
		ServerRMI.clients = new ArrayList<ClientIF>();
		this.pcs = new PropertyChangeSupport(this); 
	}
	
	public ServerRMI(PropertyChangeListener observer) throws RemoteException {
		ServerRMI.clients = new ArrayList<ClientIF>();
		this.pcs = new PropertyChangeSupport(this);
		getPCS().addPropertyChangeListener(observer);
	}
	
	@Override
	public synchronized void registerClient(ClientIF client) throws RemoteException {
		getClients().add(client);
		getPCS().firePropertyChange("registerClient", null, client);
//		try {
//			getPCS().firePropertyChange("notification", null, client.getUsername() + " entrou.");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
	@Override
	public synchronized void removeClient(int id) throws RemoteException {
		// TODO mandar notificações para clientes
		getPCS().firePropertyChange("removeClient", id, null);
//		try {
//			getPCS().firePropertyChange("notification", null, getClients().get(id).getUsername() + " saiu.");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		getClients().remove(getClients().get(id));
	}
	
	@Override
	public synchronized void broadcastMessage(Message message) throws RemoteException, Exception {
		for (ClientIF clientIF : getClients()) {
			if (!clientIF.getUsername().equals(((Message) message).getUsername()))
					clientIF.retrieveMessage((Message) message);
		}
	}
	
	@Override
	public synchronized void serverBroadcast(Notification notification) throws RemoteException, Exception {
		for (ClientIF clientIF : getClients()) {
			clientIF.retrieveMessage(notification);
		}
	}
	
	@Override
	public boolean checkUsernames(String name) throws Exception {
		for (ClientIF clientIF : getClients()) {
			if (clientIF.getUsername().equals(name))
				return true;
		}
		return false;
	}
	
	public PropertyChangeSupport getPCS() { return pcs;	}

	public static List<ClientIF> getClients() { return ServerRMI.clients;	}

}