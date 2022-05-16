package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.io.IOException;

public class PaymentController extends AnchorPane {

    @FXML
    AnchorPane cardCredentials;

    @FXML
    TextField swish;

    @FXML
    AnchorPane cardPayment;

    @FXML
    AnchorPane swishPayment;

    @FXML
    AnchorPane cardRadioButton;

    @FXML
    AnchorPane swishRadioButton;

    @FXML
    Label paymentCost;


    Boolean swishSelected = false;
    Boolean cardSelected = true;

    public PaymentController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/payment.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (
                IOException exception) {
            throw new RuntimeException(exception);
        }

        swishPayment.getChildren().remove(swish);

        cardPayment.setOnMouseClicked(mouseEvent -> cardClicked());
        swishPayment.setOnMouseClicked(mouseEvent -> swishClicked());

        paymentCost.setText("Totalkostnad: " + (String.format("%.2f", IMatDataHandler.getInstance().getShoppingCart().getTotal() + 25)) + " kr");
    }

    private void swishClicked() {
        if (!swishSelected){
            cardPayment.getChildren().remove(cardCredentials);
            swishPayment.getChildren().add(swish);
            swishRadioButton.getStyleClass().add("selectedRadioButton");
            cardRadioButton.getStyleClass().remove("selectedRadioButton");
            swishSelected = true;
            cardSelected = false;
        }

    }

    private void cardClicked() {
        if (!cardSelected){
            swishPayment.getChildren().remove(swish);
            cardPayment.getChildren().add(cardCredentials);
            cardRadioButton.getStyleClass().add("selectedRadioButton");
            swishRadioButton.getStyleClass().remove("selectedRadioButton");
            swishSelected = false;
            cardSelected = true;
        }
    }

}
