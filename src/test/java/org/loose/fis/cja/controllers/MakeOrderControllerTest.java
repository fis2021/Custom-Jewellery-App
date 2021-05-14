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
import org.loose.fis.cja.model.Order;
import org.loose.fis.cja.model.User;
import org.loose.fis.cja.services.*;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.testfx.assertions.api.Assertions.assertThat;


@ExtendWith(ApplicationExtension.class)
class MakeOrderControllerTest {

    private static final String CURRENT_USER = "user";
    private static final String TYPE = "type";
    private static final String MATERIAL = "material";
    private static final int PRICE = 0;
    private static final String MESSAGE = "message";
    private static final String DEFAULT_STATE = "Asteptare";

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
        MaterialService.initDatabase();
        OrderService.initDatabase();
        ProductTypeService.addType(TYPE, PRICE);
        MaterialService.addMaterial(MATERIAL, PRICE);
        User.setCurrentUser(CURRENT_USER);
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/makeOrder.fxml"));
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setTitle("Login-test");
        primaryStage.show();
    }

    @Test
    @DisplayName("No productType selected")
    void testNoProductTypeSelected(FxRobot robot) {
        robot.clickOn("#orderButton");
        assertThat(robot.lookup("#orderMessage").queryText()).hasText("Nu este selectat niciun produs");
    }

    @Test
    @DisplayName("No material selected")
    void testNoMaterialSelected(FxRobot robot) {
        robot.clickOn("#type");
        robot.clickOn(TYPE);
        robot.clickOn("#orderButton");
        assertThat(robot.lookup("#orderMessage").queryText()).hasText("Nu este selectat niciun material");
    }

    @Test
    @DisplayName("Order with message added successfully")
    void testAddOrderWithMessageSuccessfully(FxRobot robot){

        robot.clickOn("#type");
        robot.clickOn(TYPE);
        robot.clickOn("#material");
        robot.clickOn(MATERIAL);
        robot.clickOn("#message");
        robot.write(MESSAGE);
        robot.clickOn("#orderButton");

        assertThat(OrderService.getAllOrders().size()).isEqualTo(1);
        Order order = OrderService.getAllOrders().get(0);
        assertThat(order.getProductType().getType()).isEqualTo(TYPE);
        assertThat(order.getProductType().getPrice()).isEqualTo(PRICE);
        assertThat(order.getMaterial().getName()).isEqualTo(MATERIAL);
        assertThat(order.getMaterial().getPrice()).isEqualTo(PRICE);
        assertThat(order.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(order.getMessage()).isEqualTo(MESSAGE);
        robot.clickOn("#startUserFile");
    }

    @Test
    @DisplayName("Order without message added successfully")
    void testAddOrderWithoutMessageSuccessfully(FxRobot robot){

        robot.clickOn("#type");
        robot.clickOn(TYPE);
        robot.clickOn("#material");
        robot.clickOn(MATERIAL);
        robot.clickOn("#orderButton");

        assertThat(OrderService.getAllOrders().size()).isEqualTo(1);
        Order order = OrderService.getAllOrders().get(0);
        assertThat(order.getProductType().getType()).isEqualTo(TYPE);
        assertThat(order.getProductType().getPrice()).isEqualTo(PRICE);
        assertThat(order.getMaterial().getName()).isEqualTo(MATERIAL);
        assertThat(order.getMaterial().getPrice()).isEqualTo(PRICE);
        assertThat(order.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(order.getMessage()).isEqualTo("");
        robot.clickOn("#startUserFile");
    }

    @Test
    @DisplayName("Cancel button testing")
    void testCancelButton(FxRobot robot) {
        robot.clickOn("#cancelButton");
        robot.clickOn("#startUserFile");
    }
}