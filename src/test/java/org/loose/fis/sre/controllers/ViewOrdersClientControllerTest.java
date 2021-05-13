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
import org.loose.fis.sre.model.Order;
import org.loose.fis.sre.model.User;
import org.loose.fis.sre.services.FileSystemService;
import org.loose.fis.sre.services.MaterialService;
import org.loose.fis.sre.services.OrderService;
import org.loose.fis.sre.services.ProductTypeService;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
class ViewOrdersClientControllerTest {

    private ViewOrdersClientController controller;
    private final static String TYPE = "product_type";
    private final static int PRICE = 0;
    private final static String MATERIAL = "material";
    private final static String CLIENT = "client";
    private final static String MESSAGE = "message";
    private final static String WAIT_STATE = "Asteptare";
   

    @BeforeEach
    void setUp(){
        FileSystemService.APPLICATION_FOLDER = ".test-jewellery-databases";
    }

    @AfterEach
    void tearDown() {
        OrderService.close();
        MaterialService.close();
        ProductTypeService.close();
    }

    @Start
    void start(Stage primaryStage) throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-jewellery-databases";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        ProductTypeService.initDatabase();
        ProductTypeService.addType(TYPE, PRICE);
        MaterialService.initDatabase();
        MaterialService.addMaterial(MATERIAL, PRICE);
        OrderService.initDatabase();
        User.setCurrentUser(CLIENT);
        OrderService.addOrder(CLIENT, ProductTypeService.getAllProductTypes().get(0), MaterialService.getAllMaterials().get(0), MESSAGE);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/viewOrdersClient.fxml"));
        Parent root = fxmlLoader.load();
        controller = fxmlLoader.getController();
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setTitle("Login-test");
        primaryStage.show();
    }

    @Test
     @DisplayName("Check if your order is displayed correctly")
     void testCheckOrderCorrect()
    {
        Order order=controller.getAllOrdersFromTable().get(0);

        assertThat(order.getClient()).isEqualTo(CLIENT);
        assertThat(order.getMessage()).isEqualTo(MESSAGE);
        assertThat(order.getPrice()).isEqualTo(PRICE+PRICE);
        assertThat(order.getState()).isEqualTo(WAIT_STATE);
        assertThat(order.getMaterial().getName()).isEqualTo(MATERIAL);
        assertThat(order.getMaterial().getPrice()).isEqualTo(PRICE);
        assertThat(order.getProductType().getType()).isEqualTo(TYPE);
        assertThat(order.getProductType().getPrice()).isEqualTo(PRICE);


    }

    @Test
    @DisplayName("Check the back button")
    void testCheckBackButton(FxRobot robot)
    {
        robot.clickOn("#backButton");
        robot.clickOn("#startUserFile");
    }
    

}