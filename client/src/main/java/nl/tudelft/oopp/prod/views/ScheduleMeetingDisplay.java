package nl.tudelft.oopp.prod.views;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScheduleMeetingDisplay extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/scheduleScreen.fxml");
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