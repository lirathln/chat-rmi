package factory;

import java.rmi.NotBoundException;

import controller.MessagePaneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class MessagePaneControllerFactory {
	
	public static MessagePaneController getInstance() throws NotBoundException, Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(MessagePaneControllerFactory.class.getResource("../view/pane-message.fxml"));
		Parent parent = fxmlLoader.load();
		MessagePaneController pane = fxmlLoader.getController();
		pane.setParent(parent);
		return pane;
	}
	
}