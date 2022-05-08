package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.text.DecimalFormat;

public class OrderHistoryRow extends AnchorPane {



    @FXML Label nameAndAmountLabel;
    @FXML Label priceLabel;

    private static final DecimalFormat noDecimals = new DecimalFormat("#");
    private static final DecimalFormat twoDecimals = new DecimalFormat("#.##");

    public OrderHistoryRow(ShoppingItem item){

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/orderHistoryRow.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        nameAndAmountLabel.setText(noDecimals.format(item.getAmount()) + "x " + item.getProduct().getName());
        priceLabel.setText(twoDecimals.format(item.getTotal()));

    }


}
