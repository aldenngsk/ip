package sixseven;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    private static final String DATA_FILE = "data" + java.io.File.separator + "duke.txt";

    @Override
    public void start(Stage stage) throws IOException {
        java.net.URL fxmlUrl = getClass().getResource("/view/MainWindow.fxml");
        if (fxmlUrl == null) {
            throw new IOException("FXML not found: /view/MainWindow.fxml");
        }
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        AnchorPane root = loader.load();
        sixseven.gui.MainWindow controller = (sixseven.gui.MainWindow) loader.getController();
        controller.setDataFile(DATA_FILE);
        controller.showWelcome();

        Scene scene = new Scene(root);
        String css = getClass().getResource("/css/style.css") != null
                ? getClass().getResource("/css/style.css").toExternalForm()
                : null;
        if (css != null) {
            scene.getStylesheets().add(css);
        }
        stage.setScene(scene);
        stage.setTitle("SixSeven");
        stage.setMinWidth(400);
        stage.setMinHeight(480);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
