package controller;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Clock;
import model.ServerRMI;

public class ServerController implements Serializable {

	private static final long serialVersionUID = -7385263869628783331L;
	private Stage stage;
	private Parent parent;
	private Clock clock;

	@FXML
	private VBox clientsConnected;

	@FXML
	private TextField host, port, service;

	@FXML
	private ScrollPane scrollPane;

	@FXML
	private Label timer;

	// TODO habilitar visualização de clientes online
	// TODO exibir mensagem de conexão
	// TODO alterar botão no handle
	public ServerController() throws UnknownHostException {
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
		getTimer().setText("00 : 00 : 00");
	}

	@FXML
	void handleConnectServer(MouseEvent event) throws NumberFormatException, RemoteException {
		Registry registry = LocateRegistry.createRegistry(Integer.parseInt(getPort().getText()));
		registry.rebind("chat-rmi", new ServerRMI());
		System.out.println("Server online");

		new Thread(() -> runTimer()).start();
		getTimer().setDisable(false);
	}

	private void runTimer() {
		while (getStage().isShowing()) {
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
	}

	public Clock getClock() { return clock;	}

	public void setClock(Clock clock) { this.clock = clock;	}

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

}