package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;


public class DeliveryDateController extends AnchorPane{

    @FXML
    Label day;

    @FXML
    Label date;

    String[] weekDays = new String[]{"Lör", "Sön", "Mån", "Tis", "Ons", "Tors", "Fre"};

    public DeliveryDateController(int dateNumber, int dayName){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/deliveryChoices.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        day.setText(String.valueOf(weekDays[dayName]));
        date.setText(String.valueOf(dateNumber));
    }


}
