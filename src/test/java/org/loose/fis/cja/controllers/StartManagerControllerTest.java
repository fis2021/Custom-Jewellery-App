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
import org.loose.fis.cja.services.*;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
class StartManagerControllerTest {

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-jewellery-databases";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        ProductTypeService.initDatabase();
        MaterialService.initDatabase();
        OrderService.initDatabase();
    }

    @AfterEach
    void tearDown() {
        OrderService.close();
        MaterialService.close();
        ProductTypeService.close();
    }

    @Start
    void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/startManager.fxml"));
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setTitle("StartManager-test");
        primaryStage.show();
    }

    @Test
    @DisplayName("LogOut button correct functionality")
    void testLogOutButton(FxRobot robot) {
        robot.clickOn("#logOutButton");
        robot.clickOn("#loginFile");
    }

    @Test
    @DisplayName("View products button correct functionality")
    void testViewProductsButton(FxRobot robot) {
        robot.clickOn("#viewProductsButton");
        robot.clickOn("#viewProductsManagerFile");
    }

    @Test
    @DisplayName("List orders button correct functionality")
    void testListOrdersButton(FxRobot robot) {
        robot.clickOn("#viewOrdersButton");
        robot.clickOn("#viewOrdersFile");
    }
}