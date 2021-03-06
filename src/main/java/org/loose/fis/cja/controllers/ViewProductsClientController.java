package org.loose.fis.cja.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.loose.fis.cja.model.Material;
import org.loose.fis.cja.model.ProductType;
import org.loose.fis.cja.services.MaterialService;
import org.loose.fis.cja.services.ProductTypeService;

import java.util.List;

public class ViewProductsClientController {
    @FXML
    private Button backButton;

    @FXML
    private TableView<ProductType> typeTable;
    @FXML
    private TableColumn<ProductType, String> productTypeColumn;
    @FXML
    private TableColumn<ProductType, Integer> productPriceColumn;

    @FXML
    private TableView<Material> materialTable;
    @FXML
    private TableColumn<Material, String> materialNameColumn;
    @FXML
    private TableColumn<Material, Integer> materialPriceColumn;

    public void initialize() {
        productTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        typeTable.setItems(types);

        materialNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        materialPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        materialTable.setItems(materials);
    }

    private ObservableList<ProductType> types = FXCollections.observableArrayList(ProductTypeService.getAllProductTypes());
    private ObservableList<Material> materials = FXCollections.observableArrayList(MaterialService.getAllMaterials());

    public void handleBackButtonAction() throws Exception{
        Stage window = (Stage) materialTable.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/startUser.fxml"));
        window.setScene(new Scene(root, 800,600));
    }

    public List<ProductType> getProductTypesFromTable() {
        return typeTable.getItems();
    }

    public List<Material> getMaterialsFromTable() {
        return materialTable.getItems();
    }
}
