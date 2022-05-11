package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class DeliveryController extends AnchorPane {

    @FXML
    FlowPane days;
    Calendar date = Calendar.getInstance();

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
            days.getChildren().add(new DeliveryDateController((date.get(Calendar.DAY_OF_MONTH) + i), (date.get(Calendar.DAY_OF_WEEK) + i) % 7));
       }

        days.getChildren().get(0).getStyleClass().add("selected");
        days.getChildren().get(0).getStyleClass().add("selectedLabel");

    }


}
