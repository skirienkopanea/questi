package nl.tudelft.oopp.prod.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import nl.tudelft.oopp.prod.communication.LectureRequest;
import nl.tudelft.oopp.prod.communication.PollOptionRequest;
import nl.tudelft.oopp.prod.communication.PollRequest;
import nl.tudelft.oopp.prod.objects.Lecture;

public class ScheduleController {

    //javafx object ids
    @FXML
    private Spinner<Integer> startHour;

    @FXML
    private Spinner<Integer> startMinute;

    @FXML
    private Spinner<Integer> endHour;

    @FXML
    private Spinner<Integer> endMinute;

    @FXML
    private TextField moderatorLink;

    @FXML
    private TextField publicLink;

    @FXML
    private TextField name;

    @FXML
    private DatePicker date;

    @FXML
    private Button submit;

    @FXML
    private Button cancel;

    @FXML
    private Label hour;

    @FXML
    private Label hour1;

    @FXML
    private Label minute;

    @FXML
    private Label minute1;

    @FXML
    private Label startTime;

    @FXML
    private Label endTime;

    @FXML
    private Label dateLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label title;


    /**
     * Returns to home page.
     */
    public void buttonCancelClicked(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass()
                .getClassLoader().getResource("homeScreen.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    /**
     * Get id and tokens and POST lecture request.
     */
    public void buttonSaveClicked(ActionEvent event) throws IOException {

        String lectureName = name.getText();
        Date lectureDate;
        long id;
        String pubLink = "";
        String modLink = "";
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();


        try {
            lectureDate = java.sql.Date.valueOf(date.getValue().toString());
        } catch (Exception e) {
            lectureDate = null;
        }

        if (!lectureName.isEmpty() && lectureDate != null) {


            try {
                id = LectureRequest.getGeneratedLectureId();
                pubLink = LectureRequest.getGeneratedToken();
                modLink = LectureRequest.getGeneratedToken();

                start.setTime(lectureDate);
                start.add(Calendar.HOUR_OF_DAY, startHour.getValue() - 2); //CET
                start.add(Calendar.MINUTE, startMinute.getValue());

                end.setTime(lectureDate);
                end.add(Calendar.HOUR_OF_DAY, endHour.getValue() - 2); //CET
                end.add(Calendar.MINUTE, endMinute.getValue());


                Lecture lecture = new Lecture(
                        id,
                        modLink,
                        pubLink,
                        name.getText(),
                        false,
                        start.getTime(),
                        end.getTime()
                );

                LectureRequest.postLecture(lecture);

            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Unexpected error");
                alert.setHeaderText("Lecture could not be saved on the server, "
                        + "please try again");
                alert.showAndWait();
                return;
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Lecture created, copy the links before you return");
            alert.showAndWait();

            name.setVisible(false);
            date.setVisible(false);
            startHour.setVisible(false);
            startMinute.setVisible(false);
            endHour.setVisible(false);
            endMinute.setVisible(false);
            hour.setVisible(false);
            hour1.setVisible(false);
            minute.setVisible(false);
            minute1.setVisible(false);

            startTime.setText(startTime.getText() + " "
                    + (startHour.getValue() < 10 ? "0"
                    + startHour.getValue() : startHour.getValue())
                    + ":" + (startMinute.getValue() < 10 ? "0"
                    + startMinute.getValue() : startMinute.getValue()));

            endTime.setText(endTime.getText() + " "
                    + (endHour.getValue() < 10 ? "0"
                    + endHour.getValue() : endHour.getValue())
                    + ":" + (endMinute.getValue() < 10 ? "0"
                    + endMinute.getValue() : endMinute.getValue()));

            nameLabel.setText(nameLabel.getText() + " " + name.getText());

            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
            dateLabel.setText(dateLabel.getText() + " " + sdf.format(lectureDate));

            moderatorLink.setOpacity(1);
            moderatorLink.setText(modLink);

            publicLink.setOpacity(1);
            publicLink.setText(pubLink);

            title.setText("Session details");


            submit.setVisible(false);
            cancel.setText("RETURN");

            try {
                PollRequest.addSpeedPoll(id, id);
                PollOptionRequest.addSpeedOptions(id);

            } catch (Exception e) {
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Unexpected error");
                alert2.setHeaderText("Lecture could not be saved on the serve because of the poll, "
                        + "please try again");
                alert2.showAndWait();
                return;
            }
        } else {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Empty Input");
            alert.setHeaderText("Please fill in all the information");
            alert.showAndWait();
        }


    }


}
