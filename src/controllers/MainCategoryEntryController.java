package controllers;

import interfaces.Collapsable;
import interfaces.Deselectable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import structs.MainCategoryEntryRecord;
import structs.SubCategoryEntryRecord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainCategoryEntryController extends AnchorPane implements Collapsable {

    @FXML AnchorPane mainCategoryAnchorPane;
    @FXML Label mainCategoryLabel;
    @FXML VBox subCategoriesVBox;

    private static List<Deselectable> allDeselectableSubcategories = new ArrayList<>();

    private final double expandedHeight;
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
            SubCategoryEntryController subController = new SubCategoryEntryController(subEntry);
            registrerDeselectableSubCategory(subController);
            subCategoriesVBox.getChildren().add(subController);
        }

        //Set to collapsed height

        this.getChildren().remove(subCategoriesVBox);
        //Calculate the expanded height;
        expandedHeight = mainCategoryAnchorPane.getHeight() + 2 + 62 * subCategoriesVBox.getChildren().size();


        mainCategoryAnchorPane.setOnMouseClicked((mouseEvent) -> clicked());


    }

    private void clicked(){
        if (expanded){
            mainCategoryAnchorPane.getStyleClass().remove("selected");
            mainCategoryLabel.getStyleClass().remove("selected");
            this.getChildren().remove(subCategoriesVBox);
        }else {
            CategoriesSidePanelController.clearSelections();
            mainCategoryAnchorPane.getStyleClass().add("selected");
            mainCategoryLabel.getStyleClass().add("selected");
            this.getChildren().add(subCategoriesVBox);
        }

        expanded = !expanded;
    }

    private static void registrerDeselectableSubCategory(Deselectable deselectable){
        allDeselectableSubcategories.add(deselectable);
    }

    public static void deselectAllSubcategories(){
        for (Deselectable des :
                allDeselectableSubcategories) {
            try {
                des.Deselect();
            }catch (Exception e){
                System.out.println(e);
            }
        }
    };

    @Override
    public void Collapse() {
        if (expanded){
            getChildren().remove(subCategoriesVBox);
            mainCategoryAnchorPane.getStyleClass().remove("selected");
            mainCategoryLabel.getStyleClass().remove("selected");
            expanded = false;
        }
    }
}
