package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class OrderHistoryEntryController extends AnchorPane {

    @FXML VBox entryRowVBox;
    @FXML Label orderNameLabel;
    @FXML Label orderDateLabel;
    @FXML Label orderTotalLabel;

    public OrderHistoryEntryController(Order order){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/orderHistoryEntry.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        double total = 0;
        double itemCount = 0;

        for (ShoppingItem item:order.getItems() ) {
            entryRowVBox.getChildren().add(new OrderHistoryRow(item));
            total += item.getTotal();
            itemCount += item.getAmount();
        }

        orderNameLabel.setText("Order " + order.getOrderNumber());
        orderDateLabel.setText(new SimpleDateFormat("yyyy/mm/dd").format(order.getDate()));
        orderTotalLabel.setText(new DecimalFormat("#.##").format(total) + "kr - " +
                new DecimalFormat("#").format(itemCount) + ((itemCount > 1) ? "saker" : "sak"));

    }


}
