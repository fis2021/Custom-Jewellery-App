package org.loose.fis.sre.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.loose.fis.sre.model.Order;
import org.loose.fis.sre.model.User;
import org.loose.fis.sre.services.OrderService;

public class ViewOrdersClientController {

    @FXML
    private TableView<Order> orderTable;
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
        productTypeOrder.setCellValueFactory(new PropertyValueFactory<>("productType"));
        materialOrder.setCellValueFactory(new PropertyValueFactory<>("material"));
        orderMessage.setCellValueFactory(new PropertyValueFactory<>("message"));
        orderPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        orderState.setCellValueFactory(new PropertyValueFactory<>("state"));

        orderTable.setItems(orders);

        orderState.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                setText(item);

                TableRow<Order> currentRow = getTableRow();

                if(!isEmpty()){
                    if(item.equals("Asteptare"))
                        currentRow.setStyle("-fx-background-color: yellow");
                    else if(item.equals("Acceptat"))
                        currentRow.setStyle("-fx-background-color: #49941b");
                    else
                        currentRow.setStyle("-fx-background-color: red");
                }
            }
        });
    }

    ObservableList<Order> orders = FXCollections.observableArrayList(OrderService.getAllOrders(User.getCurrentUser()));

    public void handleBackButtonAction() throws Exception{
        Stage window = (Stage) orderTable.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/startUser.fxml"));
        window.setScene(new Scene(root, 800,600));
    }
}