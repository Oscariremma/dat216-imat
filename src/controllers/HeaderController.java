package controllers;

import interfaces.HeaderNavigationListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class HeaderController extends AnchorPane implements ShoppingCartListener, Initializable {

    @FXML Label priceTagLabel;
    @FXML Label amountTagLabel;
    @FXML Label homeLabel;
    @FXML Label favoriteLabel;
    @FXML Label orderHistoryLabel;
    @FXML Label logoLabel;

    @FXML TextField searchTextField;

    @FXML Button searchButton;

    private static List<HeaderNavigationListener> navigationListeners = new ArrayList<>();

    public HeaderController(){
        IMatDataHandler.getInstance().getShoppingCart().addShoppingCartListener(this);
    }

    private void changePriceLabel(){
        priceTagLabel.setText(String.format("%.2f", IMatDataHandler.getInstance().getShoppingCart().getTotal()) + " kr");
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
        int amount = countAmountOfItems();
        return amount + (amount == 1 ? " vara": " varor");
    }

    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        this.changePriceLabel();
        this.changeAmountLabel();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changePriceLabel();
        changeAmountLabel();

        homeLabel.setOnMouseClicked(mouseEvent -> triggerGoToHome());
        favoriteLabel.setOnMouseClicked(mouseEvent -> triggerGoToFavorites());
        orderHistoryLabel.setOnMouseClicked(mouseEvent -> triggerGoToOrderHistory());
        logoLabel.setOnMouseClicked(mouseEvent -> triggerGoToHome());

        searchTextField.setOnKeyReleased(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) triggerSearch();
        });

         searchButton.setOnMouseClicked(mouseEvent -> triggerSearch());


    }

    public static void registerNavigationListener(HeaderNavigationListener listener){
        navigationListeners.add(listener);
    }

    private void triggerGoToHome(){
        for (HeaderNavigationListener listener : navigationListeners) {
            try {
                listener.goToHome();
            }catch (Exception e){
                System.out.println(e);
            }
        }
    }

    private void triggerGoToFavorites(){
        for (HeaderNavigationListener listener : navigationListeners) {
            try {
                listener.goToFavorites();
            }catch (Exception e){
                System.out.println(e);
            }
        }
    }

    private void triggerGoToOrderHistory(){
        for (HeaderNavigationListener listener : navigationListeners) {
            try {
                listener.goToOrderHistory();
            }catch (Exception e){
                System.out.println(e);
            }
        }
    }

    private void triggerGoToCart(){
        for (HeaderNavigationListener listener : navigationListeners) {
            try {
                listener.goToCart();
            }catch (Exception e){
                System.out.println(e);
            }
        }
    }

    private void triggerSearch(){
        if (searchTextField.getText().length() > 0){
            for (HeaderNavigationListener listener : navigationListeners) {
                try {
                    listener.goToSearchResult(searchTextField.getText());
                }catch (Exception e){
                    System.out.println(e);
                }
            }
        }else {
            //todo Add error when search quarry is empty
        }


    }

}