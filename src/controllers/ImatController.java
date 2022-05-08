package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import se.chalmers.cse.dat216.project.IMatDataHandler;

public class ImatController extends AnchorPane implements Initializable {

    @FXML AnchorPane contentRootPane;
    @FXML AnchorPane categoriesSidePanel;

    ProductsGridViewController productsGridViewController = new ProductsGridViewController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        productsGridViewController.showProducts(IMatDataHandler.getInstance().getProducts(), "Test");
        contentRootPane.getChildren().add(productsGridViewController);
        contentRootPane.setRightAnchor(productsGridViewController, 0.0);
        contentRootPane.setLeftAnchor(productsGridViewController, 0.0);
        contentRootPane.setTopAnchor(productsGridViewController, 0.0);
        contentRootPane.setBottomAnchor(productsGridViewController, 0.0);
    }
}
