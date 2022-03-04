package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import client.ClientIF;

public class ServerRMI extends UnicastRemoteObject implements ServerIF {

	private static final long serialVersionUID = 2231544417008493003L;
	private List<ClientIF> clients;

	
	public ServerRMI() throws RemoteException { }
	
	protected ServerRMI(List<ClientIF> clients) throws RemoteException {
		setClients(clients);
	}

	@Override
	public synchronized void registerClient(ClientIF client) throws RemoteException {
		getClients().add(client);
	}

	@Override
	public synchronized void broadcastMessage(Message message) throws RemoteException, Exception {
		for (ClientIF clientIF : getClients()) {
			if (!clientIF.getName().equals(message.getUser()))
				clientIF.retrieveMessage(message);
		}
	}
	
	public List<ClientIF> getClients() {
		if (clients == null)
			clients = new ArrayList<ClientIF>();
		return clients;
	}

	public void setClients(List<ClientIF> clients) { this.clients = clients; }

}