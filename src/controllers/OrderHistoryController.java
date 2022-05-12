package controllers;

import helpers.BreadcrumbGenerator;
import interfaces.NavigationRequestListener;
import interfaces.NavigationRequestSender;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;
import structs.BreadcrumbItem;
import structs.NavigationRequest;
import structs.NavigationType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderHistoryController extends AnchorPane implements NavigationRequestSender {

    private static List<NavigationRequestListener> navigationRequestListeners = new ArrayList<>();

    @FXML VBox orderHistoryVBox;
    @FXML VBox nothingHereVBox;
    @FXML Button backButton;
    @FXML HBox breadcrumbsHBox;

    public OrderHistoryController(List<BreadcrumbItem> breadcrumbs){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/orderHistoryPage.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        refreshOrders();

        backButton.setOnMouseClicked(mouseEvent -> triggerNavigationRequest(new NavigationRequest(NavigationType.Back, null)));
        BreadcrumbGenerator.generateBreadcrumbs(breadcrumbs, breadcrumbsHBox, this);

    }

    public void refreshOrders(){
        if (IMatDataHandler.getInstance().getOrders().isEmpty()){
            if (!getChildren().contains(nothingHereVBox)) getChildren().add(nothingHereVBox);
            return;
        }else {
            getChildren().remove(nothingHereVBox);
        }

        for (Order order: IMatDataHandler.getInstance().getOrders()) {
            orderHistoryVBox.getChildren().add(new OrderHistoryEntryController(order));
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

}
