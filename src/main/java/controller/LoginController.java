package controller;

import java.io.Serializable;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class LoginController implements Serializable {

	private static final long serialVersionUID = -5197400721736490832L;
	private Stage stage;
	private Parent parent;

	@FXML
    private ImageView image;

    @FXML
    private ColorPicker textColor;

    @FXML
    private TextField username;
    
	@FXML
    void accessChat(ActionEvent event) {
    	System.out.println("Nome: " + username.getText());
    	System.out.println("Cor: " + textColor.getValue());
    	System.out.println("Imagem :" + image.getImage().getUrl());
    	
    	
    }

}