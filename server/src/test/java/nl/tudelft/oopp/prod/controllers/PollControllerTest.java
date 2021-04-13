package nl.tudelft.oopp.prod.controllers;

import static org.mockito.Mockito.description;
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
import nl.tudelft.oopp.prod.entities.Poll;
import nl.tudelft.oopp.prod.services.PollService;
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
@WebMvcTest(PollController.class)
public class PollControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PollService pollService;

    private Poll p1;
    private Poll p2;
    private Poll p3;
    private Lecture l1;
    private Date date;

    /**
     * Initializes the test prereqs.
     */
    @BeforeEach
    public void beforeEach() {

        this.p1 = new Poll(1L, 10L, "Test poll #1", true);
        this.p2 = new Poll(2L, 11L, "Test poll #2");
        this.p3 = new Poll(3L, 12L, "Test poll #3");

        this.date = new Date(2021, 3, 30);

        this.l1 =  new Lecture(10L, "modlink1", "studlink1",
                "cse1", true, date, date);

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
    public void getNewPollIdTest() throws Exception {

        when(this.pollService.getPollId()).thenReturn(1L);

        mockMvc.perform(get("/poll/pollId")
                .accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    public void getAllPollsTest() throws Exception {

        when(this.pollService.getAllPolls())
                .thenReturn(List.of(p1, p2, p3));

        mockMvc.perform(get("/poll")
                .contentType(MediaType.APPLICATION_JSON)
                .content(UserControllerTest.lectureJsonString(l1))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("["
                        + PollControllerTest.asJsonString(p1)
                        + "," + PollControllerTest.asJsonString(p2)
                        + "," + PollControllerTest.asJsonString(p3)
                        + "]"
                ));

    }

    @Test
    public void getLivePollTest() throws Exception {

        when(this.pollService.getLivePoll(10L)).thenReturn(p1);

        mockMvc.perform(get("/poll/lecture/{id}", 10L)
                .accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .json(PollControllerTest.asJsonString(p1)));

    }

    @Test
    public void insertPollTest() throws Exception {

        when(this.pollService.insertPoll(p2)).thenReturn(p2);

        mockMvc.perform(post("/poll/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(PollControllerTest.asJsonString(p2))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .json(PollControllerTest.asJsonString(p2)));

    }

    @Test
    public void deletePollTest() throws Exception {

        mockMvc.perform(delete("/poll/delete/{userId}", 1L)
                .accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void updatePollTest() throws Exception {
        p1.setDescription("This poll has been changed");
        when(this.pollService
                .updatePoll(1L, 10L, "This poll has been changed"))
                .thenReturn(p1);

        mockMvc.perform(put("/poll/{pollId}", 1L)
                .param("lectureId", "10")
                .param("description", "This poll has been changed")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .json(PollControllerTest.asJsonString(p1)));
    }

    @Test
    public void deactivatePollTest() throws Exception {

        p1.setActive(false);
        when(this.pollService.deactivate(Long.toString(p1.getId())))
                .thenReturn(p1);

        mockMvc.perform(put("/poll/deactivate")
                .content(Long.toString(p1.getId()))
                .contentType(MediaType.ALL)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .json(PollControllerTest.asJsonString(p1)));


    }

}
