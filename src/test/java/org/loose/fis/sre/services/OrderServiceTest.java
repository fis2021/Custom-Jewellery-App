package org.loose.fis.sre.services;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.loose.fis.sre.model.Material;
import org.loose.fis.sre.model.Order;
import org.loose.fis.sre.model.ProductType;

import static org.assertj.core.api.Assertions.assertThat;

class OrderServiceTest {

    private static final String CLIENT = "client";
    private static final ProductType PRODUCT_TYPE = new ProductType("type", 0);
    private static final Material MATERIAL = new Material("name", 0);
    private static final String MESSAGE = "message";

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-jewellery-databases";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        OrderService.initDatabase();
    }

    @Test
    @DisplayName("Database is initialized, and there are no orders")
    void testDatabaseIsInitializedAndNoOrderIsPersisted() {
        assertThat(OrderService.getAllOrders()).isNotNull();
        assertThat(OrderService.getAllOrders()).isEmpty();
    }

    @Test
    @DisplayName("Order is successfully persisted to Database")
    void testOrderIsAddedToDatabase() {
        OrderService.addOrder(CLIENT, PRODUCT_TYPE, MATERIAL, MESSAGE);
        assertThat(OrderService.getAllOrders()).isNotEmpty();
        assertThat(OrderService.getAllOrders()).size().isEqualTo(1);
        Order order = OrderService.getAllOrders().get(0);
        assertThat(order).isNotNull();
        assertThat(order.getClient()).isEqualTo(CLIENT);
        assertThat(order.getProductType()).isEqualTo(PRODUCT_TYPE);
        assertThat(order.getMaterial()).isEqualTo(MATERIAL);
        assertThat(order.getMessage()).isEqualTo(MESSAGE);
        assertThat(order.getPrice()).isEqualTo(PRODUCT_TYPE.getPrice() + MATERIAL.getPrice());
    }

    @Test
    @DisplayName("Order is accepted")
    void testAcceptedOrder() {

        OrderService.addOrder(CLIENT, PRODUCT_TYPE, MATERIAL, MESSAGE);
        assertThat(OrderService.getAllOrders().get(0).getState()).isEqualTo("Asteptare");

        Order order = new Order(0, CLIENT, PRODUCT_TYPE, MATERIAL, MESSAGE);
        OrderService.acceptOrder(order);
        assertThat(OrderService.getAllOrders().get(0).getState()).isEqualTo("Acceptat");
    }

    @Test
    @DisplayName("Order is rejected")
    void testDeclinedOrder() {

        OrderService.addOrder(CLIENT, PRODUCT_TYPE, MATERIAL, MESSAGE);
        assertThat(OrderService.getAllOrders().get(0).getState()).isEqualTo("Asteptare");

        Order order = new Order(0, CLIENT, PRODUCT_TYPE, MATERIAL, MESSAGE);
        OrderService.rejectOrder(order);
        assertThat(OrderService.getAllOrders().get(0).getState()).isEqualTo("Respins");
    }

}