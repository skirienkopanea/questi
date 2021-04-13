package nl.tudelft.oopp.prod.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import nl.tudelft.oopp.prod.entities.BannedUser;
import nl.tudelft.oopp.prod.entities.Lecture;
import nl.tudelft.oopp.prod.entities.User;
import nl.tudelft.oopp.prod.repositories.BannedUserRepository;
import nl.tudelft.oopp.prod.repositories.LectureRepository;
import nl.tudelft.oopp.prod.repositories.UserRepository;
import nl.tudelft.oopp.prod.services.BannedUserService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.web.servlet.MockMvc;


@DataJpaTest
@RunWith(MockitoJUnitRunner.class)
public class BannedUserServiceTest {


    @InjectMocks
    BannedUserService bannedUserService;

    @Mock
    BannedUserRepository bannedUserRepository;

    @Mock
    LectureRepository lectureRepository;

    @Mock
    UserRepository userRepository;

    private BannedUser b1;
    private BannedUser b2;
    private BannedUser b3;
    private List<BannedUser> bannedUsers;
    private Date date;
    private Lecture l1;
    private User user;

    /**
     * Initializes the test prereqs.
     */
    @BeforeEach
    public void beforeEach() {

        bannedUserService = new BannedUserService(bannedUserRepository,
                lectureRepository, userRepository);

        this.date = new Date(2021, 3, 31);

        this.l1 =  new Lecture(10L, "modlink1", "studlink1",
                "cse1", true, date, date);

        user = new User(1L, "Alex", false, l1, l1.getId());

        this.b1 = new BannedUser(1L, 10L, 1L);
        this.b2 = new BannedUser(2L, 11L, 1L);
        this.b3 = new BannedUser(3L, 10L, 2L);

        when(bannedUserRepository.findById(b1.getId())).thenReturn(
                Optional.ofNullable(b1)
        );

        when(bannedUserRepository.findById(b2.getId())).thenReturn(
                Optional.ofNullable(b2)
        );

        when(bannedUserRepository.findById(b3.getId())).thenReturn(
                Optional.ofNullable(b3)
        );

        when(bannedUserRepository.save(b1)).thenReturn(b1);
        when(bannedUserRepository.save(b2)).thenReturn(b2);
        when(bannedUserRepository.save(b3)).thenReturn(b3);

    }

    @Test
    void checkIfUserBannedTest() {

        when(bannedUserRepository
                .findBannedUserByUserIdAndLectureId(b1.getUserId(), b1.getLectureId()))
                .thenReturn(Optional.of(b1));

        boolean result = bannedUserService.checkIfUserBanned(b1.getLectureId(), b1.getUserId());

        assertTrue(result);

    }

    @Test
    void banUserTest() {

        when(lectureRepository.findById(l1.getId())).thenReturn(Optional.of(l1));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        bannedUserService.banUser(b1.getLectureId(), b1.getUserId());

        assertTrue(bannedUserRepository.findById(b1.getId()).isPresent());

    }


    @Test
    void getAllBannedUsersInLectureTest() {

        bannedUsers = List.of(b1, b3);

        when(lectureRepository.findById(l1.getId())).thenReturn(Optional.of(l1));
        when(bannedUserRepository.findBannedUsersByLectureId(l1.getId()))
                .thenReturn(List.of(b1, b3));

        assertEquals(bannedUsers, bannedUserService.getAllBannedUsersInLecture(l1.getId()));

    }

}
