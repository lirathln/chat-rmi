package controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import javax.swing.JOptionPane;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import client.Client;
import server.Message;
import server.ServerIF;

public class ClientController implements PropertyChangeListener, Serializable {
	
	private static final long serialVersionUID = -5510985359823935020L;
	private Client client;
	private String location = "../view/";

    @FXML
    private TextArea currentMessage;
    
    @FXML
    private VBox messagesVBox;
    
    @FXML
    private ScrollPane scrollPane;
    
    
    public ClientController() {
    	try {
    		getClient();
    		scrollPane = new ScrollPane();
    		messagesVBox = new VBox();
		} catch (AccessException ae) {
			ae.printStackTrace();
		} catch (RemoteException re) {
			re.printStackTrace();
		} catch (NotBoundException nbe) {
			nbe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	@FXML
    void sendMessage(MouseEvent event) throws Exception {
    	if (!currentMessage.getText().isEmpty()) {
    		try {
    			Message message = new Message(getClient().getName(), currentMessage.getText());
				getClient().getServer().broadcastMessage(message);
				refreshPane(message, location + "primary-message.fxml");
				currentMessage.clear();
			} catch (RemoteException re) {
				re.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    }
    
    private void refreshPane(Message message, String fxml) throws Exception {
    	Platform.runLater(() -> {
    		messagesVBox.getChildren().add(loadFXML(fxml, message));
    		scrollPane.vvalueProperty().bind(messagesVBox.heightProperty());
    	});
    }
    
    private Parent loadFXML(String file, Message message) {
    	try {
    		FXMLLoader fxml = new FXMLLoader(getClass().getResource(file));
    		Parent parent = fxml.load();
    		MessageController controller = fxml.getController();
    		controller.getUsername().setText(message.getUser());
    		controller.getMessageArea().setText(message.getContent());
    		
			return parent;
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
		return null;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
    	try { 
			refreshPane((Message) evt.getNewValue(), location + "secundary-message.fxml");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
	public Client getClient() throws AccessException, RemoteException, NotBoundException, Exception {
		if(client == null)
			client = new Client(JOptionPane.showInputDialog("Insira o nome: "), (ServerIF) LocateRegistry.getRegistry(1099).lookup("chat-rmi"), this);
		return client;
	}

	public VBox getMessagesVBox() {
		if (messagesVBox == null)
			messagesVBox = new VBox();
		return messagesVBox;
	}
	
}