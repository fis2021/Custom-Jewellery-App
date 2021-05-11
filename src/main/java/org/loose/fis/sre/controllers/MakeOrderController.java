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
import org.loose.fis.sre.model.User;
import org.loose.fis.sre.services.MaterialService;
import org.loose.fis.sre.services.OrderService;
import org.loose.fis.sre.services.ProductTypeService;


import java.util.ArrayList;

public class MakeOrderController {
    @FXML
    private ChoiceBox<String> type;
    @FXML
    private ChoiceBox<String> material;
    @FXML
    private TextField message;
    @FXML
    private Text orderMessage;

    @FXML
    public void initialize() {
        ArrayList<String> types = new ArrayList<>();
        for (ProductType productType : ProductTypeService.productTypes())
            types.add(productType.getType());
        type.getItems().addAll(types);

        ArrayList<String> materials = new ArrayList<>();
        for (Material material : MaterialService.getAllMaterials())
            materials.add(material.getName());
        material.getItems().addAll(materials);

    }

    public void handleMakeOrderButtonAction() throws Exception{
        if(type.getValue() == null){
            orderMessage.setText("Nu este selectat niciun produs");
            return;
        }
        if(material.getValue() == null){
            orderMessage.setText("Nu este selectat niciun material");
            return;
        }

        ProductType productType=ProductTypeService.getProductType(type.getValue());
        Material material1=MaterialService.getMaterial(material.getValue());
        OrderService.addOrder(User.getCurrentUser(),productType,material1,message.getText());

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/startUser.fxml"));
        Stage window = (Stage) type.getScene().getWindow();
        window.setScene(new Scene(root, 800, 600));
    }

    public void handleCancelButtonAction() throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/startUser.fxml"));
        Stage window = (Stage) type.getScene().getWindow();
        window.setScene(new Scene(root, 800, 600));
    }



}
