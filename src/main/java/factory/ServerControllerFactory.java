package factory;

import java.rmi.NotBoundException;

import controller.ServerController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class ServerControllerFactory {
	
	public static ServerController getInstance() throws NotBoundException, Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(ServerControllerFactory.class.getResource("../view/server-view.fxml"));
		Parent parent = fxmlLoader.load();
		ServerController server = fxmlLoader.getController();
		server.setParent(parent);
		return server;
	}
	
}