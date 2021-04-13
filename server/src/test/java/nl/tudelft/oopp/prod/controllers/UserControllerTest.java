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
import nl.tudelft.oopp.prod.entities.User;

import nl.tudelft.oopp.prod.services.UserService;
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
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    private User u1;
    private User u2;
    private User u3;
    private Lecture l1;
    private Date date;
    private static final String TIME_FORMAT = "3921-04-28T22:00:00.000+0000";

    @BeforeEach
    public void beforeEach() {

        // The users do not have an explicit lecture
        this.u1 = new User(1L, 42L, "Yue", false);
        this.u2 = new User(2L, 42L, "Mod", true);
        this.u3 = new User(3L, 40L, "Stud", false);

        this.date = new Date(2021, 3, 29);
        this.l1 =  new Lecture(42L, "modlink1", "studlink1",
                "cse1", true, date, date);
    }


    //generic JSON parser
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "-";
    }

    //JSON parser for Lecture Objects - specific date format required
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
    public void getUserByIdTest() throws Exception {
        when(this.service.getUser(1L)).thenReturn(u1);

        mockMvc.perform(get("/users/user")
                .param("id", "1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(UserControllerTest.asJsonString(u1)));

    }

    @Test
    public void getAllUsersTest() throws Exception {
        when(this.service.getAllUsers())
                .thenReturn(List.of(u1, u2, u3));

        mockMvc.perform(get("/users/all")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("["
                        + UserControllerTest.asJsonString(u1)
                        + "," + UserControllerTest.asJsonString(u2)
                        + "," + UserControllerTest.asJsonString(u3)
                        + "]"
                ));
    }

    @Test
    public void getAllUsersInLectureTest() throws Exception {
        u1.setLecture(l1);
        u2.setLecture(l1);
        when(this.service.getAllUsersInLecture(l1)).thenReturn(List.of(u1, u2));

        mockMvc.perform(get("/users/getLecture")
                .contentType(MediaType.APPLICATION_JSON)
                .content(UserControllerTest.lectureJsonString(l1))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        // Cannot parse ``JSON of Users, since json parser
        // does not parse the date of Lecture correctly.
    }


    @Test
    public void getNewUserIdTest() throws Exception {
        when(this.service.getUserId()).thenReturn(1L);

        mockMvc.perform(get("/users/userid")
                .accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    public void insertUserTest() throws Exception {
        when(this.service.insertUser(u2)).thenReturn(u2);

        mockMvc.perform(post("/users/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(UserControllerTest.asJsonString(u2))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(UserControllerTest.asJsonString(u2)));
    }

    @Test
    public void deleteUserTest() throws Exception {
        //could refactor to actually return the user whose deleted
        mockMvc.perform(delete("/users/delete/{userId}", 3)
                .accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk());
    }



}
