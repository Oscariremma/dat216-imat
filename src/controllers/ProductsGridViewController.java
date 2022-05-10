package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class ProductsGridViewController extends AnchorPane {


    @FXML Label titleLabel;
    @FXML FlowPane productsFlowPane;
    @FXML VBox nothingHereVBox;

    private Dictionary<Integer,ProductController> productControllerCache = new Hashtable<>();

    public ProductsGridViewController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/productsGridView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        for (Product product : IMatDataHandler.getInstance().getProducts()) {
            productControllerCache.put(product.getProductId(), new ProductController(product));
        }

    }

    public void setContent(String title, List<Product> products){
        productsFlowPane.getChildren().clear();

        titleLabel.setText(title);

        if (products.isEmpty()){
            if (!getChildren().contains(nothingHereVBox)) getChildren().add(nothingHereVBox);
            return;
        }else {
            getChildren().remove(nothingHereVBox);
        }

        for (Product product : products) {
            ProductController controller = productControllerCache.get(product.getProductId());
            if (controller == null) {
                controller = new ProductController(product);
                productControllerCache.put(product.getProductId(),controller);
            }
            productsFlowPane.getChildren().add(controller);
        }


    }


}
