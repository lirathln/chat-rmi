package factory;

import java.rmi.NotBoundException;

import controller.ClientController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class ClientControllerFactory {

	public static ClientController getInstance() throws NotBoundException, Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(ClientControllerFactory.class.getResource("../view/client-view.fxml"));
		Parent parent = fxmlLoader.load();
		ClientController client = fxmlLoader.getController();
		client.setParent(parent);
		return client;
	}
	
}