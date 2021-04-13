package nl.tudelft.oopp.dev;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import nl.tudelft.oopp.prod.objects.PollOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PollOptionTest {

    PollOption po1;
    PollOption po2;
    PollOption po3;

    /**
     * Javadoc to be added.
     */
    @BeforeEach
    public void initEach() {
        po1 = new PollOption(1, 1, "first option of first poll", 5);
        po2 = new PollOption(2, 1, "second option of first poll", 3);
        po3 = new PollOption(1, 1, "first option of first poll", 5);
    }

    @Test
    public void testContructor1() {
        assertNotEquals(po1, po2);
    }

    @Test
    public void testContructor2() {
        assertNotEquals(po1, po3);
    }

    @Test
    public void testID() {
        assertEquals(1, po1.getId());
    }

    @Test
    public void testPollID() {
        assertEquals(1, po1.getPollId());
    }

    @Test
    public void testText() {
        assertEquals("first option of first poll", po1.getText());
    }

    @Test
    public void testScore() {
        assertEquals(0, po1.getScore());
    }

    @Test
    public void testSetID() {
        po1.setId(10);
        assertEquals(10, po1.getId());
    }

    @Test
    public void testSetPollID() {
        po1.setPollId(42);
        assertEquals(42, po1.getPollId());
    }

    @Test
    public void testSetText() {
        po1.setText("new text");
        assertEquals("new text", po1.getText());
    }

    @Test
    public void testSetScore() {
        po1.setScore(19);
        assertEquals(19, po1.getScore());
    }
}
