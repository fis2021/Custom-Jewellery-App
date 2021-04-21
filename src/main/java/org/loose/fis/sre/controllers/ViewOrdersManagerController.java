package org.loose.fis.sre.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ViewOrdersManagerController {

    @FXML
    private Button backButton;

    public void handleBackButtonAction() throws Exception{
        Stage window = (Stage) backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/startManager.fxml"));
        window.setScene(new Scene(root, 800,600));
    }
}
