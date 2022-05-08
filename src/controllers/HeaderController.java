package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.*;

import java.net.URL;
import java.util.List;
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
        priceTagLabel.setText(String.format("%.2f", IMatDataHandler.getInstance().getShoppingCart().getTotal()));
    }

    private void changeAmountLabel(){
        amountTagLabel.setText(this.amountText());
    }

    private int countAmountOfItems() {
        int totalAmount = 0;

        List<ShoppingItem> items = IMatDataHandler.getInstance().getShoppingCart().getItems();
        for (ShoppingItem item: items) {
            totalAmount += item.getAmount();
        }

        return totalAmount;
    }

    private String amountText(){
        if(countAmountOfItems() == 1){
            return this.countAmountOfItems() + " vara";
        }
        else{
            return this.countAmountOfItems() + " varor";
        }
    }

    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        this.changePriceLabel();
        this.changeAmountLabel();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        amountTagLabel.setText(this.amountText());
        priceTagLabel.setText(String.format("%.2f", IMatDataHandler.getInstance().getShoppingCart().getTotal()));
    }
}