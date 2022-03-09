package model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServerRMI extends UnicastRemoteObject implements ServerIF {

	private static final long serialVersionUID = 2231544417008493003L;
	private static List<ClientIF> clients;

	
	public ServerRMI() throws RemoteException { }
	
	public ServerRMI(List<ClientIF> clients) throws RemoteException {
		setClients(clients);
	}

	@Override
	public synchronized void registerClient(ClientIF client) throws RemoteException {
		getClients().add(client);
	}

	@Override
	public synchronized void broadcastMessage(Message message) throws RemoteException, Exception {
		for (ClientIF clientIF : getClients()) {
			if (!clientIF.getUsername().equals(message.getUsername()))
				clientIF.retrieveMessage(message);
		}
	}
	
	@Override
	public boolean checkUsernames(String name) throws Exception {
		for (ClientIF clientIF : getClients()) {
			if (clientIF.getUsername().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	public static List<ClientIF> getClients() {
		if (clients == null)
			clients = new ArrayList<ClientIF>();
		return clients;
	}

	public void setClients(List<ClientIF> clients) { ServerRMI.clients = clients; }

}