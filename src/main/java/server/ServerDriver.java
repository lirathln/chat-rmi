package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerDriver {
	
	public static void main(String[] args) {
		try {
			Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("chat-rmi", new ServerRMI());
			System.out.println("Server online!");
		} catch (RemoteException re) {
			re.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}