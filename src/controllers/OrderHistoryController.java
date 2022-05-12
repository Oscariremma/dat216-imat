package controllers;

import interfaces.BackNavigationListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class OrderHistoryController extends AnchorPane {

    private static List<BackNavigationListener> backNavigationListeners = new ArrayList<>();

    @FXML VBox orderHistoryVBox;
    @FXML VBox nothingHereVBox;
    @FXML Button backButton;

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

        backButton.setOnMouseClicked(mouseEvent -> triggerGoBack());

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

    public static void registerBackNavigationListener(BackNavigationListener listener){
        backNavigationListeners.add(listener);
    }

    private void triggerGoBack(){
        for (BackNavigationListener listener : backNavigationListeners) {
            try {
                listener.goBack();
            }catch (Exception e){
                System.out.println(e);
            }
        }
    }

}
