package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.io.IOException;

public class ProductController extends AnchorPane {


    @FXML Label productPriceLabel;
    @FXML Label productPricePerUnitLabel;
    @FXML Label productNameLabel;
    @FXML Label productSubtextLabel;
    @FXML ImageView productImageView;
    @FXML ImageView heartImageView;
    @FXML Button addButton;


    public ProductController (Product product){

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/product.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }


        productNameLabel.setText(product.getName());
        productPriceLabel.setText(product.getPrice() + " " + product.getUnit());
        productImageView.setImage(IMatDataHandler.getInstance().getFXImage(product));


    }


}
