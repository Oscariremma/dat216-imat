package controllers;

import interfaces.Selectable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.ProductCategory;
import structs.MainCategoryEntryRecord;
import structs.SubCategoryEntryRecord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainCategoryEntryController extends AnchorPane implements Selectable {

    @FXML AnchorPane mainCategoryAnchorPane;
    @FXML Label mainCategoryLabel;
    @FXML VBox subCategoriesVBox;
    @FXML ImageView arrowImageView;

    private static List<Selectable> allSelectableSubcategories = new ArrayList<>();

    private final List<ProductCategory> productCategories = new ArrayList<>();
    
    private boolean expanded = false;

    public MainCategoryEntryController(MainCategoryEntryRecord entry){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/mainCategoryEntry.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        
        
        mainCategoryLabel.setText(entry.name());

        for (SubCategoryEntryRecord subEntry: entry.subCategories()) {
            SubCategoryEntryController subController = new SubCategoryEntryController(subEntry, this);
            registerSelectableSubCategory(subController);
            subCategoriesVBox.getChildren().add(subController);
            productCategories.add(subEntry.category());
        }

        this.getChildren().remove(subCategoriesVBox);

        mainCategoryAnchorPane.setOnMouseClicked((mouseEvent) -> clicked());


    }

    private void clicked(){
        if (expanded){
            Deselect();
            CategoriesSidePanelController.raiseDeselectedEvent();
        }else {
            Select();
            CategoriesSidePanelController.raiseCategorySelectedEvent(mainCategoryLabel.getText(), productCategories, this);
        }
    }

    private static void registerSelectableSubCategory(Selectable selectable){
        allSelectableSubcategories.add(selectable);
    }

    public static void deselectAllSubcategories(){
        for (Selectable des :
                allSelectableSubcategories) {
            try {
                des.Deselect();
            }catch (Exception e){
                System.out.println(e);
            }
        }
    };

    @Override
    public void Select() {
        if (!expanded){
            CategoriesSidePanelController.clearSelections();
            getChildren().add(subCategoriesVBox);
            mainCategoryAnchorPane.getStyleClass().add("selected");
            mainCategoryLabel.getStyleClass().add("selected");
            arrowImageView.setRotate(180);
            expanded = true;
        }
    }

    @Override
    public void Deselect() {
        if (expanded){
            getChildren().remove(subCategoriesVBox);
            mainCategoryAnchorPane.getStyleClass().remove("selected");
            mainCategoryLabel.getStyleClass().remove("selected");
            arrowImageView.setRotate(90);
            expanded = false;
        }
    }
}
