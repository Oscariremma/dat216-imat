package controllers;

import interfaces.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.*;

import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ProductCategory;
import structs.BreadcrumbItem;
import structs.NavigationRequest;
import structs.NavigationType;

public class ImatController extends AnchorPane implements Initializable, HeaderNavigationListener, NavigationRequestListener {

    @FXML AnchorPane contentRootPane;



    CategoriesSidePanelController categoriesSidePanel = new CategoriesSidePanelController();
    ProductsGridViewController productsGridViewController = new ProductsGridViewController();
    OrderHistoryController orderHistoryController = new OrderHistoryController(Arrays.asList(homeWithNav,
            new BreadcrumbItem("Köphistorik", null)));
    DeliveryController deliveryController = new DeliveryController();

    private static final double categoriesSidePanelWidth = 370;

    private Stack<NavigationRequest> navigationHistory = new Stack<>();

    private static final BreadcrumbItem homeWithNav = new BreadcrumbItem("Hem", new NavigationRequest(NavigationType.Home, null));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        goToCart();

        HeaderController.registerNavigationListener(this);
        CategoriesSidePanelController.registernavigationRequestListener(this);
        ProductsGridViewController.registernavigationRequestListener(this);
        OrderHistoryController.registernavigationRequestListener(this);
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
        goToNavigationRequest(navigationHistory.peek());

    }

    public void goToNavigationRequest(NavigationRequest request){
        Object[] args = request.args();
        switch (request.navigationType()){

            case Home -> goToHome();
            case OrderHistory -> goToOrderHistory();
            case Favorites -> goToFavorites();
            case Category -> {
                categorySelected((String) args[0], (List<ProductCategory>) args[1], (Selectable) args[2], (List<BreadcrumbItem>) args[3]);
                CategoriesSidePanelController.clearSelections();
                ((Selectable) args[2]).Select();
            }
            case Search -> goToSearchResult((String) args[0]);
            case Cart -> goToCart();
            case Back -> goBack();
        }
    }

    public void categorySelected(String title, List<ProductCategory> categories, Selectable selectable, List<BreadcrumbItem> breadcrumbItems) {
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

        productsGridViewController.setContent(title, products, breadcrumbItems);

        setViewTo(productsGridViewController, true);

    }

    @Override
    public void goToHome() {
        if (navigationHistory.size() == 0 || navigationHistory.peek().navigationType() != NavigationType.Home)
            navigationHistory.add(new NavigationRequest(NavigationType.Home, null));


        List<Product> products = new ArrayList<>();
        products.addAll(IMatDataHandler.getInstance().getProducts());
        Collections.shuffle(products);

        productsGridViewController.setContent("Populära varor", new ArrayList<>(products.subList(0,10)), Arrays.asList(new BreadcrumbItem("Hem", null)));

        CategoriesSidePanelController.clearSelections();
        setViewTo(productsGridViewController, true);

    }

    @Override
    public void goToFavorites() {
        if (navigationHistory.size() == 0 || navigationHistory.peek().navigationType() != NavigationType.Favorites)
            navigationHistory.add(new NavigationRequest(NavigationType.Favorites, null));

        CategoriesSidePanelController.clearSelections();
        productsGridViewController.setContent("Favoriter", IMatDataHandler.getInstance().favorites(), Arrays.asList(homeWithNav, new BreadcrumbItem("Favoriter", null)));
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
        setViewTo(deliveryController, false);
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
                IMatDataHandler.getInstance().findProducts(quarry),
                Arrays.asList(homeWithNav, new BreadcrumbItem("Sökresultat", null)));
        setViewTo(productsGridViewController, true);
    }
}
