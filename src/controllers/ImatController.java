package controllers;

import interfaces.CategorySelectedListener;
import interfaces.HeaderNavigationListener;
import interfaces.Selectable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.*;

import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ProductCategory;
import structs.NavigationRequest;
import structs.NavigationType;

public class ImatController extends AnchorPane implements Initializable, CategorySelectedListener, HeaderNavigationListener {

    @FXML AnchorPane contentRootPane;



    CategoriesSidePanelController categoriesSidePanel = new CategoriesSidePanelController();
    ProductsGridViewController productsGridViewController = new ProductsGridViewController();
    OrderHistoryController orderHistoryController = new OrderHistoryController();

    private static final double categoriesSidePanelWidth = 370;

    private Stack<NavigationRequest> navigationHistory = new Stack<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        goToHome();

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

    public void goBack(){
        if (navigationHistory.size() < 2){
            //No history
            return;
        }

        //Remove current page from history
        navigationHistory.pop();

        Object[] args = navigationHistory.peek().args();
        switch (navigationHistory.peek().navigationType()){

            case Home -> goToHome();
            case OrderHistory -> goToOrderHistory();
            case Favorites -> goToFavorites();
            case Category -> {
                categorySelected((String) args[0], (List<ProductCategory>) args[1], (Selectable) args[2]);
                CategoriesSidePanelController.clearSelections();
                ((Selectable) args[2]).Select();
            }
            case Search -> goToSearchResult((String) args[0]);
            case Cart -> goToCart();
        }


    }


    @Override
    public void categorySelected(String title, List<ProductCategory> categories, Selectable selectable) {
        //Log navigation if last isn't category or args differ
        if (navigationHistory.size() == 0 ||
                navigationHistory.peek().navigationType() != NavigationType.Category ||
                !navigationHistory.peek().args()[0].equals(title) && !navigationHistory.peek().args()[1].equals(categories)) {
            navigationHistory.add(new NavigationRequest(NavigationType.Category, new Object[]{title, categories, selectable}));
        }


        List<Product> products = new ArrayList<>();

        for (ProductCategory category : categories) {
            products.addAll(IMatDataHandler.getInstance().getProducts(category));
        }

        productsGridViewController.setContent(title, products);

        setViewTo(productsGridViewController, true);

    }

    @Override
    public void goToHome() {
        if (navigationHistory.size() == 0 || navigationHistory.peek().navigationType() != NavigationType.Home)
            navigationHistory.add(new NavigationRequest(NavigationType.Home, null));


        List<Product> products = new ArrayList<>();
        products.addAll(IMatDataHandler.getInstance().getProducts());
        Collections.shuffle(products);

        productsGridViewController.setContent("Populära varor", new ArrayList<>(products.subList(0,10)));

        CategoriesSidePanelController.clearSelections();
        setViewTo(productsGridViewController, true);

    }

    @Override
    public void goToFavorites() {
        if (navigationHistory.size() == 0 || navigationHistory.peek().navigationType() != NavigationType.Favorites)
            navigationHistory.add(new NavigationRequest(NavigationType.Favorites, null));

        CategoriesSidePanelController.clearSelections();
        productsGridViewController.setContent("Favoriter", IMatDataHandler.getInstance().favorites());
        setViewTo(productsGridViewController, true);
    }

    @Override
    public void goToOrderHistory() {
        if (navigationHistory.size() == 0 || navigationHistory.peek().navigationType() != NavigationType.OrderHistory)
            navigationHistory.add(new NavigationRequest(NavigationType.OrderHistory, null));

        CategoriesSidePanelController.clearSelections();
        setViewTo(orderHistoryController, true);
    }

    @Override
    public void goToCart() {
        if (navigationHistory.size() == 0 || navigationHistory.peek().navigationType() != NavigationType.Cart)
            navigationHistory.add(new NavigationRequest(NavigationType.Cart, null));
        //todo
    }

    @Override
    public void goToSearchResult(String quarry) {

        //Log if last navigation wasn't Search or search quarry differs
        if (navigationHistory.size() == 0 || (navigationHistory.peek().navigationType() != NavigationType.Search ||
                //Check search quarry
                !navigationHistory.peek().args()[0].equals(quarry))){

            navigationHistory.add(new NavigationRequest(NavigationType.Search, new Object[] {quarry}));
        }


        CategoriesSidePanelController.clearSelections();
        productsGridViewController.setContent("Sökresultat för \"" + quarry + "\"",
                IMatDataHandler.getInstance().findProducts(quarry));
        setViewTo(productsGridViewController, true);
    }
}
