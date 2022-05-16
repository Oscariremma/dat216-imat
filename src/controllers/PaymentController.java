package controllers;

import interfaces.NavigationRequestListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.CartEvent;
import se.chalmers.cse.dat216.project.CreditCard;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingCartListener;
import structs.NavigationRequest;
import structs.NavigationType;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PaymentController extends AnchorPane implements ShoppingCartListener {

    @FXML
    AnchorPane cardCredentials;

    @FXML
    TextField swish;

    @FXML
    AnchorPane cardPayment;

    @FXML TextField cardNumberTextField;
    @FXML TextField cardExprTextField;
    @FXML TextField cardCVCTextField;

    @FXML
    AnchorPane swishPayment;

    @FXML
    AnchorPane cardRadioButton;

    @FXML
    AnchorPane swishRadioButton;

    @FXML
    Label paymentCost;

    @FXML AnchorPane backButtonAnchorPane;
    @FXML AnchorPane nextPaymentAnchorPane;


    Boolean cardSelected = true;

    private static List<NavigationRequestListener> navigationRequestListeners = new ArrayList<>();

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

        backButtonAnchorPane.setOnMouseClicked(mouseEvent -> triggerNavigationRequest(new NavigationRequest(NavigationType.Back, null)));
        nextPaymentAnchorPane.setOnMouseClicked(mouseEvent -> placeOrder());

        CreditCard creditCard = IMatDataHandler.getInstance().getCreditCard();

        cardNumberTextField.setText(creditCard.getCardNumber());
        cardCVCTextField.setText(Integer.toString(creditCard.getVerificationCode()));
        if (creditCard.getValidMonth() != 0){
            cardExprTextField.setText(new DecimalFormat("##").format(creditCard.getValidMonth()) + "/" + creditCard.getValidYear());
        }

        swish.setText(IMatDataHandler.getInstance().getCustomer().getMobilePhoneNumber());

        refreshPage();


    }

    public void refreshPage(){
        paymentCost.setText("Totalkostnad: " + (String.format("%.2f", IMatDataHandler.getInstance().getShoppingCart().getTotal() + 25)) + " kr");
    }

    private void swishClicked() {
        if (cardSelected){
            cardPayment.getChildren().remove(cardCredentials);
            swishPayment.getChildren().add(swish);
            swishRadioButton.getStyleClass().add("selectedRadioButton");
            cardRadioButton.getStyleClass().remove("selectedRadioButton");
            cardSelected = false;
        }

    }

    private void cardClicked() {
        if (!cardSelected){
            swishPayment.getChildren().remove(swish);
            cardPayment.getChildren().add(cardCredentials);
            cardRadioButton.getStyleClass().add("selectedRadioButton");
            swishRadioButton.getStyleClass().remove("selectedRadioButton");
            cardSelected = true;
        }
    }

    private void placeOrder(){
        //todo check valid input before placing?
        savePaymentInfo();
        IMatDataHandler.getInstance().placeOrder();
        triggerNavigationRequest(new NavigationRequest(NavigationType.CheckoutDone, null));
    }

    private void savePaymentInfo(){
        CreditCard card = IMatDataHandler.getInstance().getCreditCard();

        card.setCardNumber(cardNumberTextField.getText());
        try {
            card.setVerificationCode(Integer.parseInt(cardCVCTextField.getText()));
            String exprDate = cardExprTextField.getText();
            String[] monthAndYear = exprDate.split("/");
            card.setValidMonth(Integer.parseInt(monthAndYear[0]));
            card.setValidYear(Integer.parseInt(monthAndYear[1]));
        }catch (Exception e){
            //todo Handel invalid input?
        }
    }

    public static void registernavigationRequestListener(NavigationRequestListener listener){
        navigationRequestListeners.add(listener);
    }


    public void triggerNavigationRequest(NavigationRequest request){
        for (NavigationRequestListener listener : navigationRequestListeners) {
            try {
                listener.goToNavigationRequest(request);
            }catch (Exception e){
                System.out.println(e);
            }
        }
    }

    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        refreshPage();
    }
}
