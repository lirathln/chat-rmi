package controller;

import java.io.Serializable;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public abstract class Controller implements Serializable {

	private static final long serialVersionUID = 8844043160047292023L;

	public void informationAlert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Sucesso");
		alert.setContentText(message);
		alert.show();

		// TODO notificação temporária. ERRO: não exibe mensagem
//		try {
//			Double opacity = alert.getDialogPane().getScene().getWindow().getOpacity();
//			
//			while (opacity > 0) {
//				alert.getDialogPane().getScene().getWindow().setOpacity(opacity);
//				Thread.sleep(30);
//				opacity -= 0.01;
//			}
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		alert.close();
	}

	public void errorAlert(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Ops... algo deu errado!");
		alert.setHeaderText(message);
		alert.show();
	}

	public void warningAlert(String message) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Alerta");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.show();
	}

	public boolean confirmationAlert(String message) {
		Alert alert = new Alert(AlertType.CONFIRMATION, "Escolha uma opção: ", new ButtonType("Confirmar"),
				new ButtonType("Cancelar"));
		alert.setTitle("Confirmação");
		alert.setHeaderText(message);
		alert.showAndWait();
		return alert.getResult().getText() == "Confirmar" ? true : false;
	}
}