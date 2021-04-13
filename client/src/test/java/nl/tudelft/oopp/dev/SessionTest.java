package nl.tudelft.oopp.dev;

import java.util.Date;

import nl.tudelft.oopp.prod.objects.Lecture;
import nl.tudelft.oopp.prod.objects.Session;
import nl.tudelft.oopp.prod.objects.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SessionTest {

    @Test
    public void setUser() {
        User user = new User(1, "user", 1, false);
        Session.setUser(user);
        Assertions.assertNotNull(Session.getUser());
    }

    @Test
    public void setLecture() {
        Lecture lecture = new Lecture(1, "moderator", "student",
                "lecture", true,
                new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis()));
        Session.setLecture(lecture);
        Assertions.assertNotNull(Session.getLecture());
    }

    @Test
    public void getUser() {
        User user = new User(1, "user", 1, false);
        Session.setUser(user);
        Assertions.assertEquals(user, Session.getUser());
    }

    @Test
    public void getLecture() {
        Lecture lecture = new Lecture(1, "moderator", "student",
                "lecture", true,
                new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis()));
        Session.setLecture(lecture);
        Assertions.assertEquals(lecture, Session.getLecture());
    }
}