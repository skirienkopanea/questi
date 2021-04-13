package nl.tudelft.oopp.prod.controllers;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import nl.tudelft.oopp.prod.communication.LectureRequest;
import nl.tudelft.oopp.prod.communication.PollOptionRequest;
import nl.tudelft.oopp.prod.communication.PollRequest;
import nl.tudelft.oopp.prod.communication.QuestionRequest;
import nl.tudelft.oopp.prod.communication.UserRequest;
import nl.tudelft.oopp.prod.objects.Customization;
import nl.tudelft.oopp.prod.objects.Lecture;
import nl.tudelft.oopp.prod.objects.Poll;
import nl.tudelft.oopp.prod.objects.PollOption;
import nl.tudelft.oopp.prod.objects.Question;
import nl.tudelft.oopp.prod.objects.Session;
import nl.tudelft.oopp.prod.objects.User;


public class ModeratorScreenController {

    @FXML
    private ListView<String> answeredQuestionsList;
    @FXML
    private Label lectureName;
    @FXML
    private Label userName;
    @FXML
    private GridPane questionGrid;
    @FXML
    private ListView<String> userList;
    @FXML
    private Label pollQuestion;
    @FXML
    private Button pollOptionBtn;
    @FXML
    private Button pollDeleteBtn;
    @FXML
    private GridPane pollAnswers;
    @FXML
    private GridPane sidePane;
    @FXML
    private CheckBox speedOne;
    @FXML
    private MenuItem lecturerViewButton;
    @FXML
    private MenuItem moderatorViewButton;
    @FXML
    private MenuItem enableRateLimitButton;
    @FXML
    private MenuItem disableRateLimitButton;
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
    private TextArea editArea;
    @FXML
    private Button submit;
    @FXML
    private Pane editPane;
    @FXML
    private Button reply;
    @FXML
    private Pane replyPane;
    @FXML
    private TextArea replyArea;
    @FXML
    private TextField moderatorLink;
    @FXML
    private TextField publicLink;
    @FXML
    private Button closeBtn;
    @FXML
    private TitledPane participantsLabel;
    @FXML
    private TitledPane pollpaneheader;
    @FXML
    private TitledPane answeredheader;
    @FXML
    private VBox background;
    @FXML
    private MenuItem logDownloadButton;

    private static Lecture lecture;
    private static User user;

    //How should the questions be sorted:
    //  0 - By number of votes
    //  1 - HOT - Number of votes and time relevance
    //  2 - NEW - Time
    //  3+ - Future Improvement
    private static int sortingType = 0;

    private static long startTime = 0;

    private static boolean started = false;


    /**
     * This initialises the moderator screen.
     */
    @FXML
    public void initialize() {
        Session.closeAppEvent(); //window close listener

        this.lectureName.setText(lecture.getName());
        this.userName.setText(user.getAlias());

        this.moderatorLink.setText(lecture.getModeratorLink());
        this.publicLink.setText(lecture.getPublicLink());
        participantsLabel.setExpanded(true);
        this.participantsLabel.setText("Participants (0)");


        this.editArea.setEditable(false);
        this.submit.setDisable(true);
        this.editPane.setVisible(false);

        this.replyArea.setEditable(false);
        this.reply.setDisable(true);
        this.replyPane.setVisible(false);

        this.moderatorViewButton.setVisible(false);
        this.disableRateLimitButton.setVisible(false);

        String pic = (Customization.backgroundPath != null)
                ? Customization.backgroundPath : "images/mountains.jpg";
        background.setStyle("-fx-background-image: url('" + pic + "');-fx-background-size: cover;");

        Timer timer = new Timer();

        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (started) {
                        if (checkLectureEnded()) {
                            Session.timer.cancel();
                            Session.timer.purge();
                        }
                    }
                    getAllQuestions();
                    showUsers();
                    refreshBackground();
                    try {
                        refreshPoll();
                        refreshPollOptions();
                        showAnsweredQuestions();
                        refreshPacePoll();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        };

        Session.timer.scheduleAtFixedRate(tt, 0, 2000);

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

            int j = questionGrid.getRowCount();

            for (int i = 0; i < result.size(); i++) {

                if (!result.get(i).isAnswered() && !result.get(i).getHidden()) {

                    Label text = new Label(result.get(i).toString()
                            + "\n"
                            + "asked by "
                            + result.get(i).getUserAlias());

                    text.setStyle("-fx-font-size: " + Customization.fontSize);
                    text.setWrapText(true);
                    text.setTextFill(Customization.fontColor);
                    text.setPadding(new Insets(5, 0, 7, 17));

                    GridPane textContainer = new GridPane();
                    textContainer.add(text, 0, 0);

                    //System.out.println(result.get(i).getReplierAlias());
                    textContainer.setStyle("-fx-background-color: rgb("
                            + Customization.cellColor.getRed() * 255
                            + ","
                            + Customization.cellColor.getGreen() * 255
                            + ","
                            + Customization.cellColor.getBlue() * 255
                            + ","
                            + Customization.cellColor.getOpacity()
                            + "); ");

                    questionGrid.add(textContainer, 0, j);


                    Label textReply = new Label(result.get(i).getAnswer());
                    textReply.setStyle("-fx-font-size: " + Customization.fontSize);
                    textReply.setWrapText(true);
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

                    questionGrid.add(replyContainer, 1, j);

                    j++;

                    //isTyping
                    if (result.get(i).getReplierId() > 0) {
                        Label typer = new Label(result.get(i).getReplierAlias()
                                + " is replying...");
                        typer.setStyle("-fx-font-size: " + Customization.fontSize);
                        typer.setWrapText(true);
                        typer.setTextFill(Customization.fontColor);
                        typer.setPadding(new Insets(20, 0, 0, 5));
                        questionGrid.add(typer, 1, j - 2);
                    }


                    // Buttons
                    GridPane buttons = new GridPane();

                    Button answer = new Button("ANSWER");
                    answer.setOnAction(new MarkAnswerEvent(result.get(i), text, answer));
                    buttons.add(answer, 0, 0);

                    Button votes = new Button(result.get(i).getVotes() + " UPVOTES");
                    votes.setStyle("-fx-background-color: orange");
                    buttons.add(votes, 1, 0);

                    Button edit = new Button("EDIT");
                    edit.setStyle("-fx-background-color: #ea4492");
                    buttons.add(edit, 2, 0);
                    edit.setOnAction(new EditEvent(result.get(i),
                            editArea,
                            submit,
                            text,
                            editPane));


                    Button replyButton = new Button("REPLY");
                    replyButton.setStyle("-fx-background-color: #bd93f9");
                    buttons.add(replyButton, 3, 0);
                    replyButton.setOnAction(new ReplyEvent(result.get(i),
                            replyArea,
                            reply,
                            textReply,
                            replyPane));

                    Button delete = new Button("DELETE");
                    Button hide = new Button("HIDE");

                    delete.setStyle("-fx-background-color: #ff5555;");
                    buttons.add(delete, 4, 0);
                    delete.setOnAction(new ModeratorDeleteEvent(result.get(i).getId(),
                            text,
                            votes,
                            answer,
                            edit,
                            hide,
                            delete));


                    hide.setStyle("-fx-background-color: #ff9190");
                    buttons.add(hide, 5, 0);
                    hide.setOnAction(new HideEvent(result.get(i), text,
                            answer, votes, edit, delete, replyButton, hide));
                    buttons.setHgap(5);
                    buttons.setPadding(new Insets(10, 0, 5, 5));

                    questionGrid.add(buttons, 0, j - 2);

                    j++;

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText("This lecture has no questions yet");
        }
    }

    /**
     * This shows the list of users
     * in this lecture.
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
     * This allows user to return to home page.
     */
    public void leave() throws Exception {
        Session.timer.cancel();
        Session.timer.purge();

        UserRequest.deleteUser(Session.getUser());

        Session.reset();

        Stage window = (Stage) Stage.getWindows().get(0);

        Scene tableViewScene = new Scene(FXMLLoader.load(getClass()
                .getClassLoader().getResource("homeScreen.fxml")));
        window.setScene(tableViewScene);
        window.show();
    }

    /**
     * This method displays all answered questions.
     *
     * @throws NullPointerException Possible NullPointerException.
     */
    public void showAnsweredQuestions() throws NullPointerException {

        if (answeredheader.expandedProperty().get()) {
            collapsePoll();
        }

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
     * handles the button to create a new poll.
     */
    public void createPoll() throws Exception {
        refreshPoll();
        if (!pollQuestion.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Another moderator already created a poll");
            alert.showAndWait();
            return;
        }
        if (pollQuestion.getText().isEmpty()) {
            TextInputDialog input = new TextInputDialog();
            input.setTitle("New poll");
            input.setHeaderText("Type in the poll question");
            Optional<String> okClicked = input.showAndWait();

            if (okClicked.isPresent()) {
                pollQuestion.setText(input.getEditor().getText());
                if (!pollQuestion.getText().isEmpty()) {
                    pollOptionBtn.setVisible(true);
                    pollDeleteBtn.setVisible(true);
                    closeBtn.setVisible(true);
                    Poll poll = PollRequest.addPoll(lecture.getId(), pollQuestion.getText());
                    Session.setCurrentPoll(poll);
                    Session.setNumberOfPollOptions(0);
                } else {
                    Alert temp = new Alert(Alert.AlertType.ERROR);
                    temp.setTitle("Empty");
                    temp.setHeaderText("No question has been filled");
                    temp.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("There is an active poll already");
                alert.setGraphic(new ImageView(getClass().getClassLoader()
                        .getResource("images/error.png").toString()));
                alert.showAndWait();
            }
        }
    }


    /**
     * Add a new poll option to the current lecture poll.
     */
    public void addPollOption() throws Exception {

        if (Session.getCurrentPoll() != null) {

            List<PollOption> current = PollOptionRequest
                    .getListOfOptions(Session.getCurrentPoll().getId());
            if (current.size() != Session.getNumberOfPollOptions()) {

                Alert temp = new Alert(Alert.AlertType.INFORMATION);
                temp.setTitle("Empty");
                temp.setHeaderText("Someone added a new poll option");
                temp.showAndWait();

                for (int i = Session.getNumberOfPollOptions(); i < current.size(); i++) {
                    CheckBox option = new CheckBox();
                    option.setText(current.get(i).getText());
                    option.setStyle(" -fx-font: Fira Code Regular; ");
                    pollAnswers.add(option, 0, i);
                }
                Session.setNumberOfPollOptions(current.size());
            }
        }

        int amount = PollOptionRequest.getNumberOfOptions(Session.getCurrentPoll().getId());

        if (amount < 10) {

            TextInputDialog input = new TextInputDialog();
            input.setTitle("New pollOption");
            input.setHeaderText("Type in the poll answer");
            Optional<String> okClicked = input.showAndWait();

            if (okClicked.isPresent()) {
                String id = PollOptionRequest.getNewPollOptionId() + String.valueOf(amount);
                long optionId = Long.parseLong(id);

                CheckBox option = new CheckBox();
                PollOption newOption = new PollOption(optionId, Session.getCurrentPoll()
                        .getId(), input.getEditor().getText(), 0);

                PollOptionRequest.save(newOption);

                option.setText(input.getEditor().getText());

                Label pollOptionVotes = new Label();
                pollOptionVotes.setId(String.valueOf(optionId));

                option.setOnAction(new PollVoteEvent(optionId, option, pollOptionVotes));


                pollAnswers.add(option, 0, amount);
                pollAnswers.add(pollOptionVotes, 1, amount);

                Session.incNumberOfPollOptions();
            }

        } else {

            Alert temp = new Alert(Alert.AlertType.INFORMATION);
            temp.setTitle("Maximum Options Added");
            temp.setHeaderText("Can not add more than 10 choices.");
            temp.showAndWait();

        }
    }

    /**
     * Refreshes the current lecture poll.
     */
    public void refreshPoll() throws Exception {

        if (pollpaneheader.expandedProperty().get()) {
            collapseAnswered();
        }

        String question = PollRequest.getPollQuestion(Session.getLecture().getId());

        if (!question.equals(pollQuestion.getText())) {
            if (pollQuestion.getText().isEmpty() && !question.isEmpty()) {
                pollQuestion.setText(question);
                if (Session.getCurrentPoll().isActive()) {
                    pollOptionBtn.setVisible(true);
                }
                pollDeleteBtn.setVisible(true);
                closeBtn.setVisible(true);
            }

            if (!pollQuestion.getText().isEmpty() && !question.isEmpty()) {
                pollQuestion.setText(question);
                if (!Session.getCurrentPoll().isActive()) {
                    pollOptionBtn.setVisible(false);
                }
            }

            if (!pollQuestion.getText().isEmpty() && question.isEmpty()) {
                pollQuestion.setText(question);
                pollOptionBtn.setVisible(false);
                pollDeleteBtn.setVisible(false);
                closeBtn.setVisible(false);
            }
        }

        if (Session.getCurrentPoll() != null && !Session.getCurrentPoll().isActive()) {
            refreshPollOptions();
            pollOptionBtn.setVisible(false);
            closeBtn.setDisable(true);
            getPollResults(Session.getCurrentPoll().getId());
        }
    }

    private void refreshPollOptions() throws Exception {

        if (Session.getCurrentPoll() != null) {
            if (Session.getCurrentPoll().isActive()) {
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
                    }
                }
            }
        } else {
            pollAnswers.getChildren().clear();
        }
    }

    private void getPollResults(long id) {
        ObservableList<Node> choices = pollAnswers.getChildren();
        for (Node choice : choices) {
            for (int i = 0; i < 10; i++) {
                if (pollAnswers.getRowIndex(choice) == i
                        && pollAnswers.getColumnIndex(choice) == 1) {
                    Label label = (Label) choice;
                    label.setText(PollOptionRequest.getPollVotes(label.getId()));
                }
            }
        }
    }

    /**
     * Refreshes the current speed poll.
     */
    public void refreshPacePoll() throws Exception {

        fastLabel.setText(String.valueOf(PollOptionRequest
                .getVotes(Session.getLecture().getId(), 1)));

        rightLabel.setText(String.valueOf(PollOptionRequest
                .getVotes(Session.getLecture().getId(), 2)));

        slowLabel.setText(String.valueOf(PollOptionRequest
                .getVotes(Session.getLecture().getId(), 3)));
    }

    /**
     * Deletes the current lecture poll.
     */
    public void closePoll() throws Exception {

        pollOptionBtn.setVisible(false);
        closeBtn.setDisable(true);
        PollRequest.deactivateCurrentPoll(Session.getCurrentPoll().getId());

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
     * Deletes the current lecture poll.
     */
    public void deletePoll() throws Exception {
        pollQuestion.setText("");
        pollOptionBtn.setVisible(false);
        pollDeleteBtn.setVisible(false);
        closeBtn.setVisible(false);
        closeBtn.setDisable(false);

        PollRequest.deleteCurrentPoll(Session.getCurrentPoll().getId());
        PollOptionRequest.deleteCurrentPoll(Session.getCurrentPoll().getId());
        pollAnswers.getChildren().clear();
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
     * End the lecture by deleting all users.
     */
    public void endCurrentLecture() throws Exception {
        LectureRequest.endLecture(lecture);
        LectureRequest.deleteUsers(lecture);
        started = false;
    }

    /**
     * Start the lecture by setting the ongoing attribute true.
     */
    public void startLecture(ActionEvent actionEvent) {
        LectureRequest.startLecture(this.lecture);
        startTime = System.currentTimeMillis();
        started = true;
    }

    /**
     * Prompt user to set the destination path for the csv file.
     * Call the method that saves the csv file with the path as a parameter.
     */
    public void exportQuestions() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save questions");
        fileChooser.setInitialFileName(escapeName(lecture.getName()));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("JSON", "*.json"),
                new FileChooser.ExtensionFilter("TXT", "*.txt"));

        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            try {
                System.out.println(file.getAbsolutePath());
                if (file.getAbsolutePath().contains(".json")) {
                    System.out.println("JSON");
                    writeQuestionsJson(file.getAbsolutePath());
                } else {
                    System.out.println("CSV");
                    writeQuestionsCsv(file.getAbsolutePath());
                }

                //alert success
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                //alert fail
            }
        }
    }

    /**
     * Clean lecture name to avoid IO name exceptions.
     */
    public String escapeName(String name) {
        return name.replaceAll("[^\\p{IsAlphabetic}\\p{IsDigit}]", "");
    }

    /**
     * Export questions to CSV.
     */
    public void writeQuestionsCsv(String path) throws FileNotFoundException {
        System.out.println(path);
        Field[] fields = Question.class.getDeclaredFields();
        StringBuilder file = new StringBuilder();//"\r\n"
        for (Field field : fields) {
            file.append(field.getName() + ",");
        }
        file.deleteCharAt(file.length() - 1);
        file.append("\r\n");

        List<Question> questions = QuestionRequest
                .getAllQuestionsByLectureId(lecture.getIdString());

        for (Question question : questions) {
            for (Field field : fields) {
                java.lang.reflect.Field.setAccessible(fields, true);
                try {
                    file.append(String.valueOf(field.get(question)).replaceAll(",", ".") + ",");
                } catch (IllegalAccessException e) {
                    file.append(",");
                    e.printStackTrace();
                }
            }
            file.deleteCharAt(file.length() - 1);
            file.append("\r\n");
        }
        PrintWriter writer = new PrintWriter(path);
        writer.print(file);
        writer.close();

        System.out.println(file);
    }

    /**
     * Export questions to JSON.
     */
    public void writeQuestionsJson(String path) throws FileNotFoundException {

        System.out.println(path);
        PrintWriter writer = new PrintWriter(path);
        StringBuilder file = new StringBuilder();

        List<Question> questions = QuestionRequest
                .getAllQuestionsByLectureId(lecture.getIdString());

        Gson gson = new Gson();
        String str = gson.toJson(questions);
        file.append(str);

        writer.print(file);
        writer.close();
        System.out.println(file);
    }

    /**
     * This method hides the side panel to provide a more focused view for the lecturer.
     *
     * @param actionEvent Checkstyle
     */
    public void toggleLecturerView(ActionEvent actionEvent) {

        moderatorViewButton.setVisible(true);
        lecturerViewButton.setVisible(false);

        sidePane.setVisible(false);

    }

    /**
     * This method shows the side panel back to provide moderators with more information.
     *
     * @param actionEvent ugh Checkstyle
     */
    public void toggleModeratorView(ActionEvent actionEvent) {

        moderatorViewButton.setVisible(false);
        lecturerViewButton.setVisible(true);

        sidePane.setVisible(true);

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

    public static long getStartTime() {
        return startTime;
    }

    /**
     * Method for banning users.
     * @param actionEvent button for banning users
     */
    public void banUser(ActionEvent actionEvent) {

        /*
        Parent root;

        try {

            URL url = new File("client/src/main/resources/banUserPopUp.fxml").toURI().toURL();

            root = FXMLLoader.load(url);
            Stage stage = new Stage();
            stage.setTitle("Ban User");
            stage.setScene(new Scene(root, 450, 450));
            stage.show();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

         */

        try {
            List<User> banUserList = LectureRequest.getAllUserAliases(Session.getLecture().getId());

            if (!(banUserList.isEmpty())) {
                TextInputDialog input = new TextInputDialog();
                input.setTitle("Ban User");
                input.setHeaderText("Type in the user's alias:");
                input.showAndWait();
                String banAlias = input.getEditor().getText();

                for (User u : banUserList) {
                    if (u.getAlias().equals(banAlias)) {
                        System.out.println(u.toString());
                        UserRequest.banUser(u);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("User banned!");
                        alert
                                .setHeaderText("This user has been banned"
                                        + "from the lecture successfully!");
                        alert.showAndWait();
                        return;
                    }
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("User not found!");
                alert.setHeaderText("User not found! Please try again!");
                alert.showAndWait();
                return;

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("No users!");
                alert.setHeaderText("There are no users in the lecture!");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    /**
     * This method gets all logs from the server.
     * @param actionEvent Event
     * @throws Exception Error.
     */
    public void downloadLogFile(ActionEvent actionEvent) throws Exception {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save log file:");
        fileChooser.setInitialFileName("logs");
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("Log file (.log)", "*.log"));

        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {

            FileWriter output = new FileWriter(file.getAbsolutePath());
            output.write(LectureRequest.getServerLogs(file.getName()));
            output.close();

        }

    }

    /**
     * This method enables the rate limit feature.
     */
    public void enableRateLimit() {

        try {
            LectureRequest.enableRateLimit(Session.getLecture());

            enableRateLimitButton.setVisible(false);
            disableRateLimitButton.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * This method disables the rate limit feature.
     */
    public void disableRateLimit() {

        try {
            LectureRequest.disableRateLimit(Session.getLecture());

            disableRateLimitButton.setVisible(false);
            enableRateLimitButton.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

