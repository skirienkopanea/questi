package nl.tudelft.oopp.prod.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest {

    User u1;
    Lecture l1;

    @BeforeEach
    public void initialization() {
        l1 = new Lecture(
                1,
                "firstMOD",
                "firstSTUDENT",
                "Introduction",
                true,
                new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis())
        );

        u1 = new User(
                1,
                "user1",
                false,
                l1,
                1L);

    }

    @Test
    void getId() {
        assertEquals(1, u1.getId());
    }

    @Test
    void setId() {
        u1.setId(2);
        assertEquals(2, u1.getId());
    }

    @Test
    void getAlias() {
        assertEquals("user1", u1.getAlias());
    }

    @Test
    void setAlias() {
        u1.setAlias("aaa");
        assertEquals("aaa", u1.getAlias());
    }

    @Test
    void isMod() {
        assertFalse(u1.isMod());
    }

    @Test
    void setMod() {
        u1.setMod(true);
        assertTrue(u1.isMod());
    }

    @Test
    void getLecture() {
        assertEquals(l1, u1.getLecture());
    }

    @Test
    void setLecture() {
        Lecture l2 = new Lecture(
                2,
                "secondMOD",
                "secondSTUDENT",
                "Summary",
                true,
                new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis())
        );
        u1.setLecture(l2);
        assertEquals(l2, u1.getLecture());
    }

    @Test
    void getLectureId() {
        assertEquals(1, u1.getLectureId());
    }

    @Test
    void setLectureId() {
        u1.setLectureId(100L);
        assertEquals(100, u1.getLectureId());
    }

    @Test
    void testToString() {
        assertNotNull(u1.toString());
    }

    @Test
    void testEquals() {
        User u2 = new User(
                1,
                "user1",
                false,
                l1,
                1L);
        assertEquals(u1, u2);
    }
}
