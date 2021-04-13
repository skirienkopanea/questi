package nl.tudelft.oopp.prod.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.Timer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import nl.tudelft.oopp.prod.communication.LectureRequest;

import nl.tudelft.oopp.prod.communication.PollOptionRequest;
import nl.tudelft.oopp.prod.communication.PollRequest;
import nl.tudelft.oopp.prod.communication.UserRequest;
import nl.tudelft.oopp.prod.objects.Lecture;
import nl.tudelft.oopp.prod.objects.Session;

public class HomeScreenController {
    @FXML
    private TextField link;
    @FXML
    private TextField username;
    @FXML
    private Button startDirect;
    @FXML
    private Button helpButton;

    /**
     * Sets the boolean clicked for all three speedoptions to be false.
     */
    public static void resetFastPoll() {
        Session.setClickedOne(false);
        Session.setClickedTwo(false);
        Session.setClickedThree(false);
    }

    /**
     * Request to login with a username to a lecture.
     */
    public void buttonEnterClicked() {

        String name = username.getText();
        String token = link.getText();

        // Start the lecture
        if (!name.isEmpty() && !token.isEmpty()) {

            try {
                Session.setLecture(LectureRequest.getLecture(token));

                if (Session.getLecture().getId() == -1
                        && LectureRequest.getModeratorLecture(token).getId() != -1) {

                    //Check by moderator link
                    Session.setLecture(LectureRequest.getModeratorLecture(token));

                    //Log in as moderator
                    Session.setUser(UserRequest.addUser(name, Session.getLecture().getId(), true));
                    resetFastPoll();
                    // Load moderator screen
                    Session.timer = new Timer();
                    ModeratorScreenController.init(Session.getLecture(), Session.getUser());
                    Parent tableViewParent = FXMLLoader.load(getClass()
                            .getClassLoader().getResource("moderatorScreen.fxml"));
                    Scene tableViewScene = new Scene(tableViewParent);
                    Stage window = (Stage) Stage.getWindows().get(0);
                    window.setScene(tableViewScene);
                    window.show();
                } else if (Session.getLecture().getId() != -1) {
                    //Login as normal user
                    if (LectureRequest.isOngoing(Session.getLecture().getId())) {
                        Session.setUser(UserRequest
                                .addUser(name, Session.getLecture().getId(), false));
                        resetFastPoll();

                        // Load student screen
                        Session.timer = new Timer();
                        StudentScreenController.setLeft(false);
                        StudentScreenController.init(Session.getLecture(), Session.getUser());
                        Parent tableViewParent = FXMLLoader.load(getClass()
                                .getClassLoader().getResource("studentScreen.fxml"));
                        Scene tableViewScene = new Scene(tableViewParent);
                        Stage window = (Stage) Stage.getWindows().get(0);
                        window.setScene(tableViewScene);
                        window.show();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(
                                "The start time of the lecture \""
                                        + Session.getLecture().getName() + "\" is "
                                        + (Session.getLecture().getStartTime() == null
                                        ? "not defined, but the lecture exists."
                                        : Session.getLecture().getStartTime())
                                        + "\nWait for the lecturer to start the lecture");
                        alert.showAndWait();
                        link.setPromptText("Enter the lecture link");
                        username.setPromptText("Username");
                        return;
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("ERROR");
                    alert.setHeaderText("The link you have provided does not exist");
                    alert.setGraphic(new ImageView(getClass().getClassLoader()
                            .getResource("images/error.png").toString()));
                    alert.showAndWait();
                }

            } catch (Exception e) {
                // Show the error
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ERROR");
                alert.setHeaderText("The link you have provided does not exist");
                alert.setGraphic(new ImageView(getClass().getClassLoader()
                        .getResource("images/error.png").toString()));
                alert.showAndWait();
                link.setPromptText("Enter the lecture link");
                username.setPromptText("Username");
                return;
            }

        } else {
            //1 or Both fields are empty.
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Empty Input");
            alert.setHeaderText("Please fill in all the information");
            alert.showAndWait();
            link.setPromptText("Lecture Link");
            username.setPromptText("Username");
        }
    }

    /**
     * Schedule method.
     *
     * @param event - Update this text necessary to pass the checkstyle
     * @throws IOException - Update this text necessary to pass the checkstyle
     */
    public void scheduleLinkClicked(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass()
                .getClassLoader().getResource("scheduleScreen.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    /**
     * Method that allows a user to start a lecture without scheduling it.
     */
    public void startLectureDirectly(ActionEvent actionEvent) throws Exception {
        TextInputDialog temp = new TextInputDialog();
        temp.setHeaderText("Type in the lecture name");
        //temp.showAndWait();
        Optional<String> okClicked = temp.showAndWait();
        String lectureName = temp.getEditor().getText();

        if (okClicked.isPresent()) {
            TextInputDialog temp2 = new TextInputDialog();
            temp2.setHeaderText("Type in your name");
            Optional<String> okClicked2 = temp2.showAndWait();
            String moderatorAlias = temp2.getEditor().getText();

            if (okClicked2.isPresent()) {
                if (moderatorAlias.isEmpty() || lectureName.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("EMPTY FIELDS");
                    alert.setHeaderText("You have not provided all necessary information"
                            + ", please try again.");
                    alert.showAndWait();
                } else {
                    long id = LectureRequest.getGeneratedLectureId();
                    String pubLink = LectureRequest.getGeneratedToken();
                    String modLink = LectureRequest.getGeneratedToken();

                    Lecture lecture = new Lecture(
                            id,
                            lectureName,
                            modLink,
                            pubLink,
                            true
                    );
                    LectureRequest.postDirectLecture(lecture);

                    try {
                        PollRequest.addSpeedPoll(id, id);
                        PollOptionRequest.addSpeedOptions(id);

                    } catch (Exception e) {
                        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                        alert2.setTitle("Unexpected error");
                        alert2.setHeaderText("Lecture could not be saved on the server"
                                + " because of the poll, "
                                + "please try again");
                        alert2.showAndWait();
                        return;
                    }

                    Session.setLecture(lecture);

                    //Log in as moderator
                    Session.setUser(UserRequest
                            .addUser(moderatorAlias, Session.getLecture().getId(), true));

                    // Create the lecture speed poll
                    resetFastPoll();

                    // Show the public and the moderator token
                    TextArea links = new TextArea("Join as a moderator with: "
                            + modLink + "\n" + "Join as a student with: " + pubLink);
                    links.setEditable(false);
                    GridPane pane = new GridPane();
                    pane.add(links, 0, 0);

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("COPY THE LECTURE LINKS");
                    alert.getDialogPane().setContent(pane);
                    alert.showAndWait();

                    // revert everything
                    if (alert.getResult() == ButtonType.CANCEL) {
                        PollRequest.deleteCurrentPoll(id);
                        PollOptionRequest.deleteCurrentPoll(id);
                        UserRequest.deleteUser(Session.getUser());
                        Session.reset();
                    } else {

                        // Load moderator screen
                        Session.timer = new Timer();
                        ModeratorScreenController.init(Session.getLecture(), Session.getUser());
                        Parent tableViewParent = FXMLLoader.load(getClass()
                                .getClassLoader().getResource("moderatorScreen.fxml"));
                        Scene tableViewScene = new Scene(tableViewParent);
                        Stage window = (Stage) startDirect.getScene().getWindow();
                        window.setScene(tableViewScene);
                        window.show();
                    }
                }
            }
        }
    }

    /**
     * On enter submit form.
     */
    public void onEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            buttonEnterClicked();
        }
    }

    /**
     *  This functions loads the help screen.
     * @param event Needed for checkstyle
     * @throws Exception Needed for checkstyle
     */
    public void loadHelpPage(ActionEvent event) throws Exception {

        Parent tableViewParent = FXMLLoader.load(getClass()
                .getClassLoader().getResource("helpPage.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();

    }

}

