package nl.tudelft.oopp.dev;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import nl.tudelft.oopp.prod.objects.Poll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PollTest {

    Poll p1;
    Poll p2;
    Poll p3;

    /**
     * Javadoc to be added.
     */
    @BeforeEach
    private void initEach() {
        p1 = new Poll(1, 1, "Description of poll");
        p2 = new Poll(1, 1, "Description of poll");
        p3 = new Poll(3, 4, "Description of poll");
    }

    @Test
    public void testConstructor1() {
        assertNotEquals(p1, p2);
    }

    @Test
    public void testConstructor2() {
        assertNotEquals(p1, p3);
    }
}
