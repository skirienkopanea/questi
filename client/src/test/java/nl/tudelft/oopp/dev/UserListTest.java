package nl.tudelft.oopp.dev;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;

import nl.tudelft.oopp.prod.objects.User;
import nl.tudelft.oopp.prod.objects.UserList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserListTest {

    UserList ul;

    /**
     * Javadoc to be added.
     */
    @BeforeEach
    public void initEach() {
        ul = new UserList();
    }

    @Test
    public void testUsers() {
        ArrayList<User> al = new ArrayList<>();
        assertEquals(al, ul.getUsers());
    }

    @Test
    public void testEquals() {
        ArrayList<User> al = new ArrayList<>();
        assertFalse(al.equals(ul));
    }

    @Test
    public void testToString() {
        String result = "Participants list:";
        assertEquals(result, ul.toString());
    }

}
