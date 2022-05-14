package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class PaymentController extends AnchorPane {

    @FXML
    VBox cardCredentials;

    CardPaymentController cardPayment = new CardPaymentController();

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

        cardCredentials.getChildren().add(cardPayment);

    }

}
