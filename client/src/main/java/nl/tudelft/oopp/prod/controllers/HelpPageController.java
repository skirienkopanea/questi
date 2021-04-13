package nl.tudelft.oopp.prod.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HelpPageController {

    @FXML
    private Button helpButton;

    @FXML
    private Label titleLabel;

    @FXML
    private Label introductionLabel;

    @FXML
    private Label howDoYouUseLabel;

    @FXML
    private Label studentLabel;

    @FXML
    private Label studentTextLabel;

    @FXML
    private Label lecturerLabel;

    @FXML
    private Label existingLectureLabel;

    @FXML
    private Label existingLectureTextLabel;

    @FXML
    private Label newLectureLabel;

    @FXML
    private Label newLectureTextLabel;

    /**
     * This function initialises the help screen.
     */
    @FXML
    public void initialize() {

        ArrayList<String> helpTextLines = new ArrayList<String>();

        try {
            String path = new String("client/src/main/resources/helpText.txt");
            File file = new File(path);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                helpTextLines.add(scanner.nextLine());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        titleLabel.setText(helpTextLines.get(0));
        introductionLabel.setText(helpTextLines.get(1));
        howDoYouUseLabel.setText(helpTextLines.get(2));
        studentLabel.setText(helpTextLines.get(3));
        studentTextLabel.setText(helpTextLines.get(4));
        lecturerLabel.setText(helpTextLines.get(5));
        existingLectureLabel.setText(helpTextLines.get(6));
        existingLectureTextLabel.setText(helpTextLines.get(7));
        newLectureLabel.setText(helpTextLines.get(8));
        newLectureTextLabel.setText(helpTextLines.get(9));

    }

    /**
     *  This functions loads the home screen.
     * @param event Needed for checkstyle
     * @throws Exception Needed for checkstyle
     */
    public void loadHomeScreen(ActionEvent event) throws Exception {

        Parent tableViewParent = FXMLLoader.load(getClass()
                .getClassLoader().getResource("homeScreen.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();

    }

}
