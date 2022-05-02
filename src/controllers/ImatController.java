package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import se.chalmers.cse.dat216.project.IMatDataHandler;

public class ImatController implements Initializable {

    ProductController productTest;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productTest = new ProductController(IMatDataHandler.getInstance().getProducts().get(0));
        productTest.setLayoutX(500);
        productTest.setLayoutY(500);
    }
}
