package org.loose.fis.sre.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loose.fis.sre.services.*;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
class StartUserControllerTest {

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-jewellery-databases";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        ProductTypeService.initDatabase();
        MaterialService.initDatabase();
        OrderService.initDatabase();
    }

    @Start
    void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/startUser.fxml"));
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setTitle("StartUser-test");
        primaryStage.show();
    }

    @Test
    @DisplayName("LogOut button correct functionality")
    void testLogOutButton(FxRobot robot) {
        robot.clickOn("#logOutButton");
        robot.clickOn("#loginFile");
    }

    @Test
    @DisplayName("Order button correct functionality")
    void testOrderButton(FxRobot robot) {
        robot.clickOn("#orderButton");
        robot.clickOn("#makeOrderFile");
    }

    @Test
    @DisplayName("List products button correct functionality")
    void testListProductsButton(FxRobot robot) {
        robot.clickOn("#listProductsButton");
        robot.clickOn("#viewProductsClientFile");
    }

    @Test
    @DisplayName("List orders button correct functionality")
    void testListOrdersButton(FxRobot robot) {
        robot.clickOn("#listOrdersButton");
        robot.clickOn("#ordersClientFile");
    }
}