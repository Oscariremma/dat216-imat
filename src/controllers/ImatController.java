package controllers;

import interfaces.CategorySelectedListener;
import interfaces.HeaderNavigationListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.*;

import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ProductCategory;

public class ImatController extends AnchorPane implements Initializable, CategorySelectedListener, HeaderNavigationListener {

    @FXML AnchorPane contentRootPane;



    CategoriesSidePanelController categoriesSidePanel = new CategoriesSidePanelController();
    ProductsGridViewController productsGridViewController = new ProductsGridViewController();
    OrderHistoryController orderHistoryController = new OrderHistoryController();
    DeliveryController deliveryController = new DeliveryController();

    private static final double categoriesSidePanelWidth = 370;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        goToCart();

        HeaderController.registerNavigationListener(this);
        CategoriesSidePanelController.registerCategorySelectedListener(this);
    }

    private void setViewTo(AnchorPane pane, boolean showCategories){
        contentRootPane.getChildren().clear();
        contentRootPane.getChildren().add(pane);

        contentRootPane.setRightAnchor(pane, 0.0);
        contentRootPane.setTopAnchor(pane, 0.0);
        contentRootPane.setBottomAnchor(pane, 0.0);

        if (showCategories){
            contentRootPane.getChildren().add(categoriesSidePanel);
            contentRootPane.setLeftAnchor(contentRootPane, 0.0);
            contentRootPane.setTopAnchor(contentRootPane, 150.0);
            contentRootPane.setBottomAnchor(contentRootPane, 0.0);

            contentRootPane.setLeftAnchor(pane, categoriesSidePanelWidth);
        }else{
            contentRootPane.getChildren().remove(categoriesSidePanel);
            contentRootPane.setLeftAnchor(pane, 0.0);
        }




    }


    @Override
    public void categorySelected(String title, List<ProductCategory> categories) {
        List<Product> products = new ArrayList<>();

        for (ProductCategory category : categories) {
            products.addAll(IMatDataHandler.getInstance().getProducts(category));
        }

        productsGridViewController.setContent(title, products);

        setViewTo(productsGridViewController, true);

    }

    @Override
    public void goToHome() {
        List<Product> products = new ArrayList<>();
        products.addAll(IMatDataHandler.getInstance().getProducts());
        Collections.shuffle(products);

        productsGridViewController.setContent("Populära varor", new ArrayList<>(products.subList(0,10)));

        CategoriesSidePanelController.clearSelections();
        setViewTo(productsGridViewController, true);

    }

    @Override
    public void goToFavorites() {
        CategoriesSidePanelController.clearSelections();
        productsGridViewController.setContent("Favoriter", IMatDataHandler.getInstance().favorites());
        setViewTo(productsGridViewController, true);
    }

    @Override
    public void goToOrderHistory() {
        CategoriesSidePanelController.clearSelections();
        setViewTo(orderHistoryController, true);
    }

    @Override
    public void goToCart() {
        //todo
        setViewTo(deliveryController, false);
    }

    @Override
    public void goToSearchResult(String quarry) {
        CategoriesSidePanelController.clearSelections();
        productsGridViewController.setContent("Sökresultat för \"" + quarry + "\"",
                IMatDataHandler.getInstance().findProducts(quarry));
        setViewTo(productsGridViewController, true);
    }
}
