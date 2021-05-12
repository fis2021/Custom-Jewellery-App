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
import org.loose.fis.sre.model.Order;
import org.loose.fis.sre.services.FileSystemService;
import org.loose.fis.sre.services.MaterialService;
import org.loose.fis.sre.services.OrderService;
import org.loose.fis.sre.services.ProductTypeService;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
class ViewOrdersManagerControllerTest {

    private ViewOrdersManagerController controller;
    private final static String TYPE = "product_type";
    private final static int PRICE = 0;
    private final static String MATERIAL = "material";
    private final static String CLIENT = "client";
    private final static String MESSAGE = "message";
    private final static String WAIT_STATE = "Asteptare";
    private final static String ACCEPT_STATE = "Acceptat";
    private final static String REJECT_STATE = "Respins";

    @BeforeEach
    void setUp(){
        FileSystemService.APPLICATION_FOLDER = ".test-jewellery-databases";
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
        OrderService.addOrder(CLIENT, ProductTypeService.getAllProductTypes().get(0), MaterialService.getAllMaterials().get(0), MESSAGE);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/viewOrdersManager.fxml"));
        Parent root = fxmlLoader.load();
        controller = fxmlLoader.getController();
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setTitle("Login-test");
        primaryStage.show();
    }

    @Test
    @DisplayName("Testing correct orders displayed")
    void testCorrectOrdersDisplayed() {
        assertThat(controller.getOrdersFromTable().size()).isEqualTo(1);

        Order order = controller.getOrdersFromTable().get(0);
        assertThat(order.getProductType().getType()).isEqualTo(TYPE);
        assertThat(order.getProductType().getPrice()).isEqualTo(PRICE);
        assertThat(order.getMaterial().getName()).isEqualTo(MATERIAL);
        assertThat(order.getMaterial().getPrice()).isEqualTo(PRICE);
        assertThat(order.getClient()).isEqualTo(CLIENT);
        assertThat(order.getPrice()).isEqualTo(PRICE + PRICE);
        assertThat(order.getMessage()).isEqualTo(MESSAGE);
        assertThat(order.getState()).isEqualTo(WAIT_STATE);
    }

    @Test
    @DisplayName("Test accept button functionality")
    void testAcceptButton(FxRobot robot) {
        assertThat(controller.getOrdersFromTable().get(0).getState()).isEqualTo(WAIT_STATE);

        robot.clickOn(CLIENT);
        robot.clickOn("#acceptButton");

        Order order = OrderService.getAllOrders().get(0);
        assertThat(order.getProductType().getType()).isEqualTo(TYPE);
        assertThat(order.getProductType().getPrice()).isEqualTo(PRICE);
        assertThat(order.getMaterial().getName()).isEqualTo(MATERIAL);
        assertThat(order.getMaterial().getPrice()).isEqualTo(PRICE);
        assertThat(order.getClient()).isEqualTo(CLIENT);
        assertThat(order.getPrice()).isEqualTo(PRICE + PRICE);
        assertThat(order.getMessage()).isEqualTo(MESSAGE);
        assertThat(order.getState()).isEqualTo(ACCEPT_STATE);

        assertThat(controller.getOrdersFromTable().get(0).getState()).isEqualTo(ACCEPT_STATE);
    }

    @Test
    @DisplayName("Test reject button functionality")
    void testRejectButton(FxRobot robot) {
        assertThat(controller.getOrdersFromTable().get(0).getState()).isEqualTo(WAIT_STATE);

        robot.clickOn(CLIENT);
        robot.clickOn("#rejectButton");

        Order order = OrderService.getAllOrders().get(0);
        assertThat(order.getProductType().getType()).isEqualTo(TYPE);
        assertThat(order.getProductType().getPrice()).isEqualTo(PRICE);
        assertThat(order.getMaterial().getName()).isEqualTo(MATERIAL);
        assertThat(order.getMaterial().getPrice()).isEqualTo(PRICE);
        assertThat(order.getClient()).isEqualTo(CLIENT);
        assertThat(order.getPrice()).isEqualTo(PRICE + PRICE);
        assertThat(order.getMessage()).isEqualTo(MESSAGE);
        assertThat(order.getState()).isEqualTo(REJECT_STATE);

        assertThat(controller.getOrdersFromTable().get(0).getState()).isEqualTo(REJECT_STATE);
    }

    @Test
    @DisplayName("Testing already changed state for order")
    void testAlreadyChangedStateOrder(FxRobot robot) {
        robot.clickOn(CLIENT);
        robot.clickOn("#acceptButton");

        String initialState = controller.getOrdersFromTable().get(0).getState();
        robot.clickOn("#acceptButton");
        String lastState = controller.getOrdersFromTable().get(0).getState();

        assertThat(robot.lookup("#orderAcceptMessage").queryText().getText()).isEqualTo("Nu se mai poate modifica aceasta comanda");
        assertThat(initialState).isEqualTo(lastState);
    }

    @Test
    @DisplayName("Testing back button")
    void testBackButton(FxRobot robot) {
        robot.clickOn("#backButton");
        robot.clickOn("#startManagerFile");
    }

}