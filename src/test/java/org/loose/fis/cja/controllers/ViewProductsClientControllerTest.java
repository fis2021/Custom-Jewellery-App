package org.loose.fis.cja.controllers;

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
import org.loose.fis.cja.model.Material;
import org.loose.fis.cja.model.ProductType;
import org.loose.fis.cja.services.FileSystemService;
import org.loose.fis.cja.services.MaterialService;
import org.loose.fis.cja.services.ProductTypeService;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
class ViewProductsClientControllerTest {

    private static final String TYPE = "type";
    private static final String MATERIAL = "material";
    private static final int PRICE = 0;
    private ViewProductsClientController controller;

    @BeforeEach
    void setUp(){
        FileSystemService.APPLICATION_FOLDER = ".test-jewellery-databases";
    }

    @AfterEach
    void tearDown() {
        ProductTypeService.close();
        MaterialService.close();
    }

    @Start
    void start(Stage primaryStage) throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-jewellery-databases";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        ProductTypeService.initDatabase();
        MaterialService.initDatabase();
        ProductTypeService.addType(TYPE, PRICE);
        MaterialService.addMaterial(MATERIAL, PRICE);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/viewProductsClient.fxml"));
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
    @DisplayName("Testing back button")
    void testBackButtonFunctionality(FxRobot robot) {
        robot.clickOn("#backButton");
        robot.clickOn("#startUserFile");
    }
}