package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import se.chalmers.cse.dat216.project.*;

import java.io.IOException;


public class ShoppingCartPriceController implements ShoppingCartListener {

    @FXML
    Label priceTagLabel;

    public ShoppingCartPriceController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/header.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        priceTagLabel.setText("0");
        IMatDataHandler.getInstance().getShoppingCart().addShoppingCartListener(this);
    }

    private void changePriceLabel(){
        priceTagLabel.setText(String.valueOf(IMatDataHandler.getInstance().getShoppingCart().getTotal()));
    }

    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        this.changePriceLabel();
    }
}