package controller;

import java.rmi.RemoteException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import model.ServerIF;

public class ListController extends Controller {

	private static final long serialVersionUID = -7027491841452445473L;
	private ServerIF server;
	
	@FXML
	private GridPane gridPane;
	
	@FXML
    private Label id, username, color;

    
    public ListController() {
		this.gridPane = new GridPane();
		this.id = new Label();
		this.username = new Label();
		this.color = new Label();
	}

	@FXML
    void changeClient(MouseEvent event) {
		System.out.println("change client: " + event.getTarget());
    }

    @FXML
    void removeClient(MouseEvent event) {
    	try {
			getServer().removeClient(Integer.parseInt(getId().getText()) - 1);
		} catch (NumberFormatException e) {
			this.errorAlert("Procure o administrador para resolução o problema: \n\t" + e.fillInStackTrace());
		} catch (RemoteException e) {
			this.errorAlert("Procure o administrador para resolução o problema: \n\t" + e.fillInStackTrace());
		}
    }
    
    
	public ServerIF getServer() { return server; }

	public void setServer(ServerIF server) { this.server = server; }

	public GridPane getGridPane() { return gridPane; }

	public Label getId() { return id; }

	public void setId(Label id) { this.id = id; }

	public Label getUsername() { return username; }

	public void setUsername(Label username) { this.username = username;	}

	public Label getColor() { return color;	}

	public void setColor(Label color) { this.color = color;	}

}
