package controllers;

import interfaces.NavigationRequestSender;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.*;
import structs.NavigationRequest;
import structs.NavigationType;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

public class CartItemController extends AnchorPane implements ShoppingCartListener {

    @FXML Button incrementButton;
    @FXML Button decrementButton;
    @FXML Label amountLabel;
    @FXML ImageView productImageView;
    @FXML Label productNameLabel;
    @FXML Label priceLabel;
    @FXML Label productTotalLabel;

    private final Product product;


    public CartItemController(Product product, NavigationRequestSender navigationRequestSender){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/cartItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.product = product;

        productNameLabel.setText(product.getName());
        priceLabel.setText(product.getPrice() + product.getUnit());
        productImageView.setImage(IMatDataHandler.getInstance().getFXImage(product));

        forceRefreshCartStatus();

        incrementButton.setOnMouseClicked((mouseEvent ->{
            addProductToCart();
            mouseEvent.consume();
        }));
        decrementButton.setOnMouseClicked((mouseEvent ->{
            removeProductFromCart();
            mouseEvent.consume();
        }));

        setOnMouseClicked(mouseEvent -> {
            navigationRequestSender.triggerNavigationRequest(new NavigationRequest(NavigationType.ShowModalInfo, new Object[]{product}));
            mouseEvent.consume();
        });

        IMatDataHandler.getInstance().getShoppingCart().addShoppingCartListener(this);

    }


    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        if (cartEvent.getShoppingItem() == null){
            forceRefreshCartStatus();
            return;
        }

        //Ignore other items
        if (cartEvent.getShoppingItem().getProduct().getProductId() != product.getProductId()) return;
        updateAmountAndTotal(cartEvent.getShoppingItem());
    }

    private void forceRefreshCartStatus(){
        List<ShoppingItem> items = IMatDataHandler.getInstance().getShoppingCart().getItems();

        //Check if product exists in cart
        for (ShoppingItem item: items) {
            if (item.getProduct().getProductId() == product.getProductId()){
                updateAmountAndTotal(item);
                return;
            }
        }
    }

    private void updateAmountAndTotal(ShoppingItem item){
        amountLabel.setText(new DecimalFormat("#").format(item.getAmount()));
        productTotalLabel.setText("Totalt: " + new DecimalFormat("#.##").format(item.getTotal())+ " kr");
    }

    private void addProductToCart(){
        List<ShoppingItem> items = IMatDataHandler.getInstance().getShoppingCart().getItems();

        //Check if product exists in cart
        for (ShoppingItem item: items) {
            if (item.getProduct().getProductId() == product.getProductId()){
                //Increment the amount and fire an event
                item.setAmount(item.getAmount()+1);
                IMatDataHandler.getInstance().getShoppingCart().fireShoppingCartChanged(item, true);
                return;
            }
        }
        //Product does not exist in cart, add it
        IMatDataHandler.getInstance().getShoppingCart().addProduct(product);
    }

    private void removeProductFromCart(){
        List<ShoppingItem> items = IMatDataHandler.getInstance().getShoppingCart().getItems();

        //Check if product exists in cart
        for (ShoppingItem item: items) {
            if (item.getProduct().getProductId() == product.getProductId()){
                //More than 1, decrement and fire event
                if (item.getAmount() > 1){
                    item.setAmount(item.getAmount()-1);
                    IMatDataHandler.getInstance().getShoppingCart().fireShoppingCartChanged(item, false);
                }else {
                    //Otherwise, remove it from the cart
                    item.setAmount(0);
                    IMatDataHandler.getInstance().getShoppingCart().removeItem(item);
                }
                return;
            }
        }
        //Product does not exist in cart, do nothing
    }

}
