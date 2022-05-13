package controllers;

import interfaces.NavigationRequestListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import se.chalmers.cse.dat216.project.*;
import structs.NavigationRequest;
import structs.NavigationType;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ModalProductInfoController extends AnchorPane implements ShoppingCartListener{



    @FXML Label productPriceLabel;
    @FXML Label productNameLabel;
    @FXML Label productSubtextLabel;
    @FXML ImageView productImageView;
    @FXML ImageView heartImageView;
    @FXML Button addButton;
    @FXML Button incrementButton;
    @FXML Button decrementButton;
    @FXML Label amountLabel;
    @FXML Pane backgroundPane;
    @FXML Circle favoriteCircleButton;
    @FXML Circle closeCircleButton;
    @FXML AnchorPane modalAnchorPane;
    @FXML HBox backgroundHBox;

    private Product product;
    private static List<NavigationRequestListener> navigationRequestListeners = new ArrayList<>();

    private static Image filledHeartImage = new Image("/img/cards-heart.png");
    private static Image outlineHeartImage = new Image("/img/cards-heart-outline.png");

    public ModalProductInfoController(){

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/modalProductInfo.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }



        incrementButton.setOnMouseClicked((mouseEvent ->
                addProductToCart()));
        addButton.setOnMouseClicked((mouseEvent ->
                addProductToCart()));
        decrementButton.setOnMouseClicked((mouseEvent ->
                removeProductFromCart()));
        favoriteCircleButton.setOnMouseClicked((mouseEvent ->
                favoriteButtonClicked()));

        backgroundPane.setOnMouseClicked(mouseEvent -> {
                    triggerNavigationRequest(new NavigationRequest(NavigationType.CloseModal, null));
                    mouseEvent.consume();
                });

        backgroundHBox.setOnMouseClicked(mouseEvent -> {
            triggerNavigationRequest(new NavigationRequest(NavigationType.CloseModal, null));
            mouseEvent.consume();
        });


        closeCircleButton.setOnMouseClicked(mouseEvent -> {
            triggerNavigationRequest(new NavigationRequest(NavigationType.CloseModal, null));
            mouseEvent.consume();
        });

        modalAnchorPane.setOnMouseClicked(mouseEvent -> mouseEvent.consume());

        IMatDataHandler.getInstance().getShoppingCart().addShoppingCartListener(this);


    }

    public void setProduct(Product product){
        this.product = product;
        forceRefreshCartStatus();
        updateFavoriteButtonIcon();
        productNameLabel.setText(product.getName());
        productPriceLabel.setText(product.getPrice() + " " + product.getUnit());
        productImageView.setImage(IMatDataHandler.getInstance().getFXImage(product));
    }


    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        if (product == null) return;
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
        if (product == null) return;
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
        if (product == null) return;
        if (IMatDataHandler.getInstance().isFavorite(product)){
            IMatDataHandler.getInstance().removeFavorite(product);
        }else {
            IMatDataHandler.getInstance().addFavorite(product);
        }
        updateFavoriteButtonIcon();
    }

    private void updateFavoriteButtonIcon(){
        if (product == null) return;
        heartImageView.setImage(IMatDataHandler.getInstance().isFavorite(product)
                ? filledHeartImage : outlineHeartImage);
    }

    private void addProductToCart(){
        if (product == null) return;
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
        if (product == null) return;
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

}
