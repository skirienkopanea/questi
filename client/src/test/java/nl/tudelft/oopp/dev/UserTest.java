package nl.tudelft.oopp.dev;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import nl.tudelft.oopp.prod.objects.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {

    User u1;
    User u2;
    User u3;
    User u4;
    User u5;
    User u6;
    User u7;
    User u8;

    /**
     * Javadoc to be added.
     */
    @BeforeEach
    public void initEach() {
        u1 = new User(1234, "John Doe", 1, false);
        u2 = new User(1234, "John Doe", 1, false);
        u3 = new User(9867, "Mary", 4, true);
        u4 = new User(7683, false, 3);
        u5 = new User(7683, false, 3);
        u6 = new User(8356, true, 5);
        u7 = new User();
        u8 = new User();
    }

    @Test
    public void testConstructor1() {
        assertNotEquals(u1, u2);
    }

    @Test
    public void testConstructor2() {
        assertNotEquals(u1, u3);
    }

    @Test
    public void testConstructor3() {
        assertNotEquals(u4, u5);
    }

    @Test
    public void testConstructor4() {
        assertNotEquals(u4, u6);
    }

    @Test
    public void testConstructor5() {
        assertNotEquals(u7, u8);
    }

    @Test
    public void testID() {
        assertEquals(1234, u1.getId());
    }

    @Test
    public void testLectureID() {
        assertEquals(1, u1.getLectureId());
    }

    @Test
    public void testSetID() {
        u1.setId(5);
        assertEquals(5, u1.getId());
    }

    @Test
    public void testSetAlias() {
        u1.setAlias("New Alias");
        assertEquals("New Alias", u1.getAlias());
    }

    @Test
    public void testIsMod() {
        assertEquals(false, u1.isMod());
    }

    @Test
    public void testSetmod() {
        u1.setMod(true);
        assertEquals(true, u1.isMod());
    }

    @Test
    public void testSetLectureID() {
        u1.setLectureId(null);
        assertEquals(null, u1.getLectureId());
    }
}
