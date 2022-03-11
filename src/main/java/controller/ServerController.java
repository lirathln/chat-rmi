package controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.ClientIF;
import model.Clock;
import model.ServerRMI;

public class ServerController implements PropertyChangeListener, Serializable {

	private static final long serialVersionUID = -7385263869628783331L;
	private Stage stage;
	private Parent parent;
	private Registry registry;
	private ServerRMI server;
	private Clock clock;
	private Thread thread;

	@FXML
	private VBox clientsConnected;

	@FXML
	private TextField host, port, service;

	@FXML
	private ScrollPane scrollPane;

	@FXML
	private Label timer;

	// TODO exibir mensagem de conexão
	// TODO alterar botão no handle
	public ServerController() throws UnknownHostException, RemoteException {
		this.server = new ServerRMI(this); // provavelmente remover
		this.clock = new Clock();
		
		this.clientsConnected = new VBox();
		this.scrollPane = new ScrollPane();
		this.host = new TextField();
		this.port = new TextField();
		this.service = new TextField();
		this.timer = new Label();
	}

	public void createScreen() throws UnknownHostException {
		setStage(new Stage());
		getStage().setTitle("Server");
		getStage().setScene(new Scene(getParent(), 600, 400));
		getStage().show();

		getHost().setText(InetAddress.getLocalHost().getHostAddress());
		getPort().setText("1099");
		getService().setText("chat-rmi");
		getTimer().setText(getClock().timerString());
	}

	@FXML
	void handleConnectServer(MouseEvent event) throws NumberFormatException, RemoteException, NotBoundException {
		if (getTimer().isDisable())
			startServer();
		else
			stopServer();
	}
	
	private void startServer() {
		try {
			getRegistry().rebind(getService().getText(), getServer());
			getTimer().setDisable(false);
			setThread(new Thread(() -> runTimer()));
			getThread().start();
			System.out.println("Server online");
		} catch (AccessException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private void stopServer() {
		try {
			getRegistry().unbind(getService().getText());
			UnicastRemoteObject.unexportObject(getServer(), true); // erro ao para servidor pela segunda vez
			getTimer().setDisable(true);
			getThread().interrupt();
			System.out.println("Server offline");
		} catch (AccessException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	private void runTimer() {
		while (!getTimer().isDisable()) {
			Platform.runLater(() -> {
				getTimer().setText(getClock().timerString());
			});
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			getClock().addSecond();
		}
		
		getTimer().setText("00 : 00 : 00");
	}
	
    private void refreshPane(ClientIF client) throws Exception {
    	Platform.runLater(() -> {
    		getClientsConnected().getChildren().add(loadFXML(client));
    	});
    }
	
    private Parent loadFXML(ClientIF client) {
    	try {
    		FXMLLoader fxml = new FXMLLoader(getClass().getResource("../view/list-item.fxml"));
    		Parent parent = fxml.load();
    		
    		ListController controller = fxml.getController();
    		controller.setServer(getServer());
    		controller.getId().setText(Integer.toString(ServerRMI.getClients().size()));
    		controller.getUsername().setText(client.getUsername());
    		controller.getColor().setText(client.getColor().getHexStringColor());
    		
    		return parent;
    	} catch (IOException e) {
    		e.printStackTrace();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
    	try { 
    		System.out.println(evt.getPropertyName() + " : " + evt.getOldValue());
    		if (evt.getPropertyName() == "registerClient")
    			refreshPane((ClientIF) evt.getNewValue());
			if (evt.getPropertyName() == "removeClient")
				getClientsConnected().getChildren().remove(Integer.parseInt(evt.getOldValue().toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	public Registry getRegistry() throws NumberFormatException, RemoteException { 
		if (registry == null)
			registry = LocateRegistry.createRegistry(Integer.parseInt(getPort().getText()));
		return registry; 
	}

	public Clock getClock() { return clock;	}

	public void setClock(Clock clock) { this.clock = clock;	}

	public ServerRMI getServer() { return server; }

	public Stage getStage() { return stage;	}

	public void setStage(Stage stage) {	this.stage = stage;	}

	public Parent getParent() {	return parent; }

	public void setParent(Parent parent) { this.parent = parent; }

	public VBox getClientsConnected() { return clientsConnected; }

	public TextField getHost() { return host; }

	public void setHost(TextField host) { this.host = host;	}

	public TextField getPort() { return port; }

	public void setPort(TextField port) { this.port = port;	}

	public TextField getService() { return service;	}

	public void setService(TextField service) { this.service = service;	}

	public ScrollPane getScrollPane() { return scrollPane;	}

	public Label getTimer() { return timer;	}

	public Thread getThread() { return thread; }

	public void setThread(Thread thread) { this.thread = thread; }

}