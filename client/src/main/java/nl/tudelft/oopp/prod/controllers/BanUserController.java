package nl.tudelft.oopp.prod.controllers;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import nl.tudelft.oopp.prod.communication.LectureRequest;
import nl.tudelft.oopp.prod.communication.UserRequest;
import nl.tudelft.oopp.prod.objects.Session;
import nl.tudelft.oopp.prod.objects.User;


public class BanUserController {

    @FXML
    private VBox vbox;

    private List<User> userList;

    /**
     * Initializing the ban user format component.
     */
    @FXML
    public void initialize() {

        try {
            userList = LectureRequest.getAllUserAliases(Session.getLecture().getId());
            System.out.println(userList.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        makeVBoxOfUsers(userList);

    }

    /**
     * Create a list of all users.
     * @param users users in lecture.
     */
    public void makeVBoxOfUsers(List<User> users) {

        if (!(users.isEmpty())) {

            for (User user : users) {
                vbox.getChildren().add(new BanUserFormatComponent(user));
            }

        }

        vbox.getChildren().clear();
        vbox.setSpacing(18.0);



    }

}
