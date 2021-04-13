package nl.tudelft.oopp.prod.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LectureTest {

    Lecture lecture;

    /**
     * Initialization.
     */
    @BeforeEach
    public void initialization() {
        lecture = new Lecture(
                1,
                "firstMOD",
                "firstSTUDENT",
                "Introduction",
                true,
                new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis())
        );
    }

    @Test
    void getId() {
        assertEquals(1, lecture.getId());
    }

    @Test
    void setId() {
        lecture.setId(2);
        assertEquals(2, lecture.getId());
    }

    @Test
    void getModeratorLink() {
        assertEquals("firstMOD", lecture.getModeratorLink());
    }

    @Test
    void setModeratorLink() {
        lecture.setModeratorLink("A");
        assertEquals("A", lecture.getModeratorLink());
    }

    @Test
    void getPublicLink() {
        assertEquals("firstSTUDENT", lecture.getPublicLink());
    }

    @Test
    void setPublicLink() {
        lecture.setPublicLink("B");
        assertEquals("B", lecture.getPublicLink());
    }

    @Test
    void getName() {
        assertEquals("Introduction", lecture.getName());
    }

    @Test
    void setName() {
        lecture.setName("C");
        assertEquals("C", lecture.getName());
    }

    @Test
    void isOngoing() {
        assertTrue(lecture.isOngoing());
    }

    @Test
    void setOngoing() {
        lecture.setOngoing(true);
        assertTrue(lecture.isOngoing());
    }

    @Test
    void getStartTime() {
        assertNotNull(lecture.getStartTime());
    }

    @Test
    void setStartTime() {
        lecture.setStartTime(new Date(System.currentTimeMillis()));
        assertNotNull(lecture.getStartTime());
    }

    @Test
    void getEndTime() {
        assertNotNull(lecture.getEndTime());
    }

    @Test
    void setEndTime() {
        lecture.setEndTime(new Date(System.currentTimeMillis()));
        assertNotNull(lecture.getEndTime());
    }

    @Test
    void testToString() {
        assertNotNull(lecture.toString());
    }

    @Test
    void testEquals() {
        Lecture lecture2 = new Lecture(
                1,
                "firstMOD",
                "firstSTUDENT",
                "Introduction",
                true,
                lecture.getStartTime(),
                lecture.getEndTime());
        assertEquals(lecture, lecture2);
    }
}
