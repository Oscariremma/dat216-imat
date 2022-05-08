package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;
import java.util.List;

public class ProductsGridViewController extends AnchorPane {


    @FXML Label titleLabel;
    @FXML FlowPane productsFlowPane;

    public ProductsGridViewController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/productsGridView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void showProducts(List<Product> products, String title){
        productsFlowPane.getChildren().clear();

        titleLabel.setText(title);

        for (Product product : products) {
            productsFlowPane.getChildren().add(new ProductController(product));
        }


    }


}
