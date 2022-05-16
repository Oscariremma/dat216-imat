package controllers;

import interfaces.NavigationRequestListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import structs.NavigationRequest;
import structs.NavigationType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfirmationController extends AnchorPane {

    @FXML
    Label costLabel;

    @FXML
    Label deliveryLabel;

    @FXML
    Button homeButton;

    private static List<NavigationRequestListener> navigationRequestListeners = new ArrayList<>();

    public ConfirmationController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/confirmation.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (
                IOException exception) {
            throw new RuntimeException(exception);
        }

        refresh();
        homeButton.setOnMouseClicked(mouseEvent -> triggerNavigationRequest(new NavigationRequest(NavigationType.Home,null)));
    }

    public void refresh(){
        costLabel.setText("Totalkostnad: " + (String.format("%.2f", IMatDataHandler.getInstance().getShoppingCart().getTotal() + 25)) + " kr");
        deliveryLabel.setText(getDeliveryDateAndTime());
    }

    private String getDeliveryDateAndTime() {
        return "Levereras: " + DeliveryController.getDeliveryDate() + ", klockan " + DeliveryController.getDeliveryTime();
    }

    public static void registernavigationRequestListener(NavigationRequestListener listener){
        navigationRequestListeners.add(listener);
    }

    public void triggerNavigationRequest(NavigationRequest request){
        for (NavigationRequestListener listener : navigationRequestListeners) {
            try {
                listener.goToNavigationRequest(request);
            }catch (Exception e){
                System.out.println(e);
            }
        }
    }
}
