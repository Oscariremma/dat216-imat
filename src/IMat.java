import java.util.ResourceBundle;

import controllers.ImatController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import se.chalmers.cse.dat216.project.IMatDataHandler;

public class IMat extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        System.setProperty("prism.lcdtext", "false");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> IMatDataHandler.getInstance().shutDown()));

        ResourceBundle bundle = java.util.ResourceBundle.getBundle("Imat");

        Parent root = new ImatController();
        Scene scene = new Scene(root, 1920, 1040);


        //stage.setMinHeight(520);
        //stage.setMinWidth(810);
        stage.setTitle(bundle.getString("application.name"));
        stage.setScene(scene);
        //stage.setMaximized(true);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
