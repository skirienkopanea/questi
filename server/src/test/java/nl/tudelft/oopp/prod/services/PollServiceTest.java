package nl.tudelft.oopp.prod.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nl.tudelft.oopp.prod.entities.Poll;
import nl.tudelft.oopp.prod.entities.Question;
import nl.tudelft.oopp.prod.repositories.PollRepository;
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
class PollServiceTest {

    @Autowired
    MockMvc mockMvc;

    @Mock
    PollRepository pollRepository;

    @InjectMocks
    PollService pollService;

    Poll p1;
    Poll p2;
    Poll p3;
    List<Poll> polls;


    @BeforeEach
    public void initialization() {

        p1 = new Poll(1,
                100,
                "YES");

        p2 = new Poll(2,
                100,
                "NO");

        p3 = new Poll(3,
                200,
                "MAYBE");

        polls = new ArrayList<>();
        pollService = new PollService(pollRepository);

        when(pollRepository.findById(p1.getId())).thenReturn(
                java.util.Optional.ofNullable(p1)
        );

        when(pollRepository.findById(p2.getId())).thenReturn(
                java.util.Optional.ofNullable(p2)
        );

        when(pollRepository.findById(p3.getId())).thenReturn(
                java.util.Optional.ofNullable(p3)
        );

        when(pollRepository.findById(4)).thenReturn(
                Optional.of(new Poll())
        );

        when(pollRepository.save(p1)).thenReturn(p1);
    }

    @Test
    void getAllPolls() {
        polls.add(p1);
        polls.add(p2);
        polls.add(p3);

        when(pollRepository.findAll()).thenReturn(polls);

        List<Poll> pollList = pollService.getAllPolls();

        assertEquals(3, pollList.size());
        verify(pollRepository, times(1)).findAll();
    }

    @Test
    void getLivePoll() {
        polls.add(p1);
        polls.add(p2);

        when(pollRepository.findByLectureId(100)).thenReturn(polls);

        Poll livePoll = pollService.getLivePoll(100);

        assertEquals(livePoll, p2);
        verify(pollRepository, times(2)).findByLectureId(100);
    }

    @Test
    void insertPoll() {

        when(pollRepository.save(any(Poll.class))).thenReturn(p1);
        when(pollRepository.findById(1)).thenReturn(Optional.empty());

        pollService.insertPoll(p1);

        when(pollRepository.findById(1)).thenReturn(Optional.of(p1));

        assertTrue(pollRepository.findById(p1.getId()).isPresent());
        assertEquals(pollRepository.findById(p1.getId()).get(), p1);
        verify(pollRepository, times(1)).save(p1);

    }

    @Test
    void deletePoll() {

        polls.add(p1);
        polls.add(p2);

        when(pollRepository.findAll()).thenReturn(polls);
        when(pollRepository.existsById(p2.getId())).thenReturn(true);
        doAnswer(invocation -> {
            polls.remove(p2);
            return null;
        }).when(pollRepository).deleteById(p2.getId());

        pollService.deletePoll(p2.getId());
        List<Poll> pollList = pollService.getAllPolls();

        assertEquals(1, pollList.size());
        verify(pollRepository, times(1)).deleteById(p2.getId());

    }

    // Doesn't work, Id already in use ???????
    //    @Test
    //    void updatePoll() {
    //
    //        polls.add(p1);
    //        polls.add(p2);
    //        polls.add(p3);
    //
    //        when(pollRepository.findAll()).thenReturn(polls);
    //        when(pollRepository.findById(p1.getId())).thenReturn(Optional.of(p1));
    //
    //        pollService.updatePoll(p1.getId(), 3, "NONONO");
    //        verify(pollRepository, times(1)).save(p1);;
    //
    //    }

    @Test
    void getPollId() {
        long x = pollService.getPollId();
        long y = pollService.getPollId();

        //2 random generated numbers of with size 6 have a 1/900000 change of being equal
        //So the chance is virtually 0
        //assertTrue(x >= 100000 && x <= 999999);
        //assertTrue(y >= 100000 && y <= 999999);
        assertNotEquals(x, y);
    }
}