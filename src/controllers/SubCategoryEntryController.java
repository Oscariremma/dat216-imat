package controllers;

import interfaces.Selectable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ProductCategory;
import structs.SubCategoryEntryRecord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class SubCategoryEntryController extends AnchorPane implements Selectable {

    @FXML
    Label categoryLabel;


    private final ProductCategory category;
    private final Selectable parent;

    public SubCategoryEntryController(SubCategoryEntryRecord entry, Selectable parent){

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/subCategoryEntry.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.parent = parent;

        category = entry.category();
        categoryLabel.setText(entry.name());

        setOnMouseClicked(mouseEvent -> clicked());

    }

    private void clicked(){
        MainCategoryEntryController.deselectAllSubcategories();
        getStyleClass().add("selected");
        categoryLabel.getStyleClass().add("selected");
        CategoriesSidePanelController.raiseCategorySelectedEvent(categoryLabel.getText() ,Arrays.asList(category), this);
    }

    @Override
    public void Select() {
        parent.Select();
        MainCategoryEntryController.deselectAllSubcategories();
        getStyleClass().add("selected");
        categoryLabel.getStyleClass().add("selected");
    }

    @Override
    public void Deselect() {
        getStyleClass().remove("selected");
        categoryLabel.getStyleClass().remove("selected");
    }
}
