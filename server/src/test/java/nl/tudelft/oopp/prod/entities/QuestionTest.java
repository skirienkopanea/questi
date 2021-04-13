package nl.tudelft.oopp.prod.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QuestionTest {

    Question q1;

    @BeforeEach
    public void initialization() {
        q1 = new Question(1001,
                22,
                465833,
                "AAA",
                100,
                "no",
                "teo",
                -1,
                "");
    }

    @Test
    void getId() {
        assertEquals(1001, q1.getId());
    }

    @Test
    void setId() {
        q1.setId(1);
        assertEquals(1, q1.getId());
    }

    @Test
    void getLectureId() {
        assertEquals(22, q1.getLectureId());
    }

    @Test
    void setLectureId() {
        q1.setLectureId(1);
        assertEquals(1, q1.getLectureId());
    }

    @Test
    void getUserId() {
        assertEquals(465833, q1.getUserId());
    }

    @Test
    void setUserId() {
        q1.setUserId(1);
        assertEquals(1, q1.getUserId());
    }

    @Test
    void getText() {
        assertEquals("AAA", q1.getText());
    }

    @Test
    void setText() {
        q1.setText("Text");
        assertEquals("Text", q1.getText());
    }

    @Test
    void getVotes() {
        assertEquals(100, q1.getVotes());
    }

    @Test
    void setVotes() {
        q1.setVotes(1000);
        assertEquals(1000, q1.getVotes());
    }

    @Test
    void getAnswer() {
        assertEquals("no", q1.getAnswer());
    }

    @Test
    void setAnswer() {
        q1.setAnswer("---");
        assertEquals("---", q1.getAnswer());
    }

    @Test
    void isAnswered() {
        assertTrue(q1.isAnswered());
    }

    @Test
    void setAnswered() {
        q1.setAnswered(false);
        assertFalse(q1.isAnswered());
    }

    @Test
    void getUserAlias() {
        assertEquals("teo", q1.getUserAlias());
    }

    @Test
    void setUserAlias() {
        q1.setUserAlias("test");
        assertEquals("test", q1.getUserAlias());
    }

    @Test
    void testUpVote() {
        int temp = q1.getVotes();
        q1.upVote();
        assertEquals(temp + 1, q1.getVotes());
    }

    @Test
    void testToString() {
        assertNotNull(q1.toString());
    }

    @Test
    void testEquals() {
        Question q2 = new Question(1001,
                22,
                465833,
                "AAA",
                100,
                "no",
                "teo",
                -1,
                "");
        assertEquals(q1, q2);
    }

    @Test
    void getSeconds() {
        assertEquals(0, q1.getSeconds());
    }

    @Test
    void setSeconds() {
        q1.setSeconds(50);
        assertEquals(50, q1.getSeconds());
    }

    @Test
    void getReplierAlias() {
        assertEquals("", q1.getReplierAlias());
    }

    @Test
    void setReplierAlias() {
        q1.setReplierAlias("test");
        assertEquals("test", q1.getReplierAlias());
    }

    @Test
    void getReplierId() {
        assertEquals(-1, q1.getReplierId());
    }

    @Test
    void setReplierId() {
        q1.setReplierId(1);
        assertEquals(1, q1.getReplierId());
    }

    @Test
    void getVotedUsers() {
        assertEquals(new HashSet<>(), q1.getVotedUsers());
    }

    @Test
    void setVotedUsers() {
        q1.setVotedUsers(new HashSet<>());
        assertEquals(new HashSet<>(), q1.getVotedUsers());
    }
}
