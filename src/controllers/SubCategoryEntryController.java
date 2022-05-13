package controllers;

import interfaces.NavigationRequestSender;
import interfaces.Selectable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ProductCategory;
import structs.BreadcrumbItem;
import structs.NavigationRequest;
import structs.NavigationType;
import structs.SubCategoryEntryRecord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubCategoryEntryController extends AnchorPane implements Selectable {

    @FXML
    Label categoryLabel;


    private final ProductCategory category;
    private final Selectable parent;
    private final NavigationRequestSender requestSender;
    private final List<BreadcrumbItem> breadcrumbItems;

    public SubCategoryEntryController(SubCategoryEntryRecord entry, Selectable parent, NavigationRequestSender requestSender, List<BreadcrumbItem> breadcrumbStart){

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/subCategoryEntry.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.parent = parent;
        this.requestSender = requestSender;
        this.breadcrumbItems = new ArrayList<>(breadcrumbStart);
        this.breadcrumbItems.add(new BreadcrumbItem(entry.name(), null));

        category = entry.category();
        categoryLabel.setText(entry.name());

        setOnMouseClicked(mouseEvent -> clicked());

    }

    private void clicked(){
        MainCategoryEntryController.deselectAllSubcategories();
        getStyleClass().add("selected");
        categoryLabel.getStyleClass().add("selected");
        requestSender.triggerNavigationRequest(new NavigationRequest(NavigationType.Category, new Object[]{categoryLabel.getText(), Arrays.asList(category), this, breadcrumbItems}));
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
