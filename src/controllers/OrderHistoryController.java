package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;

import java.net.URL;
import java.util.ResourceBundle;

public class OrderHistoryController extends AnchorPane implements Initializable {


    @FXML VBox orderHistoryVBox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (Order order: IMatDataHandler.getInstance().getOrders()) {
            orderHistoryVBox.getChildren().add(new OrderHistoryEntryController(order));
        }
    }
}
