package controllers;

import helpers.BreadcrumbGenerator;
import interfaces.NavigationRequestListener;
import interfaces.NavigationRequestSender;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.CartEvent;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingCartListener;
import se.chalmers.cse.dat216.project.ShoppingItem;
import structs.BreadcrumbItem;
import structs.NavigationRequest;
import structs.NavigationType;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CartController extends AnchorPane implements ShoppingCartListener, NavigationRequestSender {

    @FXML HBox breadcrumbsHBox;
    @FXML Button backButton;
    @FXML Label cartTotalLabel;
    @FXML AnchorPane nextButtonAnchorPane;
    @FXML VBox cartVBox;

    private static List<NavigationRequestListener> navigationRequestListeners = new ArrayList<>();

    public CartController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/cart.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        BreadcrumbGenerator.generateBreadcrumbs(Arrays.asList(new BreadcrumbItem("Hem",
                new NavigationRequest(NavigationType.Home,
                        null)), new BreadcrumbItem("Kundvagn", null)),
                breadcrumbsHBox, this);

        backButton.setOnMouseClicked(mouseEvent -> {
            triggerNavigationRequest(new NavigationRequest(NavigationType.Home, null));
            mouseEvent.consume();
        });

        nextButtonAnchorPane.setOnMouseClicked(mouseEvent -> {
            triggerNavigationRequest(new NavigationRequest(NavigationType.Delivery, null));
            mouseEvent.consume();
        });

        updateCartTotal();
        IMatDataHandler.getInstance().getShoppingCart().addShoppingCartListener(this);


    }

    public void refreshCartItems(){
        cartVBox.getChildren().clear();
        List<ShoppingItem> items = IMatDataHandler.getInstance().getShoppingCart().getItems();

        for (ShoppingItem item : items) {
            cartVBox.getChildren().add(new CartItemController(item.getProduct(), this));
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
        updateCartTotal();
    }

    private void updateCartTotal(){
        cartTotalLabel.setText("Totalt: " + new DecimalFormat("#.##").format(IMatDataHandler.getInstance().getShoppingCart().getTotal()) + " kr");
    }

}
