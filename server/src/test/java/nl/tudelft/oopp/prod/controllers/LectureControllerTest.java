package nl.tudelft.oopp.prod.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;
import java.util.List;

import nl.tudelft.oopp.prod.entities.Lecture;
import nl.tudelft.oopp.prod.entities.Question;
import nl.tudelft.oopp.prod.entities.User;
import nl.tudelft.oopp.prod.services.LectureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(LectureController.class)
public class LectureControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LectureService service;

    private Lecture l1;
    private Lecture l2;
    private Lecture l3;
    private Question q1;
    private User u1;
    private Date date;
    private static final String TIME_FORMAT = "3921-04-28T22:00:00.000+0000";

    /**
     * Initialises the objects used for tests.
     */
    @BeforeEach
    public void beforeEach() {
        this.date = new Date(2021, 3, 29);
        this.l1 = new Lecture(1L, "modlink1", "studlink1",
                "cse1", true, date, date);
        this.l2 = new Lecture(2L, "modlink2", "studlink2",
                "cse2", true, date, date);
        this.l3 = new Lecture(3L, "modlink3", "studlink3",
                "cse3", false, date, date);

        //question and user in lecture 1
        this.q1 = new Question(1L, 1L, 524L,
                "How are you?", 10,
                "Hello World", "Yue");

        this.u1 = new User(524L, 1L, "Yue", false);
        u1.setLecture(this.l1);
    }

    /**
     * Generic JSON parser.
     * @param obj Object of any class
     * @return JSON String used for requests/response
     */
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "-";
    }

    /**
     * JSON parser for lecture objects.
     * @param lecture Any lecture object
     * @return JSON string for lecture object
     */
    public static String lectureJsonString(Lecture lecture) {
        String start = TIME_FORMAT;
        String end = TIME_FORMAT;

        return ("{\n"
                    + "    \"id\": " + lecture.getId() + ",\n"
                    + "    \"moderatorLink\": \"" + lecture.getModeratorLink() + "\",\n"
                    + "    \"publicLink\": \"" + lecture.getPublicLink() + "\",\n"
                    + "    \"name\": \"" + lecture.getName() + "\",\n"
                    + "    \"startTime\": \"" + start + "\",\n"
                    + "    \"endTime\": \"" + end + "\",\n"
                    + "    \"ongoing\": " + lecture.isOngoing() + "\n"
                    + "}");
    }

    @Test
    public void getAllQuestionsTest() throws Exception {

        when(service.getAllQuestions(1L)).thenReturn(List.of(q1));

        //expect list
        mockMvc.perform(get("/lecture/getAllQuestions/{lectureId}", 1L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[" + LectureControllerTest.asJsonString(q1) + "]"));
    }

    @Test
    public void getPublicLectureTest() throws Exception {
        when(service.getLecture("studlink1"))
                .thenReturn(l1);

        mockMvc.perform(get("/lecture/get")
                .param("link", "studlink1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getModeratorLectureTest() throws Exception {
        when(service.getModLecture("modlink2"))
                .thenReturn(l2);

        mockMvc.perform(get("/lecture/getm")
                .param("link", "modlink2")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getAllUsersTest() throws Exception {
        when(service.getAllUsers(1L))
                .thenReturn(List.of(u1));

        mockMvc.perform(get("/lecture/allUsers/{lectureId}", 1L)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getNewTokenTest() throws Exception {
        when(service.generateToken())
                .thenReturn("TOKEN");

        mockMvc.perform(get("/lecture/make/token")
                .accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("TOKEN"));
    }

    @Test
    public void getOnGoingTest() throws Exception {
        when(service.isOngoing(1L))
                .thenReturn(true);

        mockMvc.perform(get("/lecture/ongoing")
                .param("link", "1")
                .accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void getAllLecturesTest() throws Exception {
        when(service.getAllLectures())
                .thenReturn(List.of(l1, l2, l3));

        mockMvc.perform(get("/lecture")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void makeNewIdTest() throws Exception {
        when(service.generateId())
                .thenReturn(101010L);

        mockMvc.perform(get("/lecture/make/id")
                .accept(MediaType.ALL))
                .andDo(print())
                .andExpect(content().string("101010"));
    }

    @Test
    public void postLectureTest() throws Exception {
        when(service.postLecture(l1)).thenReturn(l1);

        mockMvc.perform(post("/lecture/post")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(LectureControllerTest.lectureJsonString(l1)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void startLectureTest() throws Exception {
        l2.setOngoing(true);
        when(service.startLecture(2L)).thenReturn(l2);

        mockMvc.perform(put("/lecture/start")
                .accept(MediaType.ALL)
                .contentType(MediaType.ALL)
                .content("2"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void endLectureTest() throws Exception {
        l3.setOngoing(false);
        when(service.endLecture(3L)).thenReturn(l3);

        mockMvc.perform(put("/lecture/stop")
                .accept(MediaType.ALL)
                .contentType(MediaType.ALL)
                .content("3"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteUsersTest() throws Exception {
        mockMvc.perform(delete("/lecture/deleteusers/{lectureId}", 1)
                .accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
