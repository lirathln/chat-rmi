package application;

import java.io.IOException;
import java.rmi.NotBoundException;

import controller.LoginController;
import factory.ClientControllerFactory;
import factory.LoginControllerFactory;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientDriver extends Application {
	
	public void start(Stage stage) {
		try {
			LoginController login;
			login = LoginControllerFactory.getInstance();
			login.createScreen();
		
			if (login.isAccessPermission())
				ClientControllerFactory.getInstance().createScreen(login.getUsername().getText(), login.getColor().getValue());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
			launch();
	}
	
}