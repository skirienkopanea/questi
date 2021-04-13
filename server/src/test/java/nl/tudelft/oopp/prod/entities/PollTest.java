package nl.tudelft.oopp.prod.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PollTest {

    Poll p1;

    @BeforeEach
    public void initialization() {
        p1 = new Poll(1,
                1,
                "YES");
    }

    @Test
    void getId() {
        assertEquals(1, p1.getId());
    }

    @Test
    void setId() {
        p1.setId(2);
        assertEquals(2, p1.getId());
    }

    @Test
    void getLectureId() {
        assertEquals(1, p1.getLectureId());
    }

    @Test
    void setLectureId() {
        p1.setLectureId(2);
        assertEquals(2, p1.getLectureId());
    }

    @Test
    void getDescription() {
        assertEquals("YES", p1.getDescription());
    }

    @Test
    void setDescription() {
        p1.setDescription("NO");
        assertEquals("NO", p1.getDescription());
    }

    @Test
    void testToString() {
        assertNotNull(p1.toString());
    }

    @Test
    void testEquals() {
        Poll p2 = new Poll(1,
                1,
                "YES");
        assertEquals(p1, p2);
    }
}