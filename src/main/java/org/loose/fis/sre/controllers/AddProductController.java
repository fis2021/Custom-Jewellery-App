package org.loose.fis.sre.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.loose.fis.sre.model.Material;
import org.loose.fis.sre.services.MaterialService;
import org.loose.fis.sre.services.ProductTypeService;

public class AddProductController {

    @FXML
    private ChoiceBox type;

    @FXML
    private TextField name;

    @FXML
    private TextField price;

    @FXML
    private Text addMessage;

    @FXML
    public void initialize() {
        type.getItems().addAll("Produs", "Material");
    }

    public void handleAddButtonAction() {
        if(type.getValue().equals("Produs")){
            try {
                ProductTypeService.checkTypeDoesNotAlreadyExist(name.getText());
                ProductTypeService.addType(name.getText(), Integer.parseInt(price.getText()));

                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("viewProductsManager.fxml"));
                Stage window = (Stage) type.getScene().getWindow();
                window.setScene(new Scene(root, 800, 600));

            }catch (Exception e){
                addMessage.setText(e.getMessage());
            }
        }
        else{
            try {
                MaterialService.checkMaterialDoesNotAlreadyExist(name.getText());
                MaterialService.addMaterial(name.getText(), Integer.parseInt(price.getText()));

                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("viewProductsManager.fxml"));
                Stage window = (Stage) type.getScene().getWindow();
                window.setScene(new Scene(root, 800, 600));
            }catch (Exception e) {
                addMessage.setText(e.getMessage());
            }
        }
    }

    public void handleCancelButtonAction() throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("viewProductsManager.fxml"));
        Stage window = (Stage) type.getScene().getWindow();
        window.setScene(new Scene(root, 800, 600));
    }
}
