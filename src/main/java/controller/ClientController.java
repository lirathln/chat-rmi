package controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.prefs.Preferences;

import factory.MessagePaneControllerFactory;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Client;
import model.Message;
import model.SerializableColor;
import model.ServerIF;

public class ClientController extends Controller implements PropertyChangeListener {

	private static final long serialVersionUID = -5510985359823935020L;
	private Client client;
	private Stage stage;
	private Parent parent;
	private MessagePaneController paneMessage;

	@FXML
	private TextArea currentMessage;

	@FXML
	private ScrollPane scrollPane;

	public ClientController() throws AccessException, RemoteException, NotBoundException, Exception {
		System.out.println("ClientController");
	}

	public void createScreen(String username, Color color) {
		try {
			getClient().setUsername(username);
			getClient().setColor(new SerializableColor(color));

			setStage(new Stage());
			getStage().setTitle(getClient().getUsername());
			getStage().setScene(new Scene(getParent(), 600, 400));
			getStage().show();
		} catch (Exception e) {
			this.errorAlert("Procure o administrador para resolução o problema: \n\t" + e.fillInStackTrace());
		}
	}

	@FXML
	void sendMessage(MouseEvent event) {
		if (!getCurrentMessage().getText().isEmpty()) {
			try {
				Message message = new Message(getClient().getUsername(), getCurrentMessage().getText(),
						getClient().getColor());
				getClient().getServer().broadcastMessage(message);
				refreshPane(message, "../view/primary-message.fxml");
				getCurrentMessage().clear();
			} catch (RemoteException e) {
				this.errorAlert("Procure o administrador para resolução o problema: \n\t" + e.fillInStackTrace());

			} catch (Exception e) {
				this.errorAlert("Procure o administrador para resolução o problema: \n\t" + e.fillInStackTrace());
			}
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		try {
			if (evt.getPropertyName() == "newMessage")
				refreshPane((Message) evt.getNewValue(), "../view/secundary-message.fxml");
		} catch (Exception e) {
			this.errorAlert("Procure o administrador para resolução o problema: \n\t" + e.fillInStackTrace());
		}
	}

	private void refreshPane(Message message, String fxml) throws Exception {
		Platform.runLater(() -> {
			try {
				getPaneMessage().addMessage(message, fxml);
				getScrollPane().vvalueProperty().bind(getPaneMessage().getMessagesVBox().heightProperty());
			} catch (IOException e) {
				this.errorAlert("Procure o administrador para resolução o problema: \n\t" + e.fillInStackTrace());
			} catch (NotBoundException e) {
				this.errorAlert("Procure o administrador para resolução o problema: \n\t" + e.fillInStackTrace());
			} catch (Exception e) {
				this.errorAlert("Procure o administrador para resolução o problema: \n\t" + e.fillInStackTrace());
			}
		});
	}

	public Client getClient() throws AccessException, RemoteException, NotBoundException, Exception {
		if (client == null) {
			ServerIF server = (ServerIF) LocateRegistry
					.getRegistry(Integer.parseInt(Preferences.userRoot().get("port", null)))
					.lookup(Preferences.userRoot().get("service", null));
			client = new Client(server, this);
		}
		return client;
	}

	public TextArea getCurrentMessage() {
		return currentMessage;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public ScrollPane getScrollPane() throws NotBoundException, Exception {
		if (scrollPane == null) {
			scrollPane = new ScrollPane();
			scrollPane.setContent(paneMessage.getParent());
		}
		return scrollPane;
	}

	public MessagePaneController getPaneMessage() throws NotBoundException, Exception {
		if (paneMessage == null)
			paneMessage = MessagePaneControllerFactory.getInstance();
		return paneMessage;
	}

	public Parent getParent() {
		return parent;
	}

	public void setParent(Parent parent) {
		this.parent = parent;
	}

}