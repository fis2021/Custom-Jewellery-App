package org.loose.fis.sre.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.loose.fis.sre.model.Order;
import org.loose.fis.sre.services.OrderService;

public class ViewOrdersManagerController {

    @FXML
    private Button backButton;
    @FXML
    private Text orderAcceptMessage;

    @FXML
    private TableView<Order> orderTable;
    @FXML
    private TableColumn<Order, String> orderUser;
    @FXML
    private TableColumn<Order, String> productTypeOrder;
    @FXML
    private TableColumn<Order, String> materialOrder;
    @FXML
    private TableColumn<Order, String> orderMessage;
    @FXML
    private TableColumn<Order, Integer> orderPrice;
    @FXML
    private TableColumn<Order, String> orderState;

    public void initialize() {
        orderUser.setCellValueFactory(new PropertyValueFactory<>("client"));
        productTypeOrder.setCellValueFactory(new PropertyValueFactory<>("productType"));
        materialOrder.setCellValueFactory(new PropertyValueFactory<>("material"));
        orderMessage.setCellValueFactory(new PropertyValueFactory<>("message"));
        orderPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        orderState.setCellValueFactory(new PropertyValueFactory<>("state"));

        orderTable.setItems(orders);

        update();
    }

    ObservableList<Order> orders = FXCollections.observableArrayList(OrderService.orders());

    public void handleAcceptButtonAction() {
        int counter = 0;
        ObservableList<Order> typesSelected;
        typesSelected = orderTable.getSelectionModel().getSelectedItems();
        for(Order order : typesSelected) {
            if(order.getState().equals("Asteptare")) {
                OrderService.acceptOrder(order);
                counter++;
            }
        }

        if(counter == 0)
            orderAcceptMessage.setText("Nu se mai poate modifica aceasta comanda");
        else {
            orderAcceptMessage.setText("");
            update();
        }
    }

    public void handleRejectButtonAction() {
        int counter = 0;
        ObservableList<Order> typesSelected;
        typesSelected = orderTable.getSelectionModel().getSelectedItems();
        for(Order order : typesSelected) {
            if(order.getState().equals("Asteptare")) {
                OrderService.rejectOrder(order);
                counter++;
            }
        }

        if(counter == 0)
            orderAcceptMessage.setText("Nu se mai poate modifica aceasta comanda");
        else {
            orderAcceptMessage.setText("");
            update();
        }
    }

    public void handleBackButtonAction() throws Exception {
        Stage window = (Stage) backButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/startManager.fxml"));
        window.setScene(new Scene(root, 800, 600));
    }

    private void update(){
        orderState.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                setText(item);

                TableRow<Order> currentRow = getTableRow();

                if (!isEmpty()) {
                    if (item.equals("Asteptare"))
                        currentRow.setStyle("-fx-background-color: yellow");
                    else if (item.equals("Acceptat"))
                        currentRow.setStyle("-fx-background-color: #49941b");
                    else
                        currentRow.setStyle("-fx-background-color: red");
                }
            }
        });
    }
}