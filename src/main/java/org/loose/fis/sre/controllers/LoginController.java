package org.loose.fis.sre.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class LoginController {

    @FXML
    private Button loginButton;

    @FXML
    private Button createAccountButton;

    @FXML
    private Text registrationMessage;



    public void handleLoginAction() {
        //TODO
    }

    public void handleCreateAccountAction() throws Exception{
        //TODO
        Stage primary = (Stage) loginButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("register.fxml"));
        Scene nextScene = new Scene(root, 800, 600);

        primary.setScene(nextScene);
    }
}
