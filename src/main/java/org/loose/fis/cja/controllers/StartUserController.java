package org.loose.fis.cja.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class StartUserController {

    @FXML
    private Button logOutButton;

    public void handleLodOutButtonAction() throws Exception{
        Stage window = (Stage) logOutButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/login.fxml"));
        window.setScene(new Scene(root, 800,600));
    }

    public void handleViewProductsButtonAction() throws Exception{
        Stage window = (Stage) logOutButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/viewProductsClient.fxml"));
        window.setScene(new Scene(root, 800,600));
    }

    public void handleViewOrdersButtonAction() throws Exception{
        Stage window = (Stage) logOutButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/viewOrdersClient.fxml"));
        window.setScene(new Scene(root, 800,600));
    }

    public void handleMakeOrderButtonAction() throws Exception{
        Stage window = (Stage) logOutButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/makeOrder.fxml"));
        window.setScene(new Scene(root, 800,600));
    }
}