package application;

import java.net.UnknownHostException;
import java.rmi.NotBoundException;

import factory.ServerControllerFactory;
import javafx.application.Application;
import javafx.stage.Stage;

public class ServerDriver extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			ServerControllerFactory.getInstance().createScreen();
		} catch (UnknownHostException e) {
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