package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ProductCategory;

import java.io.IOException;

public class SubCategoryEntryController extends AnchorPane {

    @FXML
    Label categoryLabel;


    private final ProductCategory category;

    public SubCategoryEntryController(ProductCategory category, String name){

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/subCategoryEntry.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.category = category;



    }

}
