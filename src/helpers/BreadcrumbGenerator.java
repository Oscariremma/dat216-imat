package helpers;

import interfaces.NavigationRequestSender;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import structs.BreadcrumbItem;

import java.util.List;

public class BreadcrumbGenerator {
    public static void generateBreadcrumbs(List<BreadcrumbItem> breadcrumbs, HBox hBox, NavigationRequestSender navigationRequestSender){
        hBox.getChildren().clear();
        if (breadcrumbs == null) return;
        for (int i = 0; i < breadcrumbs.size(); i++){
            BreadcrumbItem breadcrumb = breadcrumbs.get(i);
            Label label = new Label(breadcrumb.title());
            label.getStyleClass().add("breadcrumb");
            label.setMinWidth(Region.USE_PREF_SIZE);
            if (breadcrumb.navigationRequest() != null){
                label.setOnMouseClicked(mouseEvent ->  navigationRequestSender.triggerNavigationRequest(breadcrumb.navigationRequest()));
                label.getStyleClass().add("clickableBreadcrumb");
            }
            hBox.getChildren().add(label);

            //If not last element, add slash
            if (i < breadcrumbs.size()-1){
                label = new Label("/");
                label.getStyleClass().add("breadcrumb");
                hBox.getChildren().add(label);
            }
        }
    }
}
