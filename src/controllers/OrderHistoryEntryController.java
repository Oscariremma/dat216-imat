package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class OrderHistoryEntryController extends AnchorPane {

    @FXML VBox entryRowVBox;
    @FXML Label orderNameLabel;
    @FXML Label orderDateLabel;
    @FXML Label orderTotalLabel;
    @FXML Button orderHistoryBuyButton;


    @FXML AnchorPane orderHistoryOpen;
    @FXML AnchorPane orderHistoryCollapsed;

    @FXML ImageView arrowImageView;

    boolean expanded;

    private final Order order;



    public OrderHistoryEntryController(Order order){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/orderHistoryEntry.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        double total = 0;


        this.order = order;
        double itemCount = 0;
        for (ShoppingItem item:order.getItems() ) {
            entryRowVBox.getChildren().add(new OrderHistoryRow(item));
            total += item.getTotal();
            itemCount += item.getAmount();
        }



        orderNameLabel.setText("Order " + order.getOrderNumber());
        orderDateLabel.setText(new SimpleDateFormat("yyyy/MM/dd").format(order.getDate()));
        orderTotalLabel.setText(new DecimalFormat("#.##").format(total) + "kr - " +
                new DecimalFormat("#").format(itemCount) +  ((itemCount != 1) ? " saker" : " sak"));

        getChildren().remove(orderHistoryOpen);
        arrowImageView.setRotate(90);

        orderHistoryCollapsed.setOnMouseClicked(mouseEvent -> clicked());
        orderHistoryBuyButton.setOnMouseClicked(mouseEvent -> addOrderToCart());

    }

    private void clicked(){
        if (expanded){
            getChildren().remove(orderHistoryOpen);
            arrowImageView.setRotate(90);
            this.setMinHeight(-1);
            expanded = false;
        }else {
            getChildren().add(orderHistoryOpen);
            this.setMinHeight(200+ (entryRowVBox.getChildren().size()*41)-20);
            orderHistoryOpen.toBack();
            arrowImageView.setRotate(180);
            expanded = true;
        }
    }

    private void addOrderToCart(){
        ShoppingCart shoppingCart = IMatDataHandler.getInstance().getShoppingCart();

        for (ShoppingItem item : order.getItems()) {
            Boolean itemFound = false;
            for (ShoppingItem existingItem: shoppingCart.getItems()) {
                if (item.getProduct().getProductId() == existingItem.getProduct().getProductId()){
                    //Add the new amount to the old amount
                    existingItem.setAmount(existingItem.getAmount() + item.getAmount());
                    shoppingCart.fireShoppingCartChanged(existingItem, true);
                    itemFound = true;
                    break;
                }
            }
            if (itemFound) continue;

            //Add the non-existing item to the cart
            shoppingCart.getItems().add(item);
            shoppingCart.fireShoppingCartChanged(item, true);

        }

    }






}
