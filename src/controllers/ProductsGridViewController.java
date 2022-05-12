package controllers;

import helpers.BreadcrumbGenerator;
import interfaces.NavigationRequestListener;
import interfaces.NavigationRequestSender;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import structs.BreadcrumbItem;
import structs.NavigationRequest;
import structs.NavigationType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class ProductsGridViewController extends AnchorPane implements NavigationRequestSender {


    @FXML Label titleLabel;
    @FXML FlowPane productsFlowPane;
    @FXML VBox nothingHereVBox;

    @FXML HBox breadcrumbsHBox;

    @FXML Button backButton;

    private Dictionary<Integer,ProductController> productControllerCache = new Hashtable<>();

    private static List<NavigationRequestListener> navigationRequestListeners = new ArrayList<>();

    public ProductsGridViewController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/productsGridView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        for (Product product : IMatDataHandler.getInstance().getProducts()) {
            productControllerCache.put(product.getProductId(), new ProductController(product));
        }

        backButton.setOnMouseClicked(mouseEvent -> triggerNavigationRequest(new NavigationRequest(NavigationType.Back, null)));

    }

    public void setContent(String title, List<Product> products, List<BreadcrumbItem> breadcrumbs){
        productsFlowPane.getChildren().clear();

        BreadcrumbGenerator.generateBreadcrumbs(breadcrumbs, breadcrumbsHBox, this);

        titleLabel.setText(title);

        if (products.isEmpty()){
            if (!getChildren().contains(nothingHereVBox)) getChildren().add(nothingHereVBox);
            return;
        }else {
            getChildren().remove(nothingHereVBox);
        }

        for (Product product : products) {
            ProductController controller = productControllerCache.get(product.getProductId());
            if (controller == null) {
                controller = new ProductController(product);
                productControllerCache.put(product.getProductId(),controller);
            }
            productsFlowPane.getChildren().add(controller);
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
