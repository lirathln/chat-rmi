package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class MessageController extends Controller {

	private static final long serialVersionUID = -5313008742517557690L;
    
	@FXML
	private GridPane gridPane;
	 
	@FXML
    private Label username, messageArea;
	
    
	public MessageController() {
		this.gridPane = new GridPane();
	}
	
	public void especifyStyle(String gridPaneStyle, String messageStyle) {
		getGridPane().setStyle(gridPaneStyle);
		getUsername().setTextFill(Color.web(messageStyle));
		getMessageArea().setTextFill(Color.web(messageStyle));
	}
	
	public GridPane getGridPane() { return gridPane; }

	public void setGridPane(GridPane gridPane) { this.gridPane = gridPane; }

	public Label getMessageArea() { return messageArea; }

	public void setMessageArea(Label messageArea) { this.messageArea = messageArea; }

	public Label getUsername() { return username; }

	public void setUsername(Label username) { this.username = username; }

}