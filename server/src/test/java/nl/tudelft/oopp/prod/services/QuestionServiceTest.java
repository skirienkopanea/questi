package nl.tudelft.oopp.prod.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nl.tudelft.oopp.prod.entities.Lecture;
import nl.tudelft.oopp.prod.entities.Poll;
import nl.tudelft.oopp.prod.entities.Question;
import nl.tudelft.oopp.prod.repositories.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
public class QuestionServiceTest {

    @Autowired
    MockMvc mockMvc;

    @Mock
    QuestionRepository questionRepository;

    @InjectMocks
    QuestionService questionService;

    Question q1;
    Question q2;
    Question q3;
    List<Question> questions;
    Lecture lecture;


    /**
     * Before each.
     */
    @BeforeEach
    public void beforeEach() {

        lecture = new Lecture(
                22,
                "moderatorLink",
                "studentLink",
                "test lecture",
                true,
                new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis())
        );

        q1 = new Question(1001,
                22,
                465833,
                "AAAAAAAAAAAA",
                100,
                "nonononoo");
        q2 = new Question(1002,
                22,
                465833,
                "BBBBBBBBBBBB",
                100,
                null);
        q3 = new Question(1003,
                552042,
                510639,
                "CCCCCCCCCCCC",
                200,
                "ye man");

        questions = new ArrayList<>();
        questionService = new QuestionService(questionRepository);

        Mockito.when(questionRepository.findById(q1.getId())).thenReturn(
                java.util.Optional.ofNullable(q1)
        );

        Mockito.when(questionRepository.findById(q2.getId())).thenReturn(
                java.util.Optional.ofNullable(q2)
        );

        Mockito.when(questionRepository.findById(q3.getId())).thenReturn(
                java.util.Optional.ofNullable(q3)
        );

        Mockito.when(questionRepository.findById(1005)).thenReturn(
                Optional.of(new Question())
        );

        Mockito.when(questionRepository.save(q1)).thenReturn(q1);
    }


    @Test
    public void testGetAllQuestion() {
        questions.add(q1);
        questions.add(q2);
        questions.add(q3);

        when(questionRepository.findAll()).thenReturn(questions);

        List<Question> questionList = questionService.getAllQuestions();

        assertEquals(3, questionList.size());
        verify(questionRepository, times(1)).findAll();
    }

    @Test
    void getQuestion() {
        questions.add(q1);
        questions.add(q2);
        questions.add(q3);

        when(questionRepository.findAll()).thenReturn(questions);
        when(questionRepository.existsById(1001)).thenReturn(true);
        when(questionRepository.findById(1001)).thenReturn(Optional.ofNullable(q1));

        Question q = questionService.findById(1001);

        assertEquals(q, q1);
        verify(questionRepository, times(1)).existsById(1001);
        verify(questionRepository, times(1)).findById(1001);
    }

    @Test
    void getQuestionNonExistent() {
        questions.add(q1);
        questions.add(q2);

        when(questionRepository.findAll()).thenReturn(questions);
        when(questionRepository.existsById(1003)).thenReturn(false);

        Question q = questionService.findById(1003);

        assertNull(q);
        //        verify(questionRepository, times(1)).existsById(1001);
        //        verify(questionRepository, times(1)).findById(1001);
    }

    @Test
    void getAllQuestionsInLecture() {

        questions.add(q1);
        questions.add(q2);

        when(questionRepository.findAllByLectureId(22)).thenReturn(questions);

        List<Question> questionList = questionService.getAllQuestionsInLecture(22);

        assertEquals(2, questionList.size());
        verify(questionRepository, times(1)).findAllByLectureId(22);
    }


    @Test
    void insertQuestion() {

        when(questionRepository.save(any(Question.class))).thenReturn(q1);

        questionService.insertQuestion(q1);

        assertTrue(questionRepository.findById(q1.getId()).isPresent());
        assertEquals(questionRepository.findById(q1.getId()).get(), q1);
        verify(questionRepository, times(1)).save(q1);

    }

    @Test
    void deleteQuestion() {

        questions.add(q1);
        questions.add(q2);

        when(questionRepository.findAll()).thenReturn(questions);
        when(questionRepository.existsById(q2.getId())).thenReturn(true);
        doAnswer(invocation -> {
            questions.remove(q2);
            return null;
        }).when(questionRepository).deleteById(q2.getId());

        questionService.deleteQuestion(q2.getId());
        List<Question> questionList = questionService.getAllQuestions();

        assertEquals(1, questionList.size());
        verify(questionRepository, times(1)).deleteById(q2.getId());

    }

    //Doesn't work!!!!
    //TODO : Check if it can be deleted
    @Test
    void updateQuestion() {
        questions.add(q1);
        questions.add(q2);
        questions.add(q3);

        when(questionRepository.findAll()).thenReturn(questions);
        when(questionRepository.findById(1002L)).thenReturn(Optional.of(q2));
        questionService.editQuestion(q2);
        verify(questionRepository, times(1)).save(q2);

    }

    @Test
    void upVoteQuestion() {

        questions.add(q1);
        questions.add(q2);
        questions.add(q3);

        when(questionRepository.findAll()).thenReturn(questions);
        when(questionRepository.findById(1002L)).thenReturn(Optional.of(q2));

        int before = q2.getVotes();
        questionService.upVoteQuestion(q2, "y");

        assertTrue(questionRepository.findById(q2.getId()).isPresent());
        assertEquals(questionRepository.findById(q2.getId()).get().getVotes() - 1, before);
    }

    @Test
    void markAnswered() {

        questions.add(q2);
        when(questionRepository.findAll()).thenReturn(questions);
        when(questionRepository.findById(1002L)).thenReturn(Optional.of(q2));

        questionService.markAnswered(q2);

        assertTrue(questionRepository.findById(q2.getId()).isPresent());
        assertTrue(questionRepository.findById(q2.getId()).get().isAnswered());
    }

    @Test
    void getAllAnsweredQuestionsInLectureAsQuestion() {

        q1.setAnswered(true);
        questions.add(q1);

        q3.setAnswered(true);
        questions.add(q2);

        when(questionRepository.save(any(Question.class))).thenReturn(new Question());
        when(questionRepository.findAllByAnsweredAndLectureId(true, 22)).thenReturn(questions);

        List<Question> questionList = questionService
                .getAllAnsweredQuestionsInLecture(22);

        assertEquals(2, questionList.size());
        verify(questionRepository, times(1)).findAllByAnsweredAndLectureId(true, 22);
    }

    @Test
    void getAllAnsweredQuestionsInLectureAsString() {

        q1.setAnswered(true);
        questions.add(q1);

        q3.setAnswered(true);
        questions.add(q2);

        when(questionRepository.save(any(Question.class))).thenReturn(new Question());
        when(questionRepository.findAllByAnsweredAndLectureId(true, 22)).thenReturn(questions);

        List<String> questionList = questionService.getAllAnsweredQuestionsInLecture(22)
                .stream()
                .map(Question::getText).collect(Collectors.toList());

        assertEquals(2, questionList.size());
        verify(questionRepository, times(1)).findAllByAnsweredAndLectureId(true, 22);
    }

    @Test
    void replyQuestion() {
        questions.add(q1);
        when(questionRepository.findAll()).thenReturn(questions);
        when(questionRepository.findById(1001L)).thenReturn(Optional.of(q1));

        Question q = questionService.replyQuestion(q1);

        assertTrue(questionRepository.findById(q1.getId()).isPresent());
        assertNotNull(questionRepository.findById(q1.getId()).get().getAnswer());
    }

    @Test
    void hideQuestion() {
        questions.add(q2);
        when(questionRepository.findAll()).thenReturn(questions);
        when(questionRepository.findById(1002L)).thenReturn(Optional.of(q2));

        Question q = questionService.hideQuestion(q2);

        assertTrue(questionRepository.findById(q2.getId()).isPresent());
        assertTrue(questionRepository.findById(q2.getId()).get().getHidden());
    }
}
