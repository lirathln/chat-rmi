package controller;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.ServerIF;
import model.ServerRMI;

public class ServerController implements Serializable {
	
	
	private static final long serialVersionUID = -7385263869628783331L;
	private ServerIF server;
	private Stage stage;
	private Parent parent;

    @FXML
    private VBox clientsConnected;

    @FXML
    private TextField host, port, service;
    
    @FXML
    private ScrollPane scrollPane;

    
    // TODO criar relogio com tempo de execução
    // TODO habilitar visualização de clientes online
    // TODO exibir mensagem de conexão
    // TODO alterar botão no handle
    public ServerController() throws UnknownHostException {
		this.clientsConnected = new VBox();
		this.scrollPane = new ScrollPane();
		this.host = new TextField();
		this.port = new TextField();
		this.service = new TextField();
	}
    
	public void createScreen() throws UnknownHostException {
		setStage(new Stage());
		getStage().setTitle("Server");
		getStage().setScene(new Scene(getParent(), 600, 400));
		getStage().show();
		
		getHost().setText(InetAddress.getLocalHost().getHostAddress());
		getPort().setText("1099");
		getService().setText("chat-rmi");
	}

	@FXML
    void handleConnectServer(MouseEvent event) throws NumberFormatException, RemoteException {
		Registry registry = LocateRegistry.createRegistry(Integer.parseInt(getPort().getText()));
		registry.rebind("chat-rmi", new ServerRMI());
    }

	public ServerIF getServer() { return server; }

	public void setServer(ServerIF server) { this.server = server; }

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

	public ScrollPane getScrollPane() { return scrollPane; }

}