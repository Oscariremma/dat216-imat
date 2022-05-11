package controllers;

import interfaces.Selectable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DeliveryWayController extends AnchorPane implements Selectable {

    @FXML
    Label deliveryWayLabel;

    @FXML
    ImageView deliveryWayImage;

    @FXML
    AnchorPane deliveryItem;

    String imagePath;

    public DeliveryWayController(String wayToDeliver, String image){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/chooseDeliveryWay.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        imagePath = image.replace(".png","");
        deliveryWayLabel.setText(wayToDeliver);
        deliveryWayImage.setImage(new Image(image));

        setOnMouseClicked(mouseEvent -> clicked());
    }

    private void clicked(){
        this.Select();
    }

    @Override
    public void Select() {
        DeliveryController.deselectAllDelivery();
        selectItem();
    }

    public void selectItem() {
        deliveryWayImage.setImage(new Image(imagePath + "-inverted.png"));
        deliveryWayLabel.getStyleClass().add("selectedLabel");
        deliveryItem.getStyleClass().add("selected");
    }

    @Override
    public void Deselect() {
        deliveryWayImage.setImage(new Image(imagePath + ".png"));
        deliveryWayLabel.getStyleClass().remove("selectedLabel");
        deliveryItem.getStyleClass().remove("selected");
    }
}
