package nl.tudelft.oopp.prod.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nl.tudelft.oopp.prod.entities.PollOption;
import nl.tudelft.oopp.prod.entities.User;
import nl.tudelft.oopp.prod.repositories.PollOptionRepository;
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
class PollOptionServiceTest {

    PollOption o1;
    PollOption o2;
    PollOption o3;
    List<PollOption> options;

    @Autowired
    MockMvc mockMvc;

    @Mock
    PollOptionRepository pollOptionRepository;

    @InjectMocks
    PollOptionService pollOptionService;

    @BeforeEach
    public void initialize() {

        options = new ArrayList<>();
        pollOptionService = new PollOptionService(pollOptionRepository);

        o1 = new PollOption(1,
                1,
                "YES",
                2509);

        o2 = new PollOption(2,
                1,
                "NO",
                1101);

        o3 = new PollOption(3,
                2,
                "NEVER",
                555);

        when(pollOptionRepository.save(o1)).thenReturn(o1);

    }

    @Test
    void getAllPollOptions() {
        options.add(o1);
        options.add(o2);
        options.add(o3);

        when(pollOptionRepository.findAll()).thenReturn(options);

        List<PollOption> optionList = pollOptionService.getAllPollOptions();

        assertEquals(3, optionList.size());
        verify(pollOptionRepository, times(1)).findAll();
    }

    @Test
    void getAllPollOptionsByPollId() {

        options.add(o1);
        options.add(o2);

        when(pollOptionRepository.findAllByPollId(1)).thenReturn(options);

        List<PollOption> optionList = pollOptionService.getAllPollOptionsByPollId(1);

        assertEquals(2, optionList.size());
        verify(pollOptionRepository, times(1)).findAllByPollId(1);
    }
    //
    //    @Test
    //    void insertPollOption() {
    //
    //        when(pollOptionRepository.save(any(PollOption.class))).thenReturn(o1);
    //
    //        pollOptionService.insertPollOption(o1);
    //
    //        assertTrue(pollOptionRepository.findById(o1.getId()).isPresent());
    //        assertEquals(pollOptionRepository.findById(o1.getId()).get(), o1);
    //        verify(pollOptionRepository, times(1)).save(o1);
    //    }
    //
    //    @Test
    //    void deletePollOption() {
    //
    //        options.add(o1);
    //        options.add(o2);
    //
    //        when(pollOptionRepository.findAll()).thenReturn(options);
    //        when(pollOptionRepository.findAllByPollId(1)).thenReturn(options);
    //        when(pollOptionRepository.existsById(o1.getId())).thenReturn(true);
    //        doAnswer(invocation -> {
    //            options.remove(o2);
    //            return null;
    //        }).when(pollOptionRepository).deleteById(o2.getId());
    //
    //        pollOptionService.deletePollOption(o2.getId());
    //        List<PollOption> optionList = pollOptionService.getAllPollOptions();
    //
    //        assertEquals(1, optionList.size());
    //        verify(pollOptionRepository, times(1)).deleteById(o2.getId());
    //
    //    }
    //
    //    @Test
    //    void downVote() {
    //        options.add(o1);
    //        options.add(o2);
    //
    //        when(pollOptionRepository.findAllByPollId(1)).thenReturn(options);
    //        when(pollOptionRepository.save(any(PollOption.class))).thenReturn(o1);
    //
    //        assertEquals(o1.getScore(),
    //                pollOptionService.downVote(o1.getPollId() + "/1") + 1);
    //    }
    //
    //    @Test
    //    void upVote() {
    //        options.add(o1);
    //        options.add(o2);
    //
    //        when(pollOptionRepository.findAllByPollId(1)).thenReturn(options);
    //        when(pollOptionRepository.save(any(PollOption.class))).thenReturn(o1);
    //
    //        assertEquals(o1.getScore(),
    //                pollOptionService.upVote(o1.getPollId() + "/1") - 1);
    //    }
    //
    //    @Test
    //    void getVotes() {
    //        options.add(o1);
    //        options.add(o2);
    //
    //        when(pollOptionRepository.findAllByPollId(1)).thenReturn(options);
    //        when(pollOptionRepository.save(any(PollOption.class))).thenReturn(o1);
    //
    //        assertEquals(o1.getScore(),
    //                pollOptionService.getVotes(o1.getPollId() + "/1"));
    //    }

    @Test
    void getAmount() {
        options.add(o1);
        options.add(o2);

        when(pollOptionRepository.findAllByPollId(1)).thenReturn(options);

        assertEquals(2, pollOptionService.getAmount(1));
    }
}