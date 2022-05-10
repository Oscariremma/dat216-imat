package controllers;

import interfaces.CategorySelectedListener;
import interfaces.Selectable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.ProductCategory;
import structs.MainCategoryEntryRecord;
import structs.SubCategoryEntryRecord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CategoriesSidePanelController extends AnchorPane {

    @FXML VBox categoryVBox;

    private static List<Selectable> selectableMainCategories = new ArrayList<>();

    private static List<CategorySelectedListener> categorySelectedListeners = new ArrayList<>();

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
                new SubCategoryEntryRecord(ProductCategory.BERRY, "Berry"),
                new SubCategoryEntryRecord(ProductCategory.BREAD, "Bread"),
                new SubCategoryEntryRecord(ProductCategory.CABBAGE, "Sallad")),
                "Test"));
        categoryVBox.getChildren().add(tmp);
        registerSelectableMainCategory(tmp);
        tmp = new MainCategoryEntryController(new MainCategoryEntryRecord(Arrays.asList(
                new SubCategoryEntryRecord(ProductCategory.BERRY, "Berry"),
                new SubCategoryEntryRecord(ProductCategory.BREAD, "Bread"),
                new SubCategoryEntryRecord(ProductCategory.CABBAGE, "Sallad")),
                "Test 2"));
        registerSelectableMainCategory(tmp);
        categoryVBox.getChildren().add(tmp);

    }

    public static void clearSelections(){
        collapseAllMainCategories();
        MainCategoryEntryController.deselectAllSubcategories();
    }

    public static void registerCategorySelectedListener(CategorySelectedListener listener){
        categorySelectedListeners.add(listener);
    }

    public static void raiseCategorySelectedEvent(String title , List<ProductCategory> categories, Selectable selectable){
        for (CategorySelectedListener listener : categorySelectedListeners) {
            try {
                listener.categorySelected(title, categories, selectable);
            }catch (Exception e){
                System.out.println(e);
            }
        }
    }

    public static void raiseDeselectedEvent(){
        for (CategorySelectedListener listener : categorySelectedListeners) {
            try {
                listener.goToHome();
            }catch (Exception e){
                System.out.println(e);
            }
        }
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
