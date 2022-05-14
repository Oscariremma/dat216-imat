package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CardPaymentController extends AnchorPane {
    public CardPaymentController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/creditCard.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (
                IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
