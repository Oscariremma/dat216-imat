package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DeliveryController extends AnchorPane {

    @FXML
    FlowPane dayFlowPane;

    @FXML
    FlowPane timeFlowPane;

    @FXML
    FlowPane deliveryFlowPane;

    Calendar date = Calendar.getInstance();

    public static List<DeliveryDateController> deliveryDates = new ArrayList<>();
    public static List<DeliveryTimeController> deliveryTimes = new ArrayList<>();
    public static List<DeliveryWayController> deliveryWays = new ArrayList<>();

    public DeliveryController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/delivery.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        for (int i = 0; i < 8; i++){
            DeliveryDateController item = new DeliveryDateController((date.get(Calendar.DAY_OF_MONTH) + i + 1), (date.get(Calendar.DAY_OF_WEEK) + i + 1) % 7);
            dayFlowPane.getChildren().add(item);
            deliveryDates.add(item);
       }

        for (int i = 0; i < 8; i++){
            DeliveryTimeController item = new DeliveryTimeController(i);
            timeFlowPane.getChildren().add(item);
            deliveryTimes.add(item);
        }

        DeliveryWayController toHome = new DeliveryWayController("Hem", "./img/local-shipping.png");
        deliveryFlowPane.getChildren().add(toHome);
        deliveryWays.add(toHome);

        DeliveryWayController toShop = new DeliveryWayController("Butik", "./img/store-icon.png" );
        deliveryFlowPane.getChildren().add(toShop);
        deliveryWays.add(toShop);


        // Pre-selects the first options, in each flowpane.
        deliveryDates.get(0).Select();
        deliveryTimes.get(0).Select();
        deliveryWays.get(0).Select();

    }

    public static void deselectAllDates() {
        for (DeliveryDateController date: deliveryDates) {
            date.Deselect();
        }
    }

    public static void deselectAllTimes() {
        for (DeliveryTimeController time: deliveryTimes) {
            time.Deselect();
        }
    }

    public static void deselectAllDelivery() {
        for (DeliveryWayController way: deliveryWays) {
            way.Deselect();
        }
    }
}
