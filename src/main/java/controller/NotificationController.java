package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class NotificationController extends Controller {

	private static final long serialVersionUID = -2392841942396712631L;
	
	@FXML
	private Label notification;

	
	public NotificationController() {
		this.notification = new Label();
	}
	
	public void especifyStyle(String background, String notificationStyle) {
		getNotification().setStyle(background);
		getNotification().setTextFill(Color.web(notificationStyle));
	}

	public Label getNotification() { return notification; }

	public void setNotification(Label notification) { this.notification = notification;	}

}