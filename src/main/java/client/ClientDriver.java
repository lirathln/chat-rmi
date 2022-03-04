package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientDriver extends Application {
	
	public void start(Stage stage) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/client-view.fxml"));
		Scene scene = new Scene(fxmlLoader.load(), 600 , 400);
		stage.setTitle(getParameters().getNamed().get("username"));
		stage.setScene(scene);
		
		stage.show();
	}

	public static void main(String[] args) {
		try {
			launch(ClientDriver.class, "--username=" + args[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}