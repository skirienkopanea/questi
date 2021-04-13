package nl.tudelft.oopp.prod.views;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class StudentScreenDisplay extends Application {

    /**
     * This loads the student screen.
     * @param primaryStage main stage for students
     * @throws IOException an IO exception
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        //now it references the student Screen there may be issues with referencing each box
        URL xmlUrl = getClass().getResource("/studentScreen.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        primaryStage.setScene(new Scene(root));
        primaryStage.setMinWidth(898);
        primaryStage.setMinHeight(734.6);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

