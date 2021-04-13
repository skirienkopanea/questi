package nl.tudelft.oopp.dev;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Date;

import nl.tudelft.oopp.prod.objects.Lecture;
import nl.tudelft.oopp.prod.objects.Session;
import nl.tudelft.oopp.prod.objects.User;
import nl.tudelft.oopp.prod.objects.UserList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SessionTest2 {

    Session s1;
    Session s2;
    Session s3;
    Lecture l1;
    Lecture l2;
    User u1;
    User u2;
    UserList ul1;
    UserList ul2;
    Date start;
    Date end;

    /**
     * Javadoc to be added.
     */
    @BeforeEach
    public void initEach() {
        u1 = new User(1234, "John Doe", 2, false);
        u2 = new User(9867, "Mary", 4, true);
        l1 = new Lecture(2, "moderatorLink", "publicLink", "Lecture", false, start, end);
        l2  = new Lecture(5, "moderatorLink", "publicLink", "Lecture", true, start, end);
        ul1 = new UserList();
        ul2 = new UserList();
        s1 = new Session(l1, u1, ul1);
        s2 = new Session(l1, u1, ul1);
        s3 = new Session(l2, u2, ul2);
    }

    @Test
    public void testCosntructor1() {
        assertNotEquals(s1, s2);
    }

    @Test
    public void testCosntructor2() {
        assertNotEquals(s1, s3);
    }

    @Test
    public void testLecture() {
        assertEquals(l2, s3.getLecture());
    }

    @Test
    public void testUser() {
        assertEquals(u2, s3.getUser());
    }

    @Test
    public void testUsers() {
        assertEquals(ul1, s1.getUsers());
    }

    @Test
    public void testSetLecture() {
        s1.setLecture(l2);
        assertEquals(l2, s1.getLecture());
    }

    @Test
    public void testSetUser() {
        s1.setUser(u2);
        assertEquals(u2, s1.getUser());
    }

    @Test
    public void testSetUsers() {
        s1.setUsers(ul2);
        assertEquals(ul2, s1.getUsers());
    }
}
