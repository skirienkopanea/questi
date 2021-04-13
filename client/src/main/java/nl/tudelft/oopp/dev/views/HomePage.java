package nl.tudelft.oopp.dev.views;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomePage extends Application {

    /*Boiler plate code*/
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();

        //Load home page
        //This application uses the JavaFX library to load the homepage.fxml file,
        // which dictates what the scene will look like.
        // You can use the integrated Scene builder in IntelliJ to customize it.
        URL xmlUrl = getClass().getResource("/homepage.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /* Also boiler plate code, launches the JavaFX app */
    public static void main(String[] args) {
        launch(args);
    }
}
