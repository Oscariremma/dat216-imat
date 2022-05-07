package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.*;

import java.net.URL;
import java.util.ResourceBundle;


public class HeaderController extends AnchorPane implements ShoppingCartListener, Initializable {

    @FXML
    Label priceTagLabel;

    @FXML
    Label amountTagLabel;

    public HeaderController(){
        IMatDataHandler.getInstance().getShoppingCart().addShoppingCartListener(this);
    }

    private void changePriceLabel(){
        priceTagLabel.setText(String.valueOf(IMatDataHandler.getInstance().getShoppingCart().getTotal()));
    }

    private void changeAmountLabel(){
        amountTagLabel.setText(String.valueOf(IMatDataHandler.getInstance().getShoppingCart().getItems().size()));
    }

    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        this.changePriceLabel();
        this.changeAmountLabel();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        amountTagLabel.setText(String.valueOf(IMatDataHandler.getInstance().getShoppingCart().getItems().size()));
        priceTagLabel.setText(String.valueOf(IMatDataHandler.getInstance().getShoppingCart().getTotal()));
    }
}