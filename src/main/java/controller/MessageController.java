package controller;

import java.io.Serializable;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MessageController implements Serializable {

	private static final long serialVersionUID = -5313008742517557690L;
    
	@FXML
    private Label username, messageArea;
	
    
	public MessageController() {}

	public Label getMessageArea() { return messageArea; }

	public void setMessageArea(Label messageArea) { this.messageArea = messageArea; }

	public Label getUsername() { return username; }

	public void setUsername(Label username) { this.username = username; }

}