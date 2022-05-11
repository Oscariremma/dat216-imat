package controllers;

import interfaces.Selectable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;


public class DeliveryDateController extends AnchorPane implements Selectable {

    @FXML
    AnchorPane item;

    @FXML
    Label day;

    @FXML
    Label date;

    String[] daysOfWeek = new String[]{"Lör", "Sön", "Mån", "Tis", "Ons", "Tors", "Fre"};

    public DeliveryDateController(int dateNumber, int dayName){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/dateItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        day.setText(String.valueOf(daysOfWeek[dayName]));
        date.setText(String.valueOf(dateNumber));

        setOnMouseClicked(mouseEvent -> clicked());
    }

    private void clicked() {
        this.Select();
    }

    @Override
    public void Select() {
        DeliveryController.deselectAllDates();
        day.getStyleClass().add("selectedLabel");
        date.getStyleClass().add("selectedLabel");
        item.getStyleClass().add("selected");
    }

    @Override
    public void Deselect() {
        day.getStyleClass().remove("selectedLabel");
        date.getStyleClass().remove("selectedLabel");
        item.getStyleClass().remove("selected");
    }

}
