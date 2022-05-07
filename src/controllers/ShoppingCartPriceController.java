package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ShoppingCartPriceController extends AnchorPane implements ShoppingCartListener, Initializable {

    @FXML
    Label priceTagLabel;

    public ShoppingCartPriceController(){
        IMatDataHandler.getInstance().getShoppingCart().addShoppingCartListener(this);
    }

    private void changePriceLabel(){
        priceTagLabel.setText(String.valueOf(IMatDataHandler.getInstance().getShoppingCart().getTotal()));
    }

    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        this.changePriceLabel();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        priceTagLabel.setText("0");
    }
}