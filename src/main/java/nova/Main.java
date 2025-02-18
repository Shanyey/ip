package nova;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import nova.gui.MainWindow;
import nova.nova.Nova;

/**
 * A GUI for Nova using FXML.
 */
public class Main extends Application {

    private final Nova nova = new Nova();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("Nova");
            stage.getIcons().add(new Image("/images/nova.png"));
            fxmlLoader.<MainWindow>getController().setNova(nova); // inject the Nova instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
