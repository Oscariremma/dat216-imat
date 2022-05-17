package controllers;

import interfaces.NavigationRequestListener;
import interfaces.NavigationRequestSender;
import interfaces.Selectable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.ProductCategory;
import structs.MainCategoryEntryRecord;
import structs.NavigationRequest;
import structs.NavigationType;
import structs.SubCategoryEntryRecord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CategoriesSidePanelController extends AnchorPane implements NavigationRequestSender {

    @FXML VBox categoryVBox;

    private static List<Selectable> selectableMainCategories = new ArrayList<>();

    private static List<NavigationRequestListener> navigationRequestListeners = new ArrayList<>();


    public CategoriesSidePanelController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/categoriesSidePanel.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        MainCategoryEntryController tmp = new MainCategoryEntryController(new MainCategoryEntryRecord(Arrays.asList(
                new SubCategoryEntryRecord(ProductCategory.CABBAGE, "Sallad"),
                new SubCategoryEntryRecord(ProductCategory.VEGETABLE_FRUIT, "Grönt och Gott"),
                new SubCategoryEntryRecord(ProductCategory.ROOT_VEGETABLE, "Rotfrukt"),
                new SubCategoryEntryRecord(ProductCategory.POTATO_RICE,"Ris och Potatis")),
                "Grönsaker"), this);
        categoryVBox.getChildren().add(tmp);
        registerSelectableMainCategory(tmp);
        tmp = new MainCategoryEntryController(new MainCategoryEntryRecord(Arrays.asList(
                new SubCategoryEntryRecord(ProductCategory.FRUIT, "Frukt"),
                new SubCategoryEntryRecord(ProductCategory.EXOTIC_FRUIT, "Exotisk Frukt"),
                new SubCategoryEntryRecord(ProductCategory.MELONS, "Meloner"),
                new SubCategoryEntryRecord(ProductCategory.CITRUS_FRUIT, "Citrus Frukt"),
                new SubCategoryEntryRecord(ProductCategory.BERRY, "Bär")),
                "Frukt & Bär"), this);
        registerSelectableMainCategory(tmp);
        categoryVBox.getChildren().add(tmp);
        tmp = new MainCategoryEntryController(new MainCategoryEntryRecord(Arrays.asList(
                new SubCategoryEntryRecord(ProductCategory.DAIRIES, "Mejeri och Ägg")),
                "Mejeri & Ägg"), this);
        registerSelectableMainCategory(tmp);
        categoryVBox.getChildren().add(tmp);
        tmp = new MainCategoryEntryController(new MainCategoryEntryRecord(Arrays.asList(
                new SubCategoryEntryRecord(ProductCategory.BREAD, "Bröd"),
                new SubCategoryEntryRecord(ProductCategory.FLOUR_SUGAR_SALT, "Mjöl"),
                new SubCategoryEntryRecord(ProductCategory.NUTS_AND_SEEDS, "Frön och Nötter")),
                "Bröd & Bageri"), this);
        registerSelectableMainCategory(tmp);
        categoryVBox.getChildren().add(tmp);
        tmp = new MainCategoryEntryController(new MainCategoryEntryRecord(Arrays.asList(
                new SubCategoryEntryRecord(ProductCategory.MEAT, "Kött"),
                new SubCategoryEntryRecord(ProductCategory.FISH, "Fisk")),
                "Kött & Fisk"), this);
        registerSelectableMainCategory(tmp);
        categoryVBox.getChildren().add(tmp);
        tmp = new MainCategoryEntryController(new MainCategoryEntryRecord(Arrays.asList(
                new SubCategoryEntryRecord(ProductCategory.HOT_DRINKS, "Varm dryck"),
                new SubCategoryEntryRecord(ProductCategory.COLD_DRINKS, "Kall dryck")),
                "Dryck"), this);
        registerSelectableMainCategory(tmp);
        categoryVBox.getChildren().add(tmp);
        tmp = new MainCategoryEntryController(new MainCategoryEntryRecord(Arrays.asList(
                new SubCategoryEntryRecord(ProductCategory.POD, "Baljväxter"),
                new SubCategoryEntryRecord(ProductCategory.HERB, "Örter")),
                "Baljväxter & Örter"), this);
        registerSelectableMainCategory(tmp);
        categoryVBox.getChildren().add(tmp);
        tmp = new MainCategoryEntryController(new MainCategoryEntryRecord(Arrays.asList(
                new SubCategoryEntryRecord(ProductCategory.PASTA, "Pasta"),
                new SubCategoryEntryRecord(ProductCategory.POTATO_RICE, "Potatis och Ris"),
                new SubCategoryEntryRecord(ProductCategory.FLOUR_SUGAR_SALT, "Mjöl, Socker, Salt"),
                new SubCategoryEntryRecord(ProductCategory.BREAD, "Bröd"),
                new SubCategoryEntryRecord(ProductCategory.NUTS_AND_SEEDS, "Frön och nötter")),
                "Skafferi"), this);
        registerSelectableMainCategory(tmp);
        categoryVBox.getChildren().add(tmp);

    }

    public static void clearSelections(){
        collapseAllMainCategories();
        MainCategoryEntryController.deselectAllSubcategories();
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

    public void raiseCategorySelectedEvent(String title , List<ProductCategory> categories, Selectable selectable){
        triggerNavigationRequest(new NavigationRequest(NavigationType.Category, new Object[]{title ,categories, selectable}));
    }

    public void raiseDeselectedEvent(){
        triggerNavigationRequest(new NavigationRequest(NavigationType.Home, null));
    }

    private static void registerSelectableMainCategory(Selectable selectable){
        selectableMainCategories.add(selectable);
    }

    private static void collapseAllMainCategories(){
        for (Selectable col: selectableMainCategories) {
            try {
                col.Deselect();
            }catch (Exception e){
                System.out.println(e);
            }
        }
    }
}
