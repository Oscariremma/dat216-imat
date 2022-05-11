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
    FlowPane days;

    @FXML
    FlowPane times;

    Calendar date = Calendar.getInstance();

    public static List<DeliveryDateController> deliveryDates = new ArrayList<>();
    public static List<DeliveryTimeController> deliveryTimes = new ArrayList<>();

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
            days.getChildren().add(item);
            deliveryDates.add(item);
       }

        for (int i = 0; i < 8; i++){
            DeliveryTimeController item = new DeliveryTimeController(i);
            times.getChildren().add(item);
            deliveryTimes.add(item);
        }

        deliveryDates.get(0).selectItem();
        deliveryTimes.get(0).selectItem();

    }

    public static void deselectAllDates() {
        for (int i = 0; i < 8; i++){
            deliveryDates.get(i).Deselect();

        }
    }

    public static void deselectAllTimes() {
        for (int i = 0; i < 8; i++){
            deliveryTimes.get(i).Deselect();
        }
    }
}
