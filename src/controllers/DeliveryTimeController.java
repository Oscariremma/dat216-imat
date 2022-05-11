package controllers;

import interfaces.Selectable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DeliveryTimeController extends AnchorPane implements Selectable {

    @FXML
    Label timeLabel;

    @FXML
    AnchorPane timeItem;


    String[] timespans = new String[]{"11:00 - 12:00", "12:00 - 13:00", "13:00 - 14:00", "14:00 - 15:00", "15:00 - 16:00", "16:00 - 17:00", "17:00 - 18:00", "18:00 - 19:00", "19:00 - 20:00"};

    public DeliveryTimeController(int timespan){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/timeItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        timeLabel.setText(timespans[timespan]);

        setOnMouseClicked(mouseEvent -> clicked());
    }

    private void clicked(){
        this.Select();
    }

    @Override
    public void Select() {
        DeliveryController.deselectAllTimes();
        selectItem();
    }

    public void selectItem() {
        timeLabel.getStyleClass().add("selectedLabel");
        timeItem.getStyleClass().add("selected");
    }

    @Override
    public void Deselect() {
        timeLabel.getStyleClass().remove("selectedLabel");
        timeItem.getStyleClass().remove("selected");
    }
}
