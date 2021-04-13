package nl.tudelft.oopp.prod.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import nl.tudelft.oopp.prod.communication.LectureRequest;
import nl.tudelft.oopp.prod.communication.PollOptionRequest;
import nl.tudelft.oopp.prod.communication.PollRequest;
import nl.tudelft.oopp.prod.communication.QuestionRequest;
import nl.tudelft.oopp.prod.communication.UserRequest;
import nl.tudelft.oopp.prod.objects.Customization;
import nl.tudelft.oopp.prod.objects.Lecture;
import nl.tudelft.oopp.prod.objects.PollOption;
import nl.tudelft.oopp.prod.objects.Question;
import nl.tudelft.oopp.prod.objects.Session;
import nl.tudelft.oopp.prod.objects.User;

public class StudentScreenController {

    @FXML
    private Label lectureName;
    @FXML
    private Label alias;
    @FXML
    private TextArea questionArea;
    @FXML
    private GridPane questionGrid;
    @FXML
    private ListView<String> userList;
    @FXML
    private CheckBox speedOne;
    @FXML
    private CheckBox speedTwo;
    @FXML
    private CheckBox speedThree;
    @FXML
    private Label fastLabel;
    @FXML
    private Label rightLabel;
    @FXML
    private Label slowLabel;
    @FXML
    private Button submit;
    @FXML
    private TextArea editArea;
    @FXML
    private HBox editPane;
    @FXML
    private TitledPane pollpaneheader;
    @FXML
    private GridPane pollAnswers;
    @FXML
    private TitledPane answeredheader;
    @FXML
    private Label pollQuestion;
    @FXML
    private Button submitPollBtn;
    @FXML
    private Button ask;

    @FXML
    private TitledPane participantsLabel;

    @FXML
    private ListView<String> answeredQuestionsList;

    @FXML
    private VBox background;

    private static boolean firstQuestion = true;
    private static Lecture lecture;
    private static User user;
    private static boolean left = false;
    private static boolean submitted = false;
    //private static Timer timer = new Timer();

    //How should the questions be sorted:
    //  0 - By number of votes
    //  1 - HOT - Number of votes and time relevance
    //  2 - NEW - Time
    //  3+ - Future Improvement
    private static int sortingType = 0;


    /**
     * This sets associated information.
     */
    @FXML
    public void initialize() {
        Session.closeAppEvent();
        this.lectureName.setStyle("-fx-font-size: 24");
        this.lectureName.setStyle("-fx-background-color: rgb(0,0,0,0.314);"
                + " -fx-background-radius: 10");
        this.alias.setStyle("-fx-font-size: 24");
        this.alias.setStyle("-fx-background-color: rgb(0,0,0,0.314); "
                + "-fx-background-radius: 10");
        this.lectureName.setText(lecture.getName());
        this.alias.setText(user.getAlias());

        this.participantsLabel.setText("Participants (0)");

        this.editArea.setEditable(false);
        this.submit.setDisable(true);
        this.editPane.setVisible(false);

        String pic = (Customization.backgroundPath != null)
                ? Customization.backgroundPath : "images/alicante.jpg";
        background.setStyle("-fx-background-image: url('" + pic + "');-fx-background-size: cover;");

        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (checkLectureEnded()) {

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Lecture Ended");
                        alert.setHeaderText("This lecture has been ended");
                        Session.timer.cancel();
                        Session.timer.purge();
                        alert.showAndWait();

                        try {
                            switchScene();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else if (checkBanned()) {

                        Session.timer.cancel();
                        Session.timer.purge();
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("You have been banned from this lecture");
                        alert.setHeaderText("You have been banned from this lecture. "
                                + "Return to the home screen.");
                        alert.showAndWait();
                        leave();

                    } else if (left) {
                        Session.timer.cancel();
                        Session.timer.purge();
                    } else {

                        try {
                            refreshPoll();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        getAllQuestions();
                        refreshBackground();

                        try {
                            refreshPacePoll();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        showUsers();

                        if (checkRateLimited()) {

                            if (!(Session.getLecture().isRateLimited())) {
                                Session.getLecture().setRateLimited(true);
                                enableRateLimit();

                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Moderators have enabled rate limiting!");
                                alert.setHeaderText("You can only ask one"
                                        + " question every 60 seconds!");
                                alert.showAndWait();

                            }
                        } else if (Session.getLecture().isRateLimited()) {
                            Session.getLecture().setRateLimited(false);
                            disableRateLimit();

                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Moderators have disabled rate limiting!");
                            alert.setHeaderText("You can ask as many questions as you want!");
                            alert.showAndWait();

                        }

                        if (Session.getCurrentPoll() == null) {
                            submitPollBtn.setVisible(false);
                        }
                    }
                });
            }
        };
        Session.timer.scheduleAtFixedRate(tt, 0, 2000);
    }

    /**
     * This method changes the left status of the student.
     *
     * @param b the new boolean status of the left parameter
     */
    public static void setLeft(boolean b) {
        left = b;
    }

    /**
     * Checks if the current lecture has ended.
     *
     * @return boolean, true if the lecture has ended, false if the lecture is ongoing.
     */
    public boolean checkLectureEnded() {

        Lecture currentLecture = null;

        try {
            currentLecture = LectureRequest.getLecture(lecture.getPublicLink());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (currentLecture.isOngoing()) {
            return false;
        } else {
            return true;
        }

    }

    /**
     * This method checks if the user has been banned.
     *
     * @return boolean if the user has been banned.
     */
    public boolean checkBanned() {

        boolean banned = false;

        try {
            banned = UserRequest.checkIfUserBanned(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (banned) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * This methods checks if the current lecture is rate limited.
     * @return boolean
     */
    public boolean checkRateLimited() {

        boolean rateLimited = false;

        try {
            rateLimited = LectureRequest.getRateLimited(Session.getLecture());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (rateLimited) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * This method initialises the screen.
     *
     * @param lect  Lecture associated lecture object
     * @param login User associated
     */
    public static void init(Lecture lect, User login) {
        lecture = lect;
        user = login;
    }

    /**
     * This saves a question onto the database.
     * It appends the question to the current list of questions.
     */
    public void askQuestion() {
        try {
            Question myQuestion = new Question(questionArea.getText(),
                    lecture,
                    user.getId(),
                    user.getAlias(),
                    -1,
                    "");

            int seconds = HotComparator.passedTime();
            myQuestion.setSeconds(seconds);

            if (myQuestion.getText().length() > 200) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Invalid Input");
                alert.setHeaderText("Question length exceeds acceptable word count!");
                alert.showAndWait();
                questionArea.clear();
                return;
            }



            Question question = QuestionRequest.askQuestion(myQuestion);
            myQuestion.setId(question.getId());

            initialiseQuestion(questionGrid, myQuestion, true);
            questionArea.clear();

            if (Session.getLecture().isRateLimited()) {

                ask.setVisible(false);

                Timer buttonTimer = new Timer();
                buttonTimer.schedule(new TimerTask() {

                    @Override
                    public void run() {

                        ask.setVisible(true);

                    }
                }, 60000);
            }

        } catch (Exception e) {
            e.getCause();
            questionArea.setPromptText("Question submitted unsuccessfully, please try again");
        }

    }

    /**
     * Handles the seeQuestions button event.
     * After receiving the list, questions will be appended.
     */
    public void getAllQuestions() {
        try {
            questionGrid.getChildren().clear();
            //Retrieve questions from Server
            List<Question> requestResult = QuestionRequest
                    .getAllQuestionsByLectureId(lecture.getIdString());

            //Sort the questions according to the preferred sort
            List<Question> result = Sorter.sort(requestResult, sortingType);


            for (int i = 0; i < result.size(); i++) {
                initialiseQuestion(questionGrid, result.get(i), false);
            }

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText("This lecture has no questions yet");
        }
    }

    /**
     * This is a helper method to initialise a Question box.
     *
     * @param grid the Grid that needs adjusting
     */
    private void initialiseQuestion(GridPane grid, Question question, boolean ask) {
        if (!firstQuestion) {
            //not the first question
            Label text = new Label();
            text.setWrapText(true);

            if (ask) {
                text.setText(questionArea.getText() + "\n" + " asked by " + user.getAlias());
                text.setStyle("-fx-font-size: " + Customization.fontSize);
                text.setTextFill(Customization.fontColor);
            } else {
                text.setText(question.getText() + "\n" + " asked by " + question.getUserAlias());
                text.setStyle("-fx-font-size: " + Customization.fontSize);
                text.setTextFill(Customization.fontColor);
            }

            text.setPadding(new Insets(5, 0, 7, 17));

            GridPane textContainer = new GridPane();
            textContainer.add(text, 0, 0);

            textContainer.setStyle("-fx-background-color: rgb("
                    + Customization.cellColor.getRed() * 255
                    + ","
                    + Customization.cellColor.getGreen() * 255
                    + ","
                    + Customization.cellColor.getBlue() * 255
                    + ","
                    + Customization.cellColor.getOpacity()
                    + "); ");

            int r = grid.getRowCount() + 1;
            grid.add(textContainer, 0, r);

            Label textReply = new Label(question.getAnswer());

            textReply.setWrapText(true);
            textReply.setStyle("-fx-font-size: " + Customization.fontSize);
            textReply.setTextFill(Customization.fontColor);
            textReply.setPadding(new Insets(5, 0, 7, 17));

            GridPane replyContainer = new GridPane();
            replyContainer.add(textReply, 0, 0);


            replyContainer.setStyle("-fx-background-color: rgb("
                    + Customization.cellColor.getRed() * 255
                    + ","
                    + Customization.cellColor.getGreen() * 255
                    + ","
                    + Customization.cellColor.getBlue() * 255
                    + ","
                    + Customization.cellColor.getOpacity()
                    + "); ");


            int j = r;
            grid.add(replyContainer, 1, j);

            j++;

            GridPane buttons = new GridPane();

            Button vote = new Button("VOTE");
            buttons.add(vote, 0, 0);

            // User did not vote already, add the event
            if (!question.getVotedUsers().contains(user.getAlias())) {
                vote.setOnAction(new VoteEvent(question, vote, user.getAlias()));
            } else {
                vote.setDisable(true);
            }

            if (question.getUserId() == user.getId()) {
                Button edit = new Button("EDIT");
                edit.setStyle("-fx-background-color: #ea4492");
                buttons.add(edit, 1, 0);

                edit.setOnAction(new EditEvent(question, editArea, submit, text, editPane));
                Button delete = new Button("DELETE");
                delete.setOnAction(new DeleteEvent(question.getId(), text, vote, edit, delete));
                buttons.add(delete, 2, 0);
            }

            buttons.setHgap(5);
            buttons.setPadding(new Insets(10, 0, 5, 5));

            grid.add(buttons, 0, j - 2);

            j++;

        } else {
            //is the first question
            Label text = new Label();
            text.setWrapText(true);

            if (ask) {
                text.setText(questionArea.getText() + "\n" + " asked by " + user.getAlias());
                text.setStyle("-fx-font-size: 14");
                text.setTextFill(Paint.valueOf("White"));
            } else {
                text.setText(question.getText() + "\n" + " asked by " + question.getUserAlias());
                text.setStyle("-fx-font-size: 14");
                text.setTextFill(Paint.valueOf("White"));
            }

            text.setPadding(new Insets(5, 0, 7, 17));

            GridPane textContainer = new GridPane();
            textContainer.add(text, 0, 0);

            textContainer.setStyle("-fx-background-color: rgb(0,0,0,0.314);");

            grid.add(textContainer, 0, 0);

            GridPane buttons = new GridPane();

            Button vote = new Button("VOTE");
            buttons.add(vote, 0, 0);

            // User did not vote already
            if (!question.getVotedUsers().contains(user.getAlias())) {
                vote.setOnAction(new VoteEvent(question, vote, user.getAlias()));
            } else {
                vote.setDisable(true);
            }

            if (question.getUserId() == user.getId()) {
                Button edit = new Button("EDIT");
                edit.setStyle("-fx-background-color: #ea4492");
                buttons.add(edit, 1, 0);
                edit.setOnAction(new EditEvent(question, editArea, submit, text, editPane));

                Button delete = new Button("DELETE");
                delete.setOnAction(new DeleteEvent(question.getId(), text, vote, edit, delete));
                buttons.add(delete, 2, 0);
            }

            grid.add(buttons, 0, 0);

            afterFirstQuestion();
        }
    }

    /**
     * This resets the check
     * for whether or not it is the first question.
     */
    private static void afterFirstQuestion() {
        firstQuestion = false;
    }

    /**
     * This method shows the list
     * of users in this lecture.
     */
    public void showUsers() {

        List<String> userAliases = null;

        try {
            userAliases = LectureRequest.getAllUserAliases(lecture.getId())
                    .stream()
                    .map(User::getAlias)
                    .collect(Collectors.toList());;
        } catch (Exception e) {
            e.printStackTrace();
        }

        userList.getItems().clear(); //empty all list

        for (String s : userAliases) {
            userList.getItems().add(s); //add all (now we can see who left too)
        }
        this.participantsLabel.setText("Participants (" + userList.getItems().size() + ")");
    }

    /**
     * This shows the list of answered questions.
     */
    public void showAnsweredQuestions() {
        List<String> answeredQuestions = null;

        try {
            answeredQuestions = QuestionRequest
                    .retrieveAllAnsweredQuestionsInLecture(lecture.getIdString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (String s : answeredQuestions) {
            if (!(answeredQuestionsList.getItems().contains(s))) {
                answeredQuestionsList.getItems().add(s);
            }
        }
    }

    /**
     * refreshes the lecture speed poll.
     */
    public void refreshPacePoll() throws Exception {
        checkLectureEnded();
        fastLabel.setText(String.valueOf(PollOptionRequest
                .getVotes(Session.getLecture().getId(), 1)));
        rightLabel.setText(String.valueOf(PollOptionRequest
                .getVotes(Session.getLecture().getId(), 2)));
        slowLabel.setText(String.valueOf(PollOptionRequest
                .getVotes(Session.getLecture().getId(), 3)));
    }

    /**
     * handles a click on the fast option.
     */
    public void fastOption() throws Exception {
        if (Session.getClickedOne()) {
            PollOptionRequest.downVote(0);
            Session.setClickedOne(false);
        } else {
            PollOptionRequest.upVote(0);
            Session.setClickedOne(true);
            speedOne.setSelected(true);
            if (Session.getClickedTwo()) {
                Session.setClickedTwo(false);
                speedTwo.setSelected(false);
                PollOptionRequest.downVote(1);
            } else if (Session.getClickedThree()) {
                Session.setClickedThree(false);
                speedThree.setSelected(false);
                PollOptionRequest.downVote(2);
            }
        }
        refreshPacePoll();
    }

    /**
     * handles a click on the right option.
     */
    public void rightOption() throws Exception {
        if (Session.getClickedTwo()) {
            speedTwo.setSelected(false);
            PollOptionRequest.downVote(1);
            Session.setClickedTwo(false);
        } else {
            PollOptionRequest.upVote(1);
            Session.setClickedTwo(true);
            if (Session.getClickedOne()) {
                Session.setClickedOne(false);
                speedOne.setSelected(false);
                PollOptionRequest.downVote(0);
            } else if (Session.getClickedThree()) {
                Session.setClickedThree(false);
                speedThree.setSelected(false);
                PollOptionRequest.downVote(2);
            }
        }
        refreshPacePoll();
    }

    /**
     * handles a click on the slow option.
     */
    public void slowOption() throws Exception {
        if (Session.getClickedThree()) {
            speedThree.setSelected(false);
            PollOptionRequest.downVote(2);
            Session.setClickedThree(false);
        } else {
            PollOptionRequest.upVote(2);
            Session.setClickedThree(true);
            if (Session.getClickedOne()) {
                Session.setClickedOne(false);
                speedOne.setSelected(false);
                PollOptionRequest.downVote(0);
            } else if (Session.getClickedTwo()) {
                Session.setClickedTwo(false);
                speedTwo.setSelected(false);
                PollOptionRequest.downVote(1);
            }
        }
        refreshPacePoll();
    }

    /**
     * This allows user to return to home page.
     */
    public void leave() {
        Parent tableViewParent = null;
        try {
            tableViewParent = FXMLLoader.load(getClass()
                    .getClassLoader().getResource("homeScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Session.timer.cancel();
        Session.timer.purge();

        try {
            UserRequest.deleteUser(Session.getUser());
            if (Session.getClickedOne()) {
                PollOptionRequest.downVote(0);
            }
            if (Session.getClickedTwo()) {
                PollOptionRequest.downVote(1);
            }
            if (Session.getClickedThree()) {
                PollOptionRequest.downVote(2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Session.reset();

        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) Stage.getWindows().get(0);
        window.setScene(tableViewScene);
        window.show();
        left = true;

    }

    /**
     * Switch the scene to the home screen.
     */
    public void switchScene() throws Exception {
        Parent tableViewParent = null;
        try {
            tableViewParent = FXMLLoader.load(getClass()
                    .getClassLoader().getResource("homeScreen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Session.timer.cancel();
        Session.timer.purge();
        Session.reset();

        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) Stage.getWindows().get(0);
        window.setScene(tableViewScene);
        window.show();
    }


    /**
     * Refreshes the current lecture poll.
     */
    public void refreshPoll() throws Exception {
        refreshPollQuestion();
        refreshPollOptions();
    }

    /**
     * Refreshes the current lecture poll question.
     */
    public void refreshPollQuestion() throws Exception {

        if (pollpaneheader.expandedProperty().get()) {
            collapseAnswered();
        }

        String question = PollRequest.getPollQuestion(Session.getLecture().getId());

        if (!question.equals(pollQuestion.getText())) {
            if (pollQuestion.getText().isEmpty() && !question.isEmpty()) {
                pollQuestion.setText(question);
                if (Session.getCurrentPoll().isActive()) {
                    submitPollBtn.setVisible(true);
                }
            }

            if (!pollQuestion.getText().isEmpty() && !question.isEmpty()) {
                pollQuestion.setText(question);
                submitPollBtn.setVisible(true);
                submitPollBtn.setDisable(false);
                if (!Session.getCurrentPoll().isActive()) {
                    submitPollBtn.setDisable(true);
                }
            }

            if (!pollQuestion.getText().isEmpty() && question.isEmpty()) {
                pollQuestion.setText(question);
                submitPollBtn.setVisible(false);
                submitPollBtn.setDisable(false);
            }
        }

        if (Session.getCurrentPoll() != null && !Session.getCurrentPoll().isActive()) {
            submitPollBtn.setDisable(true);
            getPollResults(Session.getCurrentPoll().getId());
        }
    }

    private void getPollResults(long id) throws Exception {
        refreshPollOptions();
        ObservableList<Node> choices = pollAnswers.getChildren();
        for (Node choice : choices) {
            for (int i = 0; i < 10; i++) {
                if (pollAnswers.getRowIndex(choice) == i
                        && pollAnswers.getColumnIndex(choice) == 1) {
                    Label label = (Label) choice;
                    label.setText(PollOptionRequest.getPollVotes(label.getId()));
                }
                if (pollAnswers.getRowIndex(choice) == i
                        && pollAnswers.getColumnIndex(choice) == 0) {
                    CheckBox option = (CheckBox) choice;
                    option.setDisable(true);
                }
            }
        }
    }

    private void refreshPollOptions() throws Exception {
        PollRequest.getPollQuestion(Session.getLecture().getId());
        if (Session.getCurrentPoll() != null) {
            List<PollOption> current = PollOptionRequest
                    .getListOfOptions(Session.getCurrentPoll().getId());
            if (current.size() != Session.getNumberOfPollOptions()) {
                for (int i = Session.getNumberOfPollOptions(); i < current.size(); i++) {

                    PollOption newOption = current.get(i);
                    CheckBox option = new CheckBox();

                    option.setText(newOption.getText());

                    Label pollOptionVotes = new Label();
                    pollOptionVotes.setId(String.valueOf(newOption.getId()));

                    option.setOnAction(new PollVoteEvent(newOption.getId(),
                            option, pollOptionVotes));

                    pollAnswers.add(option, 0, i);
                    pollAnswers.add(pollOptionVotes, 1, i);

                    Session.setNumberOfPollOptions(current.size());

                    submitPollBtn.setDisable(false);
                }
            }
        } else {
            pollAnswers.getChildren().clear();
            Session.setNumberOfPollOptions(0);
        }
    }


    public void collapsePoll() {
        pollpaneheader.setExpanded(false);
    }

    public void collapseAnswered() {
        answeredheader.setExpanded(false);
    }

    public void sortByTop(ActionEvent actionEvent) {
        sortingType = 0;
    }

    public void sortByHot(ActionEvent actionEvent) {
        sortingType = 1;
    }

    public void sortByNew(ActionEvent actionEvent) {
        sortingType = 2;
    }

    /**
     * This disables further voting of an option.
     */
    public void submitPollAnswers(ActionEvent actionEvent) {
        submitted = true;
        submitPollBtn.setDisable(true);
        ObservableList<Node> choices = pollAnswers.getChildren();
        for (Node choice : choices) {
            for (int i = 0; i < 10; i++) {
                if (pollAnswers.getRowIndex(choice) == i
                        && pollAnswers.getColumnIndex(choice) == 0) {
                    CheckBox option = (CheckBox) choice;
                    option.setDisable(true);
                }
            }
        }
    }

    /**
     * Pops menu to customize GUI.
     */
    public void popCustomization(ActionEvent actionEvent) {

        if (Customization.alreadyOpenWindow) {
            try {
                Stage stage = (Stage) Stage.getWindows().get(1);
                stage.close();
            } catch (Exception e) {
                Customization.alreadyOpenWindow = false;
            }
        }
        final Stage primaryStage = new Stage();
        primaryStage.setTitle("Customize display");
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/customize.fxml");
        loader.setLocation(xmlUrl);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        Customization.alreadyOpenWindow = true;
    }

    /**
     * Updates background.
     */
    public void refreshBackground() {
        if (Customization.updateBackground) {
            background.setStyle("-fx-background-image: url('" + Customization.backgroundPath + "')"
                    + ";-fx-background-size: cover;");
            Customization.updateBackground = false;
        }
    }

    public void enableRateLimit() {



    }

    public void disableRateLimit() {



    }

}
