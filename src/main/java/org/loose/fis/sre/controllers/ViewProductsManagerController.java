package org.loose.fis.sre.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.loose.fis.sre.model.Material;
import org.loose.fis.sre.model.ProductType;
import org.loose.fis.sre.services.MaterialService;
import org.loose.fis.sre.services.ProductTypeService;

public class ViewProductsManagerController {

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

    public void handleAddButtonAction() throws Exception{
        Stage window = (Stage) typeTable.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/addProduct.fxml"));
        window.setScene(new Scene(root, 800,600));
    }

    public void handleDeleteTypeButtonAction() {
        ObservableList<ProductType> typesSelected;
        typesSelected = typeTable.getSelectionModel().getSelectedItems();
        for(ProductType productType : typesSelected) {
            ProductTypeService.removeType(productType);
        }
        typesSelected.forEach(types::remove);
    }

    public void handleDeleteMaterialButtonAction() {
        ObservableList<Material> materialSelected;
        materialSelected = materialTable.getSelectionModel().getSelectedItems();
        for(Material material : materialSelected) {
            MaterialService.removeMaterial(material);
        }
        materialSelected.forEach(materials::remove);
    }

    public void handleModifyPriceButtonAction() throws Exception{
        Stage window = (Stage) typeTable.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/modifyProduct.fxml"));
        window.setScene(new Scene(root, 800,600));
    }

    public void handleBackButtonAction() throws Exception{
        Stage window = (Stage) typeTable.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/startManager.fxml"));
        window.setScene(new Scene(root, 800,600));
    }

    private ObservableList<ProductType> types = FXCollections.observableArrayList(ProductTypeService.productTypes());
    private ObservableList<Material> materials = FXCollections.observableArrayList(MaterialService.getAllMaterials());
}