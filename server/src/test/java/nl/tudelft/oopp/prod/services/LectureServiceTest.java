package nl.tudelft.oopp.prod.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import nl.tudelft.oopp.prod.entities.Lecture;
import nl.tudelft.oopp.prod.entities.Question;
import nl.tudelft.oopp.prod.entities.User;
import nl.tudelft.oopp.prod.repositories.LectureRepository;
import nl.tudelft.oopp.prod.repositories.QuestionRepository;
import nl.tudelft.oopp.prod.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
public class LectureServiceTest {

    @Autowired
    MockMvc mockMvc;

    @Mock
    LectureRepository lectureRepository;

    @Mock
    QuestionRepository questionRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    LectureService lectureService;

    Lecture l1;
    Lecture l2;
    Lecture l3;
    List<Lecture> lectures;
    List<Question> questions;
    List<User> users;
    Question q1;
    Question q2;
    Question q3;
    User u1;
    User u2;
    User u3;

    /**
     * Initialization.
     */
    @BeforeEach
    public void initialization() {

        l1 = new Lecture(
                1,
                "firstMOD",
                "firstSTUDENT",
                "Introduction",
                true,
                new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis())
        );

        l2 = new Lecture(
                2,
                "secondMOD",
                "secondSTUDENT",
                "Summary",
                true,
                new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis())
        );

        l3 = new Lecture(
                3,
                "thirdMOD",
                "thirdSTUDENT",
                "Ending",
                true,
                new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis())
        );

        q1 = new Question(1001,
                1,
                14,
                "AAAAAAAAAAAA",
                100,
                "nonononoo");

        q2 = new Question(1002,
                2,
                55,
                "BBBBBBBBBBBB",
                100,
                null);

        q3 = new Question(1003,
                1,
                "CCCC",
                32322,
                "DDDDD");

        u1 = new User(
                14,
                1,
                "user1",
                false);

        u2 = new User(
                55,
                2,
                "user2",
                false);

        u3 = new User(
                99,
                2,
                "hunter2",
                false);

        lectures = new ArrayList<>();
        questions = new ArrayList<>();
        users = new ArrayList<>();
        lectureService = new LectureService(lectureRepository, questionRepository, userRepository);

        when(lectureRepository.findById(l1.getId())).thenReturn(
                Optional.ofNullable(l1)
        );

        when(lectureRepository.findById(l2.getId())).thenReturn(
                Optional.ofNullable(l2)
        );

        when(lectureRepository.findById(l3.getId())).thenReturn(
                Optional.ofNullable(l3)
        );

        when(lectureRepository.save(l1)).thenReturn(l1);
        when(lectureRepository.save(l2)).thenReturn(l2);
        when(lectureRepository.save(l3)).thenReturn(l3);
    }

    //Duplicate of a method in questions, still needs to be tested

    @Test
    public void getAllQuestionsInLecture() {

        questions.add(q1);
        questions.add(q3);

        when(questionRepository.findAllByLectureId(1)).thenReturn(questions);

        List<Question> questionList = lectureService.getAllQuestions(1L);

        assertEquals(2, questionList.size());
        verify(questionRepository, times(1)).findAllByLectureId(1);

    }

    @Test
    public void getLectureByStudentLink() {

        when(lectureRepository.findByPublicLink(l1.getPublicLink())).thenReturn(Optional.of(l1));

        Lecture tempLecture = lectureService.getLecture("firstSTUDENT");

        assertNotNull(tempLecture);
        assertEquals(tempLecture, l1);

    }

    @Test
    public void getLectureByStudentLink2() {

        when(lectureRepository.findByPublicLink(l1.getPublicLink())).thenReturn(Optional.of(l1));

        Lecture tempLecture = lectureService.getLecture("tzeapa");

        assertEquals(tempLecture.getId(), -1);
    }

    @Test
    public void getLectureByModeratorLink() {
        when(lectureRepository.findByModeratorLink(l2.getModeratorLink()))
                .thenReturn(Optional.of(l2));

        Lecture tempLecture = lectureService.getModLecture("secondMOD");

        assertNotNull(tempLecture);
        assertEquals(tempLecture, l2);
    }

    @Test
    public void getLectureByModeratorLink2() {
        when(lectureRepository.findByModeratorLink(l2.getModeratorLink()))
                .thenReturn(Optional.of(l2));

        Lecture tempLecture = lectureService.getModLecture("secondMOOOOOD");

        assertEquals(tempLecture.getId(), -1);
    }

    //Duplicate of a method in users, still needs to be tested
    @Test
    public void getAllUsers() {

        users.add(u2);
        users.add(u3);

        when(userRepository.findAllByLecture(l2)).thenReturn(users);
        List<User> userList = lectureService.getAllUsers(l2.getId());

        assertEquals(2, userList.size());
        verify(userRepository, times(1)).findAllByLecture(l2);
    }

    @Test
    public void getAllUsers2() {
        assertThrows(IllegalStateException.class, () -> {
            users.add(u2);
            users.add(u3);

            when(userRepository.findAllByLecture(l2)).thenReturn(users);
            when(lectureRepository.findById(l2.getId())).thenReturn(Optional.empty());
            when(userRepository.findById(u2.getId())).thenReturn(Optional.empty());
            List<User> userList = lectureService.getAllUsers(l2.getId());
        });
    }

    @Test
    public void getAllLectures() {

        lectures.add(l1);
        lectures.add(l2);
        lectures.add(l3);

        when(lectureRepository.findAll()).thenReturn(lectures);

        List<Lecture> lectureList = lectureService.getAllLectures();

        assertEquals(3, lectureList.size());
        verify(lectureRepository, times(1)).findAll();
    }

    @Test
    public void getNewId() {
        long x = lectureService.generateId();
        long y = lectureService.generateId();

        //Check if the randomizer works, chances of generating the same number twice
        //are virtually 0

        assertNotEquals(x, y);
    }

    @Test
    public void getNewToken() {

        String x = lectureService.generateToken();
        String y = lectureService.generateToken();

        //Check if the randomizer works, chances of generating the same string twice
        //are virtually 0

        assertNotEquals(x, y);
    }

    //PLEASE CHECK, TEST LOOKS AWFUL
    @Test
    public void insertLecture() {

        when(lectureRepository.save(any(Lecture.class))).thenReturn(l1);
        when(lectureRepository.findById(1)).thenReturn(Optional.empty());

        lectureService.postLecture(l1);

        when(lectureRepository.findById(1)).thenReturn(Optional.of(l1));

        assertTrue(lectureRepository.findById(l1.getId()).isPresent());
        assertEquals(lectureRepository.findById(l1.getId()).get(), l1);
        verify(lectureRepository,times(1)).save(l1);
    }

    @Test
    public void insertLecture2() {
        assertThrows(IllegalArgumentException.class, () -> {
            when(lectureRepository.save(any(Lecture.class))).thenReturn(l1);
            when(lectureRepository.findById(1)).thenReturn(Optional.empty());

            lectureService.postLecture(l1);

            when(lectureRepository.findById(1)).thenReturn(Optional.of(l1));
            lectureService.postLecture(l1);
        });
    }


    @Test
    public void endLecture() {

        l1.setOngoing(true);

        lectures.add(l1);
        when(lectureRepository.findAll()).thenReturn(lectures);
        when(lectureRepository.findById(1)).thenReturn(Optional.of(l1));

        lectureService.endLecture(l1.getId());

        assertTrue(lectureRepository.findById(l1.getId()).isPresent());
        assertFalse(lectureRepository.findById(l1.getId()).get().isOngoing());
        verify(lectureRepository, times(1)).save(l1);
    }

    @Test
    public void endLecture2() {
        assertThrows(IllegalStateException.class, () -> {
            l1.setOngoing(true);

            lectures.add(l1);
            when(lectureRepository.findAll()).thenReturn(lectures);
            when(lectureRepository.findById(1)).thenReturn(Optional.empty());

            lectureService.endLecture(l1.getId());
        });
    }

    @Test
    public void startLecture() {

        l1.setOngoing(false);

        lectures.add(l1);
        when(lectureRepository.findAll()).thenReturn(lectures);
        when(lectureRepository.findById(1)).thenReturn(Optional.of(l1));

        lectureService.startLecture(l1.getId());

        assertTrue(lectureRepository.findById(l1.getId()).isPresent());
        assertTrue(lectureRepository.findById(l1.getId()).get().isOngoing());
        verify(lectureRepository, times(1)).save(l1);

    }

    @Test
    public void startLecture2() {
        assertThrows(IllegalStateException.class, () -> {
            l1.setOngoing(false);

            lectures.add(l1);
            when(lectureRepository.findAll()).thenReturn(lectures);
            when(lectureRepository.findById(1)).thenReturn(Optional.empty());

            lectureService.startLecture(l1.getId());
        });
    }

    @Test
    public void isOngoing() {
        l1.setOngoing(true);

        lectures.add(l1);
        when(lectureRepository.findAll()).thenReturn(lectures);
        when(lectureRepository.findById(1)).thenReturn(Optional.of(l1));

        assertTrue(lectureService.isOngoing(1));
    }

    @Test
    public void isOngoing2() {
        assertThrows(IllegalStateException.class, () -> {
            l1.setOngoing(true);

            lectures.add(l1);
            when(lectureRepository.findAll()).thenReturn(lectures);
            when(lectureRepository.findById(1)).thenReturn(Optional.empty());

            lectureService.isOngoing(1);
        });
    }

    @Test
    public void deleteUsers() {
        users.add(u2);
        users.add(u3);

        when(lectureRepository.findById(2)).thenReturn(Optional.of(l1));
        when(userRepository.findAllByLecture(l2)).thenReturn(users);

        lectureService.deleteUsers(2);
        assertEquals(lectureService.getAllUsers(2).size(), 0);
    }

    @Test
    public void deleteUsers2() {
        assertThrows(IllegalStateException.class, () -> {
            users.add(u2);
            users.add(u3);

            when(lectureRepository.findById(2)).thenReturn(Optional.empty());
            when(userRepository.findAllByLecture(l2)).thenReturn(users);

            lectureService.deleteUsers(2);
        });
    }
}
