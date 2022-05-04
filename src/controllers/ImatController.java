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

    @FXML
    Pane test;

    ProductController productTest;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productTest = new ProductController(IMatDataHandler.getInstance().getProducts().get(0));

        test.getChildren().add(productTest);
    }
}
