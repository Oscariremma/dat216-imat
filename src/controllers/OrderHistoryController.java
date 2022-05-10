package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OrderHistoryController extends AnchorPane {


    @FXML VBox orderHistoryVBox;
    @FXML VBox nothingHereVBox;

    public OrderHistoryController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/orderHistoryPage.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        refreshOrders();

    }

    public void refreshOrders(){
        if (IMatDataHandler.getInstance().getOrders().isEmpty()){
            if (!getChildren().contains(nothingHereVBox)) getChildren().add(nothingHereVBox);
            return;
        }else {
            getChildren().remove(nothingHereVBox);
        }

        for (Order order: IMatDataHandler.getInstance().getOrders()) {
            orderHistoryVBox.getChildren().add(new OrderHistoryEntryController(order));
        }
    }

}
