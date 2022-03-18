package controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.prefs.Preferences;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.ClientIF;
import model.Clock;
import model.ServerRMI;

public class ServerController extends Controller implements PropertyChangeListener {

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
	
	@FXML
	private Button buttonServer;
	
	@FXML
	private ImageView imageButtonServer;

	// TODO exibir mensagem de conexão de clientes
	// TODO criar método para remover usuário, informando remoção ao mesmo e desabilitando tela
	public ServerController() throws UnknownHostException, RemoteException {
		this.server = new ServerRMI(this); // provavelmente remover
		this.clock = new Clock();
		
		this.clientsConnected = new VBox();
		this.scrollPane = new ScrollPane();
		this.host = new TextField();
		this.port = new TextField();
		this.service = new TextField();
		this.timer = new Label();
		this.buttonServer = new Button();
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
	void handleConnectServer(MouseEvent event) {
		if (getTimer().isDisable())
			startServer();
		else
			stopServer();
	}
	
	private void startServer() {
		try {
			getRegistry().rebind(getService().getText(), getServer());
			getTimer().setDisable(false);
			setPreferencesData();
			
			getButtonServer().setText("Parar Servidor");
			getImageButtonServer().setImage(new Image("/images/pausar-100.png"));
			setThread(new Thread(() -> runTimer()));
			getThread().start();
			this.informationAlert("Server online!");
		} catch (AccessException e) {
			this.errorAlert("Falha no serviço: " + e.getCause());
		} catch (NumberFormatException e) {
			this.errorAlert("Falha no serviço: " + e.getCause());
		} catch (RemoteException e) {
			this.errorAlert("Falha no serviço: " + e.getCause());
		}
	}
	
	private void setPreferencesData() {
		Preferences.userRoot().put("host", getHost().getText());
		Preferences.userRoot().put("port", getPort().getText());
		Preferences.userRoot().put("service", getService().getText());
	}
	
	private void stopServer() {
		try {
			if (this.confirmationAlert("Tem certeza que deseja parar o servidor?\nTodos os clientes serão desconectados.")) {
				getRegistry().unbind(getService().getText());
				UnicastRemoteObject.unexportObject(getServer(), true); // erro ao para servidor pela segunda vez
				getTimer().setDisable(true);
				getThread().interrupt();
				getButtonServer().setText("Iniciar Servidor");
				getImageButtonServer().setImage(new Image("/images/começar-100.png"));
				this.informationAlert("Server offline");
			}
		} catch (AccessException e) {
			this.errorAlert("Falha no serviço: " + e.getCause());
		} catch (NumberFormatException e) {
			this.errorAlert("Falha no serviço: " + e.getCause());
		} catch (RemoteException e) {
			this.errorAlert("Falha no serviço: " + e.getCause());
		} catch (NotBoundException e) {
			this.errorAlert("Falha no serviço: " + e.getCause());
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
				this.errorAlert("Falha no serviço: " + e.getCause());
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
    		this.errorAlert("Falha no serviço: " + e.getCause());
    	} catch (Exception e) {
    		this.errorAlert("Falha no serviço: " + e.getCause());
    	}
    	return null;
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
    	try { 
    		if (evt.getPropertyName() == "registerClient")
    			refreshPane((ClientIF) evt.getNewValue());
			if (evt.getPropertyName() == "removeClient") {
				getClientsConnected().getChildren().remove(Integer.parseInt(evt.getOldValue().toString()));
			}
		} catch (Exception e) {
			this.errorAlert("Falha no serviço: " + e.getCause());
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

	public Button getButtonServer() { return buttonServer; }

	public void setButtonServer(Button buttonServer) { this.buttonServer = buttonServer; }

	public ImageView getImageButtonServer() { return imageButtonServer;	}

	public void setImageButtonServer(ImageView imageButtonServer) { this.imageButtonServer = imageButtonServer;	}

}