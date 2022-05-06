package controllers;

import interfaces.CategorySelectedListener;
import interfaces.Collapsable;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.ProductCategory;
import structs.MainCategoryEntryRecord;
import structs.SubCategoryEntryRecord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CategoriesSidePanelController extends AnchorPane {

    @FXML VBox categoryVBox;

    private static List<Collapsable> collapsableMainCategories = new ArrayList<>();

    private static List<CategorySelectedListener> categorySelectedListeners = new ArrayList<>();

    public CategoriesSidePanelController(){
        System.out.println("Test");
    }

    public static void clearSelections(){
        collapseAllMainCategories();
        MainCategoryEntryController.deselectAllSubcategories();
    }

    public static void registerCategorySelectedListener(CategorySelectedListener listener){
        categorySelectedListeners.add(listener);
    }

    public static void raiseCategorySelectedEvent(ProductCategory category){
        for (CategorySelectedListener listener : categorySelectedListeners) {
            try {
                listener.categorySelected(category);
            }catch (Exception e){
                System.out.println(e);
            }
        }
    }

    private static void registerCollapsableMainCategory(Collapsable collapsable){
        collapsableMainCategories.add(collapsable);
    }

    private static void collapseAllMainCategories(){
        for (Collapsable col: collapsableMainCategories) {
            try {
                col.Collapse();
            }catch (Exception e){
                System.out.println(e);
            }
        }
    }

    @FXML
    public void initialize() {
        MainCategoryEntryController tmp = new MainCategoryEntryController(new MainCategoryEntryRecord(Arrays.asList(
                new SubCategoryEntryRecord(ProductCategory.BERRY, "Berry"),
                new SubCategoryEntryRecord(ProductCategory.BREAD, "Bread"),
                new SubCategoryEntryRecord(ProductCategory.CABBAGE, "Sallad")),
                "Test"));
        categoryVBox.getChildren().add(tmp);
        registerCollapsableMainCategory(tmp);
        tmp = new MainCategoryEntryController(new MainCategoryEntryRecord(Arrays.asList(
                new SubCategoryEntryRecord(ProductCategory.BERRY, "Berry"),
                new SubCategoryEntryRecord(ProductCategory.BREAD, "Bread"),
                new SubCategoryEntryRecord(ProductCategory.CABBAGE, "Sallad")),
                "Test 2"));
        registerCollapsableMainCategory(tmp);
        categoryVBox.getChildren().add(tmp);
    }
}
