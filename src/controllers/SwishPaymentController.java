package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SwishPaymentController extends AnchorPane {
    public SwishPaymentController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/swishBox.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}
