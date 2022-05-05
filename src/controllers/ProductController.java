package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.*;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ResourceBundle;

public class ProductController extends AnchorPane implements ShoppingCartListener {


    @FXML Label productPriceLabel;
    @FXML Label productNameLabel;
    @FXML Label productSubtextLabel;
    @FXML ImageView productImageView;
    @FXML ImageView heartImageView;
    @FXML Button addButton;
    @FXML Button incrementButton;
    @FXML Button decrementButton;
    @FXML Label amountLabel;

    private final Product product;

    private static Image filledHeartImage = new Image("/img/cards-heart.png");
    private static Image outlineHeartImage = new Image("/img/cards-heart-outline.png");


    public ProductController (Product product){


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/product.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.product = product;

        productNameLabel.setText(product.getName());
        productPriceLabel.setText(product.getPrice() + " " + product.getUnit());
        productImageView.setImage(IMatDataHandler.getInstance().getFXImage(product));
        updateFavoriteButtonIcon();
        forceRefreshCartStatus();

        incrementButton.setOnMouseClicked((mouseEvent ->
                 addProductToCart()));
        addButton.setOnMouseClicked((mouseEvent ->
                addProductToCart()));
        decrementButton.setOnMouseClicked((mouseEvent ->
                removeProductFromCart()));

        heartImageView.setOnMouseClicked((mouseEvent ->
                favoriteButtonClicked()));

        IMatDataHandler.getInstance().getShoppingCart().addShoppingCartListener(this);

    }


    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        //Ignore other items
        if (cartEvent.getShoppingItem().getProduct().getProductId() != product.getProductId()) return;

        if (cartEvent.getShoppingItem().getAmount() > 0){
            addButton.setVisible(false);
            amountLabel.setText(new DecimalFormat("#").format(cartEvent.getShoppingItem().getAmount()));
        }else {
            addButton.setVisible(true);
        }
    }

    private void forceRefreshCartStatus(){
        List<ShoppingItem> items = IMatDataHandler.getInstance().getShoppingCart().getItems();

        //Check if product exists in cart
        for (ShoppingItem item: items) {
            if (item.getProduct().getProductId() == product.getProductId()){
                if (item.getAmount() > 0){
                    addButton.setVisible(false);
                    amountLabel.setText(new DecimalFormat("#").format(item.getAmount()));
                    return;
                }
                break;
            }
        }
        addButton.setVisible(true);
    }

    private void favoriteButtonClicked(){
        if (IMatDataHandler.getInstance().isFavorite(product)){
            IMatDataHandler.getInstance().removeFavorite(product);
        }else {
            IMatDataHandler.getInstance().addFavorite(product);
        }
        updateFavoriteButtonIcon();
    }

    private void updateFavoriteButtonIcon(){
        heartImageView.setImage(IMatDataHandler.getInstance().isFavorite(product)
                ? filledHeartImage : outlineHeartImage);
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
