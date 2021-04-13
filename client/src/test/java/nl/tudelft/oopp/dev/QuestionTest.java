package nl.tudelft.oopp.dev;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Date;

import nl.tudelft.oopp.prod.communication.QuestionRequest;
import nl.tudelft.oopp.prod.objects.Lecture;
import nl.tudelft.oopp.prod.objects.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class QuestionTest {
    Question q1;
    Question q2;
    Question q3;
    Lecture l1;
    Lecture l2;
    Date start;
    Date end;

    /**
     * Javadoc to be added.
     */
    @BeforeEach
    public void initEach() {
        l1 = new Lecture(2, "moderatorLink", "publicLink", "Lecture", false, start, end);
        l2  = new Lecture(5, "moderatorLink", "publicLink", "Lecture", true, start, end);
        q1 = new Question("why", l1, 1234, "Student_1",-1,"");
        q2 = new Question("why", l1, 1234, "Student_2",-1,"");
        q3 = new Question(null, l2, 9876, "Student_3",-1,"");
    }

    @Test
    public void testConstructor1() {
        assertNotEquals(q1, q2);
    }

    @Test
    public void testConstructor2() {
        assertNotEquals(q1, q3);
    }

    @Test
    public void testID() {
        long lastQuestionID = QuestionRequest.getLastQuestionId();
        assertEquals(lastQuestionID, q1.getId());
    }

    @Test
    public void testLectureID() {
        assertEquals(2, q1.getLectureId());
    }

    @Test
    public void testUserID() {
        assertEquals(1234, q1.getUserId());
    }

    @Test
    public void testText() {
        assertEquals("why", q1.getText());
    }

    @Test
    public void testVotes() {
        assertEquals(0, q1.getVotes());
    }

    @Test
    public void testAnswer() {
        assertEquals("", q1.getAnswer());
    }

    @Test
    public void testSetID() {
        q1.setId(10);
        assertEquals(10, q1.getId());
    }

    @Test
    public void testSetLectureID() {
        q1.setLectureId(15);
        assertEquals(15, q1.getLectureId());
    }

    @Test
    public void testSetUserID() {
        q1.setUserId(4567);
        assertEquals(4567, q1.getUserId());
    }

    @Test
    public void testSetText() {
        q1.setText("why again");
        assertEquals("why again", q1.getText());
    }

    @Test
    public void testSetVotes() {
        q1.setVotes(78);
        assertEquals(78, q1.getVotes());
    }

    @Test
    public void testSetAnswer() {
        q1.setAnswer("idk");
        assertEquals("idk", q1.getAnswer());
    }

    @Test
    public void testIsAnswered() {
        assertEquals(false, q1.isAnswered());
    }

    @Test
    public void testSetAnswered() {
        q1.setAnswered(true);
        assertEquals(true, q1.isAnswered());
    }

    @Test
    public void testToStringNotNull() {
        String result = "why ";
        assertEquals(result, q1.toString());
    }

    @Test
    public void testToStringNull() {
        String result = "-----";
        assertEquals(result, q3.toString());
    }
}
