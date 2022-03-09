package factory;

import java.io.IOException;

import controller.LoginController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class LoginControllerFactory {

	public static LoginController getInstance() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(LoginControllerFactory.class.getResource("../view/login.fxml"));
		Parent parent = fxmlLoader.load();
		LoginController login = fxmlLoader.getController();
		login.setParent(parent);
		return login;
	}
	
}