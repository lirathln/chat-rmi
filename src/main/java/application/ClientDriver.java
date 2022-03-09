package application;

import controller.LoginController;
import factory.ClientControllerFactory;
import factory.LoginControllerFactory;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientDriver extends Application {
	
	public void start(Stage stage) throws Exception {
		LoginController login = LoginControllerFactory.getInstance();
		login.createScreen();
		
		if (login.isAccessPermission())
			ClientControllerFactory.getInstance().createScreen(login.getUsername().getText(), login.getColor().getValue());
	}

	public static void main(String[] args) {
		try {
			launch();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}