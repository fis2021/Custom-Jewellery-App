package org.loose.fis.sre.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loose.fis.sre.model.Material;
import org.loose.fis.sre.model.ProductType;
import org.loose.fis.sre.services.FileSystemService;
import org.loose.fis.sre.services.MaterialService;
import org.loose.fis.sre.services.OrderService;
import org.loose.fis.sre.services.ProductTypeService;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
class ViewProductsManagerControllerTest {

    private static final String TYPE = "type";
    private static final String MATERIAL = "material";
    private static final int PRICE = 0;
    private static final int NEW_PRICE = 100;
    private static final String NEW_NAME = "new_name";
    private static final String MATERIAL_TYPE = "Material";
    private static final String PRODUCT_TYPE = "Produs";

    private ViewProductsManagerController controller;

    @BeforeEach
    void setUp(){
        FileSystemService.APPLICATION_FOLDER = ".test-jewellery-databases";
    }

    @AfterEach
    void tearDown() {
        MaterialService.close();
        ProductTypeService.close();
    }

    @Start
    void start(Stage primaryStage) throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-jewellery-databases";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        ProductTypeService.initDatabase();
        MaterialService.initDatabase();
        ProductTypeService.addType(TYPE, PRICE);
        MaterialService.addMaterial(MATERIAL, PRICE);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/viewProductsManager.fxml"));
        Parent root = fxmlLoader.load();
        controller = fxmlLoader.getController();
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setTitle("Login-test");
        primaryStage.show();
    }

    @Test
    @DisplayName("Correct product type displayed")
    void testProductTypeDisplayed() {
        assertThat(controller.getProductTypesFromTable().size()).isEqualTo(1);

        ProductType productType = controller.getProductTypesFromTable().get(0);
        assertThat(productType.getType()).isEqualTo(TYPE);
        assertThat(productType.getPrice()).isEqualTo(PRICE);
    }

    @Test
    @DisplayName("Correct material displayed")
    void testMaterialDisplayed() {
        assertThat(controller.getMaterialsFromTable().size()).isEqualTo(1);

        Material material = controller.getMaterialsFromTable().get(0);
        assertThat(material.getName()).isEqualTo(MATERIAL);
        assertThat(material.getPrice()).isEqualTo(PRICE);
    }

    @Test
    @DisplayName("Correct product type and material displayed simultaneously")
    void testProductTypeAndMaterialDisplayed() {
        assertThat(controller.getMaterialsFromTable().size()).isEqualTo(1);
        assertThat(controller.getProductTypesFromTable().size()).isEqualTo(1);

        Material material = controller.getMaterialsFromTable().get(0);
        assertThat(material.getName()).isEqualTo(MATERIAL);
        assertThat(material.getPrice()).isEqualTo(PRICE);

        ProductType productType = controller.getProductTypesFromTable().get(0);
        assertThat(productType.getType()).isEqualTo(TYPE);
        assertThat(productType.getPrice()).isEqualTo(PRICE);
    }

    @Test
    @DisplayName("Testing add button")
    void testAddButtonFunctionality(FxRobot robot) {
        robot.clickOn("#addButton");
        robot.clickOn("#addProductFile");
    }

    @Test
    @DisplayName("Testing add new Product Type successfully")
    void testAddNewProductTypeSuccessfully(FxRobot robot) {
        robot.clickOn("#addButton");

        robot.clickOn("#name");
        robot.write(NEW_NAME);
        robot.clickOn("#price");
        robot.write(Integer.toString(PRICE));
        robot.clickOn("#type");
        robot.clickOn(PRODUCT_TYPE);
        robot.clickOn("#addButton");

        robot.clickOn("#viewProductsManagerFile");

        assertThat(ProductTypeService.getAllProductTypes().size()).isEqualTo(2);

        ProductType productType = ProductTypeService.getAllProductTypes().get(0);
        assertThat(productType.getType()).isEqualTo(TYPE);
        assertThat(productType.getPrice()).isEqualTo(PRICE);

        productType = ProductTypeService.getAllProductTypes().get(1);
        assertThat(productType.getType()).isEqualTo(NEW_NAME);
        assertThat(productType.getPrice()).isEqualTo(PRICE);
    }

    @Test
    @DisplayName("Testing add new Product Type already exists")
    void testAddNewProductTypeAlreadyExists(FxRobot robot) {
        robot.clickOn("#addButton");

        robot.clickOn("#name");
        robot.write(TYPE);
        robot.clickOn("#price");
        robot.write(Integer.toString(PRICE));
        robot.clickOn("#type");
        robot.clickOn(PRODUCT_TYPE);
        robot.clickOn("#addButton");

        assertThat(robot.lookup("#addMessage").queryText().getText()).isEqualTo(String.format("A product with the type %s already exists!", TYPE));
    }

    @Test
    @DisplayName("Testing add new material successfully")
    void testAddNewMaterialSuccessfully(FxRobot robot) {
        robot.clickOn("#addButton");

        robot.clickOn("#name");
        robot.write(NEW_NAME);
        robot.clickOn("#price");
        robot.write(Integer.toString(PRICE));
        robot.clickOn("#type");
        robot.clickOn(MATERIAL_TYPE);
        robot.clickOn("#addButton");

        robot.clickOn("#viewProductsManagerFile");

        assertThat(MaterialService.getAllMaterials().size()).isEqualTo(2);

        Material material = MaterialService.getAllMaterials().get(0);
        assertThat(material.getName()).isEqualTo(MATERIAL);
        assertThat(material.getPrice()).isEqualTo(PRICE);

        material = MaterialService.getAllMaterials().get(1);
        assertThat(material.getName()).isEqualTo(NEW_NAME);
        assertThat(material.getPrice()).isEqualTo(PRICE);
    }

    @Test
    @DisplayName("Testing add new material already exists")
    void testAddNewMaterialAlreadyExists(FxRobot robot) {
        robot.clickOn("#addButton");

        robot.clickOn("#name");
        robot.write(MATERIAL);
        robot.clickOn("#price");
        robot.write(Integer.toString(PRICE));
        robot.clickOn("#type");
        robot.clickOn(MATERIAL_TYPE);
        robot.clickOn("#addButton");

        assertThat(robot.lookup("#addMessage").queryText().getText()).isEqualTo(String.format("A material with the name %s already exists!", MATERIAL));
    }

    @Test
    @DisplayName("Testing cancel button from adding")
    void testCancelButtonFromAddingProduct(FxRobot robot) {
        robot.clickOn("#addButton");
        robot.clickOn("#cancelButton");
        robot.clickOn("#viewProductsManagerFile");
    }

    @Test
    @DisplayName("Testing delete a product type")
    void testDeleteProductType(FxRobot robot) {
        addNew(robot, PRODUCT_TYPE);
        robot.clickOn(NEW_NAME);

        assertThat(ProductTypeService.getAllProductTypes().size()).isEqualTo(2);
        robot.clickOn("#deleteTypeButton");

        assertThat(ProductTypeService.getAllProductTypes()).isNotEmpty();
        assertThat(ProductTypeService.getAllProductTypes().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Testing delete a material")
    void testDeleteMaterial(FxRobot robot) {
        addNew(robot, MATERIAL_TYPE);
        robot.clickOn(NEW_NAME);

        assertThat(MaterialService.getAllMaterials().size()).isEqualTo(2);
        robot.clickOn("#deleteMaterialButton");

        assertThat(MaterialService.getAllMaterials()).isNotEmpty();
        assertThat(MaterialService.getAllMaterials().size()).isEqualTo(1);
    }

    private void addNew(FxRobot robot, String type){
        robot.clickOn("#addButton");

        robot.clickOn("#name");
        robot.write(NEW_NAME);
        robot.clickOn("#price");
        robot.write(Integer.toString(PRICE));
        robot.clickOn("#type");
        robot.clickOn(type);
        robot.clickOn("#addButton");
    }

    @Test
    @DisplayName("Testing modify button")
    void testModifyButton(FxRobot robot) {
        robot.clickOn("#modifyButton");
        robot.clickOn("#modifyProductFile");
    }

    @Test
    @DisplayName("Testing modify - no product selected")
    void testModifyNoProductSelected(FxRobot robot) {
        robot.clickOn("#modifyButton");
        robot.clickOn("#modifyButton");

        assertThat(robot.lookup("#selectionMessage").queryText().getText()).isEqualTo("Nu este selectat niciun produs");
    }

    @Test
    @DisplayName("Testing modify - no type selected")
    void testModifyNoTypeSelected(FxRobot robot) {
        robot.clickOn("#modifyButton");
        robot.clickOn("#product");

        assertThat(robot.lookup("#selectionMessage").queryText().getText()).isEqualTo("Nu ati selectat inca tipul");
    }

    @Test
    @DisplayName("Testing modify - no price modified")
    void testModifyNoPriceModified(FxRobot robot) {
        robot.clickOn("#modifyButton");
        robot.clickOn("#product");
        robot.clickOn("#type");
        robot.clickOn(PRODUCT_TYPE);
        robot.clickOn("#product");
        robot.clickOn(TYPE);
        robot.clickOn("#modifyButton");

        assertThat(robot.lookup("#selectionMessage").queryText().getText()).isEqualTo("Nu ati introdus niciun pret");
    }

    @Test
    @DisplayName("Testing product type modifying")
    void testProductTypeModifiedSuccessfully(FxRobot robot) {
        robot.clickOn("#modifyButton");
        robot.clickOn("#product");
        robot.clickOn("#type");
        robot.clickOn(PRODUCT_TYPE);
        robot.clickOn("#product");
        robot.clickOn(TYPE);
        robot.clickOn("#price");
        robot.write(String.valueOf(NEW_PRICE));
        robot.clickOn("#modifyButton");

        robot.clickOn("#viewProductsManagerFile");
        assertThat(ProductTypeService.getAllProductTypes().size()).isEqualTo(1);

        ProductType productType = ProductTypeService.getAllProductTypes().get(0);
        assertThat(productType.getType()).isEqualTo(TYPE);
        assertThat(productType.getPrice()).isEqualTo(NEW_PRICE);
    }

    @Test
    @DisplayName("Testing material modifying")
    void testMaterialModifiedSuccessfully(FxRobot robot) {
        robot.clickOn("#modifyButton");
        robot.clickOn("#product");
        robot.clickOn("#type");
        robot.clickOn(MATERIAL_TYPE);
        robot.clickOn("#product");
        robot.clickOn(MATERIAL);
        robot.clickOn("#price");
        robot.write(String.valueOf(NEW_PRICE));
        robot.clickOn("#modifyButton");

        robot.clickOn("#viewProductsManagerFile");
        assertThat(MaterialService.getAllMaterials().size()).isEqualTo(1);

        Material material = MaterialService.getAllMaterials().get(0);
        assertThat(material.getName()).isEqualTo(MATERIAL);
        assertThat(material.getPrice()).isEqualTo(NEW_PRICE);
    }

    @Test
    @DisplayName("Testing cancel button from modify interface")
    void testCancelButtonFromModify(FxRobot robot) {
        robot.clickOn("#modifyButton");
        robot.clickOn("#cancelButton");
        robot.clickOn("#viewProductsManagerFile");
    }

    @Test
    @DisplayName("Testing back button")
    void testBackButtonFunctionality(FxRobot robot) {
        robot.clickOn("#backButton");
        robot.clickOn("#startManagerFile");
    }
}