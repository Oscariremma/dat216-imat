package controllers;

import interfaces.Selectable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;


public class DeliveryDateController extends AnchorPane implements Selectable {

    @FXML
    AnchorPane item;

    @FXML
    Label day;

    @FXML
    Label date;

    private LocalDate completeDate;

    HashMap<DayOfWeek, String> dateMap = new HashMap<DayOfWeek, String>() {{
        put(DayOfWeek.valueOf("MONDAY"), "Mån");
        put(DayOfWeek.valueOf("TUESDAY"), "Tis");
        put(DayOfWeek.valueOf("WEDNESDAY"), "Ons");
        put(DayOfWeek.valueOf("THURSDAY"), "Tors");
        put(DayOfWeek.valueOf("FRIDAY"), "Fre");
        put(DayOfWeek.valueOf("SATURDAY"), "Lör");
        put(DayOfWeek.valueOf("SUNDAY"), "Sön");
    }};

    public DeliveryDateController(int dateNumber, DayOfWeek dayName, LocalDate completeDate){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/dateItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.completeDate = completeDate;

        day.setText(String.valueOf(dateMap.get(dayName)));
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
        DeliveryController.setDeliveryDate(completeDate);
    }

    @Override
    public void Deselect() {
        day.getStyleClass().remove("selectedLabel");
        date.getStyleClass().remove("selectedLabel");
        item.getStyleClass().remove("selected");
    }

}
