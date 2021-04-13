package nl.tudelft.oopp.prod.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;
import java.util.List;

import nl.tudelft.oopp.prod.entities.BannedUser;
import nl.tudelft.oopp.prod.entities.Lecture;
import nl.tudelft.oopp.prod.services.BannedUserService;
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
@WebMvcTest(BannedUserController.class)
public class BannedUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BannedUserService bannedUserService;

    private Date date;
    private Lecture l1;
    private BannedUser b1;
    private BannedUser b2;
    private BannedUser b3;

    /**
     * Initializes the test prereqs.
     */
    @BeforeEach
    public void beforeEach() {

        this.date = new Date(2021, 3, 31);

        this.l1 =  new Lecture(10L, "modlink1", "studlink1",
                "cse1", true, date, date);

        this.b1 = new BannedUser(1L, 10L, 1L);
        this.b2 = new BannedUser(2L, 11L, 1L);
        this.b3 = new BannedUser(3L, 10L, 2L);

    }

    /**
     * Json parser.
     * @param obj object to be converted
     * @return string of object
     */
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "-";
    }

    @Test
    public void checkIfUserBannedTest() throws Exception {

        when(this.bannedUserService.checkIfUserBanned(b1.getLectureId(), b1.getUserId()))
                .thenReturn(true);

        mockMvc
                .perform(get("/bannedusers/check/{lectureId}/{userId}", 10L, 1L)
                        .accept(MediaType.ALL))
                        .andDo(print())
                        .andExpect(status().isOk());

    }

    @Test
    public void checkIfUserBannedTest2() throws Exception {

        when(this.bannedUserService.checkIfUserBanned(b2.getLectureId(), b3.getUserId()))
                .thenReturn(false);

        mockMvc
                .perform(get("/bannedusers/check/{lectureId}/{userId}", 11L, 2L)
                        .accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void banUserTest() throws Exception {

        when(this.bannedUserService.banUser(b1.getLectureId(), b1.getUserId()))
                .thenReturn(b1);

        mockMvc
                .perform(put("/bannedusers/ban/{lectureId}/{userId}", 10L, 1L)
                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(content()
                                .json(BannedUserControllerTest.asJsonString(b1)));

    }

    @Test
    public void getAllBannedUsersInLectureTest() throws Exception {

        when(this.bannedUserService.getAllBannedUsersInLecture(10L))
                .thenReturn(List.of(b1, b3));

        mockMvc.perform(get("/bannedusers/{lectureId}", 10L)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("["
                        + BannedUserControllerTest.asJsonString(b1)
                        + "," + BannedUserControllerTest.asJsonString(b3)
                        + "]"
                ));

    }

}
