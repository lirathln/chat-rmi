package controller;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.ServerIF;

public class LoginController extends Controller {

	private static final long serialVersionUID = -5197400721736490832L;
	private Stage stage;
	private Parent parent;
	private boolean accessPermission;

    @FXML
    private ColorPicker color;

    @FXML
    private TextField username;
    

    public LoginController() {
    	this.stage = null;
    	this.parent = null;
    	this.accessPermission = false;
    }
    
    public LoginController(Stage stage, Parent parent) {
    	this.stage = stage;
    	this.parent = parent;
    	this.accessPermission = false;
    }
    
    public void createScreen() {
    	this.setStage(new Stage(StageStyle.TRANSPARENT));
    	getStage().setTitle("Login");
    	getStage().setScene(new Scene(parent, 400, 400));
    	getStage().showAndWait();
    }
    
	@FXML
    void accessChat(ActionEvent event) throws AccessException, RemoteException, NotBoundException, Exception {
		try {
			ServerIF server = (ServerIF) LocateRegistry.getRegistry(1099).lookup("chat-rmi");
			if (!server.checkUsernames(username.getText())) {
				setAccessPermission(true);
				getStage().close();
			}
			else
				this.errorAlert("Parece que esse nome já está em uso, escolha outro nome para continuarmos!");
		} catch (Exception e) {
			this.errorAlert("Procure o administrador para resolução o problema: " + e.getMessage());
		}
    }

	public Stage getStage() { return stage; }

	public void setStage(Stage stage) { this.stage = stage; }

	public Parent getParent() { return parent; }

	public void setParent(Parent parent) { this.parent = parent; }

	public boolean isAccessPermission() { return accessPermission; }

	public void setAccessPermission(boolean accessPermission) { this.accessPermission = accessPermission; }

	public ColorPicker getColor() { return color; }

	public void setColor(ColorPicker color) { this.color = color; }

	public TextField getUsername() { return username; }

	public void setUsername(TextField username) { this.username = username; }

}