package nl.tudelft.oopp.prod.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PollOptionTest {

    PollOption o1;

    @BeforeEach
    public void initialization() {
        o1 = new PollOption(1,
                1,
                "YES",
                2509);
    }

    @Test
    void getId() {
        assertEquals(1, o1.getId());
    }

    @Test
    void setId() {
        o1.setId(2);
        assertEquals(2, o1.getId());
    }

    @Test
    void getPollId() {
        assertEquals(1, o1.getPollId());
    }

    @Test
    void setPollId() {
        o1.setPollId(2);
        assertEquals(2, o1.getPollId());
    }

    @Test
    void getText() {
        assertEquals("YES", o1.getText());
    }

    @Test
    void setText() {
        o1.setText("NO");
        assertEquals("NO", o1.getText());
    }

    @Test
    void getScore() {
        assertEquals(2509, o1.getScore());
    }

    @Test
    void setScore() {
        o1.setScore(1101);
        assertEquals(1101, o1.getScore());
    }

    @Test
    void downVote() {
        int before = o1.getScore();
        o1.downVote();
        assertEquals(before, o1.getScore() + 1);
    }

    @Test
    void upVote() {
        int before = o1.getScore();
        o1.upVote();
        assertEquals(before, o1.getScore() - 1);
    }

    @Test
    void testToString() {
        assertNotNull(o1.toString());
    }

    @Test
    void testEquals() {
        PollOption o2 = new PollOption(1,
                1,
                "YES",
                2509);
        assertEquals(o1, o2);
    }
}