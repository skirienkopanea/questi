package nl.tudelft.oopp.prod.views;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ModeratorScreenDisplay extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        //now it references the moderator Screen there may be issues with referencing each box
        URL xmlUrl = getClass().getResource("/moderatorScreen.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        stage.setScene(new Scene(root));
        stage.setMinWidth(898);
        stage.setMinHeight(734.6);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
