package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Message;
import model.Notification;

public class MessagePaneController extends Controller {

	private static final long serialVersionUID = -565783155972532418L;
	private Stage stage;
	private Parent parent;
	
	@FXML
	private VBox messagesVBox;
	
	
    public void createScreen() {
    	this.setStage(new Stage());
    	getStage().setTitle("Login");
    	getStage().setScene(new Scene(parent, 600, 400));
    	getStage().showAndWait();
    }
	
	public MessagePaneController() {
		System.out.println("MessagePaneController");
	}
	
	public void addNotification(Notification notification, String file) {
		getMessagesVBox().getChildren().add(loadFXML(notification, file));
	}
	
	public void addMessage(Message message, String file) {
		getMessagesVBox().getChildren().add(loadFXML(message, file));
	}
	
    private Parent loadFXML(Notification message, String file) {
    	try {
    		FXMLLoader fxml = new FXMLLoader(getClass().getResource(file));
    		Parent parent = fxml.load();
    		
    		Controller controller = fxml.getController();
    		if (message.getClass() == Message.class) {
	    		((MessageController)controller).getUsername().setText(((Message) message).getUsername());
	    		((MessageController) controller).getMessageArea().setText(((Message) message).getContent());
	    		((MessageController) controller).especifyStyle("-fx-background-radius: 5; -fx-background-color: " 
	    		+ message.getColor().getHexStringColor() + ";", (message.getColor().isBackgroundDark() ? "WHITE" : "BLACK"));
    		} else {
    			controller = fxml.getController();
    			((NotificationController) controller).getNotification().setText(message.getDate() + " : " + message.getNotification());
    			((NotificationController) controller).especifyStyle("-fx-background-radius: 30; -fx-background-color: " 
    		    		+ message.getColor().getHexStringColor() + ";", (message.getColor().isBackgroundDark() ? "WHITE" : "BLACK"));
    		}
			return parent;
		} catch (IOException e) {
			this.errorAlert("Procure o administrador para resolução o problema: \n\t" + e.fillInStackTrace());
		}
    	System.out.println("\n\tNULL");
		return null;
    }

	public VBox getMessagesVBox() { 
		if (messagesVBox == null)
			messagesVBox = new VBox();
		return messagesVBox; 
	}

	public Parent getParent() throws IOException {	
		if (parent == null)
			parent = new FXMLLoader(getClass().getResource("../view/pane-message.fxml")).load();
		return parent;	
	}

	public void setParent(Parent parent) { this.parent = parent; }

	public Stage getStage() { return stage; }

	public void setStage(Stage stage) { this.stage = stage; }
}