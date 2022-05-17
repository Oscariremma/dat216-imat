package controllers;

import interfaces.NavigationRequestListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.ShoppingItem;
import structs.NavigationRequest;
import structs.NavigationType;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderHistoryRow extends AnchorPane {



    @FXML Label nameAndAmountLabel;
    @FXML Label priceLabel;

    private static final DecimalFormat noDecimals = new DecimalFormat("#");
    private static final DecimalFormat twoDecimals = new DecimalFormat("#.##");
    private static List<NavigationRequestListener> navigationRequestListeners = new ArrayList<>();

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
        priceLabel.setText(twoDecimals.format(item.getTotal()) + " kr");

        setOnMouseClicked(mouseEvent -> {
            triggerNavigationRequest(new NavigationRequest(NavigationType.ShowModalInfo, new Object[]{item.getProduct()}));
            mouseEvent.consume();
        });

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
