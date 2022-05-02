import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class IMat extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Font.loadFont(this.getClass().getResourceAsStream("LexendDeca-Regular.ttf"), 40.0D);

        ResourceBundle bundle = java.util.ResourceBundle.getBundle("Imat");

        Parent root = FXMLLoader.load(getClass().getResource("fxml/imat.fxml"), bundle);


        Scene scene = new Scene(root, 1920, 1080);


        //stage.setMinHeight(520);
        //stage.setMinWidth(810);
        stage.setTitle(bundle.getString("application.name"));
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
