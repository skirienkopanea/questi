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
import nl.tudelft.oopp.prod.entities.Poll;
import nl.tudelft.oopp.prod.entities.PollOption;
import nl.tudelft.oopp.prod.services.PollOptionService;
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
@WebMvcTest(PollOptionController.class)
public class PollOptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PollOptionService pollOptionService;

    private Poll p1;
    private PollOption po1;
    private PollOption po2;
    private PollOption po3;
    private Lecture l1;
    private Date date;
    private static final String TIME_FORMAT = "3921-04-28T22:00:00.000+0000";

    /**
     * Initializes the test prereqs.
     */
    @BeforeEach
    public void beforeEach() {

        this.date = new Date(2021, 3, 31);

        this.l1 =  new Lecture(10L, "modlink1", "studlink1",
                "cse1", true, date, date);

        this.p1 = new Poll(1L, 10L, "Test Poll #1");

        this.po1 = new PollOption(1L,1L, "Poll Option #1", 5);
        this.po2 = new PollOption(2L,1L, "Poll Option #2", 7);
        this.po3 = new PollOption(3L,2L, "Poll Option #3", 0);

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
    public void getAllOptionsTest() throws Exception {

        when(this.pollOptionService.getAllPollOptions())
                .thenReturn(List.of(po1, po2, po3));

        mockMvc.perform(get("/pollOption")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("["
                        + PollOptionControllerTest.asJsonString(po1)
                        + "," + PollOptionControllerTest.asJsonString(po2)
                        + "," + PollOptionControllerTest.asJsonString(po3)
                        + "]"
                ));

    }

    @Test
    public void getAllOptionsFromPollTest() throws Exception {

        when(this.pollOptionService.getAllPollOptionsByPollId(10L))
                .thenReturn(List.of(po1, po2));

        mockMvc.perform(get("/pollOption/inpoll/{id}", 10L)
                .accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .json("["
                                + PollOptionControllerTest.asJsonString(po1)
                                + "," + PollOptionControllerTest.asJsonString(po2)
                                + "]"
                        ));

    }

    @Test
    public void getVotesTest() throws Exception {

        when(this.pollOptionService.getVotes("1/1")).thenReturn(5);

        mockMvc.perform(get("/pollOption/votes")
                .param("id", "1/1")
                .accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    @Test
    public void getAmountTest() throws Exception {

        when(this.pollOptionService.getAmount(1L)).thenReturn(2);

        mockMvc.perform(get("/pollOption/amount")
                .param("pollId", "1")
                .accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("2"));
    }

    @Test
    public void insertPollOptionTest() throws Exception {

        when(this.pollOptionService.insertPollOption(po2)).thenReturn(po2);

        mockMvc.perform(post("/pollOption/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(PollOptionControllerTest.asJsonString(po2))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .json(PollOptionControllerTest.asJsonString(po2)));

    }

    @Test
    public void downVotePollOptionTest() throws Exception {

        when(this.pollOptionService.downVote("1/1")).thenReturn(4);

        mockMvc.perform(put("/pollOption/downVote")
                .content("1/1")
                .contentType(MediaType.ALL)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .json("4"));

    }

    @Test
    public void upVotePollOptionTest() throws Exception {

        when(this.pollOptionService.upVote("1/1")).thenReturn(6);

        mockMvc.perform(put("/pollOption/upVote")
                .content("1/1")
                .contentType(MediaType.ALL)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .json("6"));

    }

    @Test
    public void deletePollOptionTest() throws Exception {

        mockMvc.perform(delete("/pollOption/delete/{pollId}", 1)
                .accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void getNewPollOptionIdTest() throws Exception {

        when(this.pollOptionService.getPollOptionId()).thenReturn(1L);

        mockMvc.perform(get("/pollOption/pollId")
                .accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

    }

    @Test
    public void getPollVotesTest() throws Exception {

        when(this.pollOptionService.getPollVotes(po1.getId())).thenReturn(5);

        mockMvc.perform(get("/pollOption/pollvotes")
                .param("id", "1")
                .accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("5"));

    }

    @Test
    public void downVoteQuestionPollOptionTest() throws Exception {

        when(this.pollOptionService.downVoteQuestion("1/1")).thenReturn(4);

        mockMvc.perform(put("/pollOption/downVoteQuestion")
                .content("1/1")
                .contentType(MediaType.ALL)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .json("4"));

    }

    @Test
    public void upVoteQuestionPollOptionTest() throws Exception {

        when(this.pollOptionService.upVoteQuestion("1/1")).thenReturn(6);

        mockMvc.perform(put("/pollOption/upVoteQuestion")
                .content("1/1")
                .contentType(MediaType.ALL)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .json("6"));

    }

}
