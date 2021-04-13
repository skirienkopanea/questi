package nl.tudelft.oopp.prod.objects;

import java.util.Timer;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import nl.tudelft.oopp.prod.communication.UserRequest;

public class Session {

    public static Timer timer;
    private static Lecture lecture;
    private static User user;
    private static UserList users;
    private static Poll currentPoll;
    private static int numberOfPollOptions;
    private static boolean clickedOne = false;
    private static boolean clickedTwo = false;
    private static boolean clickedThree = false;
    private static boolean ongoing = true;

    public static boolean isOngoing() {
        return ongoing;
    }

    public static void setOngoing(boolean ongoing) {
        Session.ongoing = ongoing;
    }

    public static int getNumberOfPollOptions() {
        return numberOfPollOptions;
    }

    public static void setNumberOfPollOptions(int numberOfPollOptions) {
        Session.numberOfPollOptions = numberOfPollOptions;
    }

    public static void incNumberOfPollOptions() {
        numberOfPollOptions++;
    }

    public static void setClickedOne(boolean clickedOne) {
        Session.clickedOne = clickedOne;
    }

    public static void setClickedTwo(boolean clickedTwo) {
        Session.clickedTwo = clickedTwo;
    }

    public static void setClickedThree(boolean clickedThree) {
        Session.clickedThree = clickedThree;
    }

    public static boolean getClickedOne() {
        return clickedOne;
    }

    public static boolean getClickedTwo() {
        return clickedTwo;
    }

    public static boolean getClickedThree() {
        return clickedThree;
    }

    public static Poll getCurrentPoll() {
        return currentPoll;
    }

    public static void setCurrentPoll(Poll poll) {
        currentPoll = poll;
    }

    /**
     * Constructor for Session.
     */
    public Session(Lecture lecture, User user, UserList users) {
        this.lecture = lecture;
        this.user = user;
        this.users = users;
    }

    public static Lecture getLecture() {
        return lecture;
    }

    public static void setLecture(Lecture lecture) {
        Session.lecture = lecture;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        Session.user = user;
    }

    public static UserList getUsers() {
        return users;
    }

    public static void setUsers(UserList users) {
        Session.users = users;
    }

    /**
     * resets all.
     */
    public static void reset() {
        setLecture(null);
        setUser(null);
        setUsers(null);
        setCurrentPoll(null);
        setNumberOfPollOptions(0);
        setClickedOne(false);
        setClickedTwo(false);
        setClickedThree(false);
    }

    /**
     * Logs user out and ends the process.
     */
    public static void closeAppEvent() {
        Stage.getWindows().get(0).setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                if (!(Session.getUser() == null)) {
                    try {
                        UserRequest.deleteUser(Session.getUser());
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    Platform.exit();
                    System.exit(0);
                }
            }
        });
    }
}
