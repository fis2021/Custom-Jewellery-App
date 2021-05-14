package org.loose.fis.cja.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.loose.fis.cja.exceptions.UsernameAlreadyExistsException;
import org.loose.fis.cja.services.UserService;

public class RegistrationController {



    @FXML
    private Text registrationMessage;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField lastnameField;
    @FXML
    private TextField firstnameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField phoneField;
    @FXML
    private ChoiceBox role;
    @FXML
    private ChoiceBox sex;

    @FXML
    public void initialize() {
        role.getItems().addAll("Client", "Manager");
        sex.getItems().addAll("Barbat", "Femeie");
    }

    @FXML
    public void handleRegisterAction() {
        if(lastnameField.getText().equals("") || firstnameField.getText().equals("") || addressField.getText().equals("") ||
           phoneField.getText().equals("") || usernameField.getText().equals("") || passwordField.getText().equals("") ||
           role.getValue() == null || sex.getValue() == null){
            registrationMessage.setText("Not all fields are set");
            return;
        }
        try {
            UserService.addUser(lastnameField.getText(), firstnameField.getText(), addressField.getText(), phoneField.getText(), (String) sex.getValue(), usernameField.getText(), passwordField.getText(), (String) role.getValue());
            registrationMessage.setText("Account created successfully!");
        } catch (UsernameAlreadyExistsException e) {
            registrationMessage.setText(e.getMessage());
        }
    }

    public void handleBackAction() throws Exception{
        Stage primaryStage = (Stage) role.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/login.fxml"));
        Scene newScene = new Scene(root, 800, 600);
        primaryStage.setScene(newScene);
    }
}