package org.loose.fis.cja.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.loose.fis.cja.model.Material;
import org.loose.fis.cja.model.ProductType;
import org.loose.fis.cja.services.MaterialService;
import org.loose.fis.cja.services.ProductTypeService;

import java.util.ArrayList;

public class ModifyProductController {

    private String previous;

    @FXML
    private ChoiceBox<String> type;

    @FXML
    private ChoiceBox<String> product;

    @FXML
    private Text selectionMessage;

    @FXML
    private TextField newPrice;

    @FXML
    public void initialize() {
        type.getItems().addAll("Produs", "Material");
    }

    public void handleProductSelectAction() {
        if(previous != null){
            if(!previous.equals(type.getValue())) {
                product.getSelectionModel().clearSelection();
                product.getItems().clear();

            }
        }
        if(product.getItems().isEmpty()) {
            if (type.getValue() == null) {
                selectionMessage.setText("Nu ati selectat inca tipul");
                return;
            }
            previous = type.getValue();
            if (type.getValue().equals("Produs")) {
                selectionMessage.setText("");
                ArrayList<String> products = new ArrayList<>();
                for (ProductType productType : ProductTypeService.getAllProductTypes())
                    products.add(productType.getType());
                product.getItems().addAll(products);
                product.show();
            }
            else {
                selectionMessage.setText("");
                ArrayList<String> materials = new ArrayList<>();
                for (Material material : MaterialService.getAllMaterials())
                    materials.add(material.getName());
                product.getItems().addAll(materials);
                product.show();
            }
        }
    }

    public void handleModifyButtonAction() throws Exception {

        if(product.getValue() == null){
            selectionMessage.setText("Nu este selectat niciun produs");
            return;
        }

        if(newPrice.getText().equals("")){
            selectionMessage.setText("Nu ati introdus niciun pret");
            return;
        }
        if(type.getValue().equals("Produs")){
            ProductTypeService.modifyPrice(product.getValue(), Integer.parseInt(newPrice.getText()));
        }
        else {
            MaterialService.modifyPrice(product.getValue(), Integer.parseInt(newPrice.getText()));
        }

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/viewProductsManager.fxml"));
        Stage window = (Stage) type.getScene().getWindow();
        window.setScene(new Scene(root, 800, 600));
    }

    public void handleCancelButtonAction() throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/viewProductsManager.fxml"));
        Stage window = (Stage) type.getScene().getWindow();
        window.setScene(new Scene(root, 800, 600));
    }
}
