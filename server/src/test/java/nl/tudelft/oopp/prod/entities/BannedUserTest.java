package nl.tudelft.oopp.prod.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BannedUserTest {

    BannedUser b1;

    /**
     * This initializes the test prereqs.
     */
    @BeforeEach
    public void beforeEach() {

        b1 = new BannedUser(1L, 10L, 2L);

    }

    @Test
    public void getId() {
        assertEquals(1, b1.getId());
    }

    @Test
    void setId() {
        b1.setId(2);
        assertEquals(2, b1.getId());
    }

    @Test
    void getLectureId() {
        assertEquals(10, b1.getLectureId());
    }

    @Test
    void setLectureId() {
        b1.setLectureId(2);
        assertEquals(2, b1.getLectureId());
    }

    @Test
    void getUserId() {
        assertEquals(2, b1.getUserId());
    }

    @Test
    void setUserId() {
        b1.setUserId(3);
        assertEquals(3, b1.getUserId());
    }

    @Test
    void testToString() {
        assertNotNull(b1.toString());
    }

    @Test
    void testEquals() {

        BannedUser b2 = new BannedUser(1L, 10L, 2L);
        assertEquals(b1, b2);

    }

}
