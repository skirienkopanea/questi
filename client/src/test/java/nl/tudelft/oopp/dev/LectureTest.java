package nl.tudelft.oopp.dev;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Date;

import nl.tudelft.oopp.prod.objects.Lecture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LectureTest {

    Date start;
    Date end;
    Lecture l1;
    Lecture l2;
    Lecture l3;
    Lecture l4;
    Lecture l5;
    Lecture l6;
    Lecture l7;
    Lecture l8;

    /**
     * Javadoc to be added.
     */
    @BeforeEach
    public void initEach() {
        start = new Date(1000);
        end = new Date(1001);
        l1 = new Lecture(2, "moderatorLink", "publicLink", "Lecture", false, start, end);
        l2 = new Lecture(2, "moderatorLink", "publicLink", "Lecture", false, start, end);
        l3  = new Lecture(5, "moderatorLink", "publicLink", "Lecture", true, start, end);
        l4 = new Lecture(1);
        l5 = new Lecture(3);
        l6 = new Lecture(1);
        l7 = new Lecture();
        l8 = new Lecture();
    }

    @Test
    public void testConstructor1() {
        assertNotEquals(l1, l2);
    }

    @Test
    public void testConstructor2() {
        assertNotEquals(l4, l5);
    }

    @Test
    public void testConstructor3() {
        assertNotEquals(l4, l6);
    }

    @Test
    public void testConstructor4() {
        assertNotEquals(l2, l3);
    }

    @Test
    public void testConstructor5() {
        assertNotEquals(l7, l8);
    }

    @Test
    public void testID() {
        assertEquals(2, l1.getId());
    }

    @Test
    public void testModLink() {
        assertEquals("moderatorLink", l1.getModeratorLink());
    }

    @Test
    public void testPubLink() {
        assertEquals("publicLink", l1.getPublicLink());
    }

    @Test
    public void testName() {
        assertEquals("Lecture", l1.getName());
    }

    @Test
    public void testOngoing() {
        assertEquals(false, l1.isOngoing());
    }

    @Test
    public void testStart() {
        Date startTest = new Date(1000);
        assertEquals(startTest, l1.getStartTime());
    }

    @Test
    public void testEnd() {
        Date endTest = new Date(1001);
        assertEquals(endTest, l1.getEndTime());
    }

    @Test
    public void testSetID() {
        l1.setId(10);
        assertEquals(10, l1.getId());
    }

    @Test
    public void testIdString() {
        assertEquals("2", l1.getIdString());
    }

    @Test
    public void testSetModLink() {
        l1.setModeratorLink("I am moderator");
        assertEquals("I am moderator", l1.getModeratorLink());
    }

    @Test
    public void testSetPubLink() {
        l1.setPublicLink("I am a student");
        assertEquals("I am a student", l1.getPublicLink());
    }

    @Test
    public void testSetName() {
        l1.setName("I am a lecture");
        assertEquals("I am a lecture", l1.getName());
    }

    @Test
    public void testSetOng() {
        l1.setOngoing(true);
        assertEquals(true, l1.isOngoing());
    }

    @Test
    public void testSetStartTime() {
        Date newStart = new Date(2000);
        l1.setStartTime(newStart);
        assertEquals(newStart, l1.getStartTime());
    }

    @Test
    public void testSetEndTime() {
        Date newEnd = new Date(2000);
        l1.setEndTime(newEnd);
        assertEquals(newEnd, l1.getEndTime());
    }

    //@Test
    //public void testJson() {
    //    assertEquals("{\n"
    //            + "    \"id\": " + 2 + ",\n"
    //            + "    \"moderatorLink\": \"" + "moderatorLink" + "\",\n"
    //            + "    \"publicLink\": \"" + "publicLink" + "\",\n"
    //            + "    \"name\": \"" + "Lecture" + "\",\n"
    //            + "    \"startTime\": \"" + "1970-01-01T01:00:01Z" + "\",\n"
    //            + "    \"endTime\": \"" + "1970-01-01T01:00:01Z" + "\",\n"
    //            + "    \"ongoing\": " + false + "\n"
    //            + "}", l1.toJson());
    //}

}
