package controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Client;
import model.Message;
import model.SerializableColor;
import model.ServerIF;

public class ClientController implements PropertyChangeListener, Serializable {
	
	private static final long serialVersionUID = -5510985359823935020L;
	private Client client;
	private Stage stage;
	private Parent parent;

    @FXML
    private TextArea currentMessage;
    
    @FXML
    private VBox messagesVBox;
    
    @FXML
    private ScrollPane scrollPane;
    
	
    public ClientController() throws AccessException, RemoteException, NotBoundException, Exception {
    	this.messagesVBox = new VBox();
    	this.scrollPane = new ScrollPane();
    }
    
	public void createScreen(String username, Color color) throws AccessException, RemoteException, NotBoundException, Exception {
		getClient().setUsername(username);
		getClient().setColor(new SerializableColor(color));
		
		setStage(new Stage());
		getStage().setTitle(getClient().getUsername());
		getStage().setScene(new Scene(getParent(), 600, 400));
		getStage().show();
	}
    
	@FXML
    void sendMessage(MouseEvent event) throws Exception {
    	if (!getCurrentMessage().getText().isEmpty()) {
    		try {
    			Message message = new Message(getClient().getUsername(), getCurrentMessage().getText(), getClient().getColor());
				getClient().getServer().broadcastMessage(message);
				refreshPane(message, "../view/primary-message.fxml");
				getCurrentMessage().clear();
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    }
    
    private void refreshPane(Message message, String fxml) throws Exception {
    	Platform.runLater(() -> {
    		getMessagesVBox().getChildren().add(loadFXML(fxml, message));
    		getScrollPane().vvalueProperty().bind(messagesVBox.heightProperty());
    	});
    }
    
    private Parent loadFXML(String file, Message message) {
    	try {
    		FXMLLoader fxml = new FXMLLoader(getClass().getResource(file));
    		Parent parent = fxml.load();
    		
    		MessageController controller = fxml.getController();
    		controller.getUsername().setText(message.getUsername());
    		controller.getMessageArea().setText(message.getContent());
    		
    		String style = "-fx-background-radius: 5;";
    		style += " -fx-background-color: " + message.getColor().getHexStringColor() + ";";
    		controller.especifyStyle(style, (message.getColor().isBackgroundDark() ? "WHITE" : "BLACK"));
    		
			return parent;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
    	try { 
			refreshPane((Message) evt.getNewValue(), "../view/secundary-message.fxml");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
	public Client getClient() throws AccessException, RemoteException, NotBoundException, Exception {
		if(client == null)
			client = new Client((ServerIF) LocateRegistry.getRegistry(1099).lookup("chat-rmi"), this);
		return client;
	}

	public TextArea getCurrentMessage() { return currentMessage; }

	public void setCurrentMessage(TextArea currentMessage) { this.currentMessage = currentMessage; }

	public VBox getMessagesVBox() { return messagesVBox; }

	public Stage getStage() { return stage; }

	public void setStage(Stage stage) { this.stage = stage; }

	public Parent getParent() { return parent; }

	public void setParent(Parent parent) { this.parent = parent; }

	public ScrollPane getScrollPane() {	return scrollPane; }

}