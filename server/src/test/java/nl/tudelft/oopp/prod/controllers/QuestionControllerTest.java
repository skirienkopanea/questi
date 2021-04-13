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
import java.util.List;

import nl.tudelft.oopp.prod.entities.Question;
import nl.tudelft.oopp.prod.services.QuestionService;
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
@WebMvcTest(QuestionController.class)
public class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionService service;

    private Question q1;
    private Question q2;
    private Question q3;

    /**
     * Initialises objects used for testing.
     */
    @BeforeEach
    public void beforeEach() {
        this.q1 = new Question(1L, 42L, 524L,
                "How are you?", 10,
                "Hello World", "Yue");
        this.q2 = new Question(2L, 42L, 524L,
                "What is this?", 1,
                "Java", "Victoria");
        this.q3 = new Question(40L, 524L,
                "Unanswered", 1, "Insufficient answer");

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

    @Test
    public void getAllQuestionsTest() throws Exception {

        when(service.getAllQuestions()).thenReturn(List.of(q1, q2));

        this.mockMvc.perform(get("/question")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("["
                        + asJsonString(q1)
                        + "," + asJsonString(q2)
                        + "]"));
    }

    @Test
    public void getAllQuestionsInLectureTest() throws Exception {

        when(service.getAllQuestionsInLecture(42))
                .thenReturn(List.of(q1));

        //expect list
        mockMvc.perform(get("/question/get/{id}", 42)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().json("[" + QuestionControllerTest.asJsonString(q1) + "]"));
    }


    @Test
    public void insertQuestionTest() throws Exception {

        when(this.service.insertQuestion(q1))
                .thenReturn(new Question(1L, 42L, 524L,
                        "How are you?", 10,
                        "Hello World", "Yue"));

        mockMvc.perform(post("/question/askQuestion")
                .content(QuestionControllerTest.asJsonString(q1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(QuestionControllerTest.asJsonString(q1)));

    }

    @Test
    public void getAnsweredQuestionsTest() throws Exception {
        when(this.service.getAllAnsweredQuestionsInLecture(42L))
                .thenReturn(List.of(q1, q2));

        mockMvc.perform(get("/question/get/{id}/answered", 42L)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("["
                        + QuestionControllerTest.asJsonString(q1.getText()) + ","
                        + QuestionControllerTest.asJsonString(q2.getText()) + "]"));
    }

    @Test
    public void deleteQuestionTest() throws Exception {
        mockMvc.perform(delete("/question/delete/{questionId}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void upVoteQuestionTest() throws Exception {
        q1.addVotedUser("Yue");
        q1.upVote();
        when(this.service.upVoteQuestion(q1, "Yue"))
                .thenReturn(q1);

        Question temp1 = new Question(1L, 42L, 524L,
                "How are you?", 11,
                "Hello World", "Yue");
        temp1.addVotedUser("Yue");

        mockMvc.perform(put("/question/upVote/{userAlias}", "Yue")
                .content(QuestionControllerTest.asJsonString(q1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(QuestionControllerTest.asJsonString(temp1)));
    }

    @Test
    public void editQuestionTest() throws Exception {
        this.q2.setText("What programming language is this?");
        when(this.service.editQuestion(q2))
                .thenReturn(new Question(2L, 42L, 524L,
                        "What programming language is this?", 1,
                        "Java", "Victoria"));

        mockMvc.perform(put("/question/editQuestion")
                .content(QuestionControllerTest.asJsonString(q2))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(QuestionControllerTest.asJsonString(q2)));
    }

    @Test
    public void replyQuestionTest() throws Exception {
        this.q3.setAnswer("Sufficient Answer now");
        when(this.service.replyQuestion(q3))
                .thenReturn(new Question(40L, 524L,
                        "Unanswered", 1,
                        "Sufficient Answer now"));

        mockMvc.perform(put("/question/replyQuestion")
                .content(QuestionControllerTest.asJsonString(q3))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(QuestionControllerTest.asJsonString(q3)));
    }

    @Test
    public void markAnsweredQuestionTest() throws Exception {
        this.q3.setAnswered(true);
        when(this.service.markAnswered(q3))
                .thenReturn(q3);

        mockMvc.perform(put("/question/answered")
                .content(QuestionControllerTest.asJsonString(q3))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(QuestionControllerTest.asJsonString(q3)));
    }

    @Test
    public void hideQuestionTest() throws Exception {
        this.q1.setHidden(true);
        when(this.service.hideQuestion(q1))
                .thenReturn(q1);

        mockMvc.perform(put("/question/hide")
                .content(QuestionControllerTest.asJsonString(q1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(QuestionControllerTest.asJsonString(q1)));

    }

}
