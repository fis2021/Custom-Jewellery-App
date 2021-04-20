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
import org.loose.fis.sre.model.ProductType;
import org.loose.fis.sre.services.MaterialService;
import org.loose.fis.sre.services.ProductTypeService;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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
            if(previous.equals(type.getValue()) == false) {
                product.getSelectionModel().clearSelection();
                product.getItems().clear();
                System.out.println("haha");
            }
        }
        if(product.getItems().isEmpty()) {
            if (type.getValue() == null) {
                selectionMessage.setText("Nu ati selectat inca tipul");
                return;
            }
            previous = type.getValue().toString();
            if (type.getValue().equals("Produs")) {
                selectionMessage.setText("");
                ArrayList<String> products = new ArrayList<>();
                for (ProductType productType : ProductTypeService.productTypes())
                    products.add(productType.getType());
                product.getItems().addAll(products);
                product.show();
            }
            else {
                selectionMessage.setText("");
                ArrayList<String> materials = new ArrayList<>();
                for (Material material : MaterialService.materials())
                    materials.add(material.getName());
                product.getItems().addAll(materials);
                product.show();
            }
        }
    }
}
