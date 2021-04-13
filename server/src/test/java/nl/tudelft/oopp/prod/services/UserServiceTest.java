package nl.tudelft.oopp.prod.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import nl.tudelft.oopp.prod.entities.Lecture;
import nl.tudelft.oopp.prod.entities.User;
import nl.tudelft.oopp.prod.repositories.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
class UserServiceTest {

    User u1;
    User u2;
    User u3;
    Lecture lecture1;
    Lecture lecture2;
    List<User> users;

    @Autowired
    MockMvc mockMvc;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;


    /**
     * Before each.
     */
    @BeforeEach
    public void beforeEach() {

        users = new ArrayList<>();

        lecture1 = new Lecture(
                10,
                "moderator",
                "student",
                "test lecture",
                true,
                new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis())
        );

        lecture2 = new Lecture(
                20,
                "link",
                "knil",
                "test lecture 2",
                true,
                new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis())
        );

        u1 = new User(
                1,
                10,
                "user1",
                false);

        u2 = new User(
                2,
                10,
                "user2",
                false);

        u3 = new User(
                3,
                20,
                "user3",
                true);

        userService = new UserService(userRepository);

        when(userRepository.findById(42)).thenReturn(
                Optional.of(new User())
        );

        when(userRepository.save(u1)).thenReturn(u1);
    }

    @Test
    public void testNotNull() {
        assertNotNull(userService.getUser(u1.getId()));
    }

    @Test
    public void testNotEquals() {
        assertNotEquals(userService.getUser(42), u1);
    }

    @Test
    void getUser() {

        when(userRepository.findById(u1.getId())).thenReturn(Optional.of(u1));

        User tempUser = userService.getUser(u1.getId());

        assertNotNull(tempUser);
        assertEquals(tempUser, u1);
    }

    @Test
    void getAllUsers() {

        users.add(u1);
        users.add(u2);
        users.add(u3);

        when(userRepository.findAll()).thenReturn(users);

        List<User> userList = userService.getAllUsers();

        assertEquals(3, userList.size());
        verify(userRepository, times(1)).findAll();

    }

    @Test
    void getAllUsersInLecture() {

        users.add(u1);
        users.add(u2);

        when(userRepository.findAllByLecture(lecture1)).thenReturn(users);

        List<User> userList = userService.getAllUsersInLecture(lecture1);

        assertEquals(2, userList.size());
        verify(userRepository, times(1)).findAllByLecture(lecture1);

    }

    @Test
    void getNewUserId() {

        long x = userService.getUserId();
        long y = userService.getUserId();

        //2 random generated numbers of with size 6 have a 1/900000 change of being equal
        //So the chance is virtually 0
        //assertTrue(x >= 100000 && x <= 999999);
        //assertTrue(y >= 100000 && y <= 999999);
        assertNotEquals(x, y);
    }


    @Test
    void insertUser() {

        when(userRepository.save(any(User.class))).thenReturn(u1);
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        userService.insertUser(u1);

        when(userRepository.findById(1)).thenReturn(Optional.of(u1));

        assertTrue(userRepository.findById(u1.getId()).isPresent());
        assertEquals(userRepository.findById(u1.getId()).get(), u1);
        verify(userRepository, times(1)).save(u1);

    }

    @Test
    public void insertUser22() {
        assertThrows(IllegalArgumentException.class, () -> {
            when(userRepository.save(any(User.class))).thenReturn(u1);
            when(userRepository.findById(1)).thenReturn(Optional.empty());

            userService.insertUser(u1);

            when(userRepository.findById(1)).thenReturn(Optional.of(u1));
            userService.insertUser(u1);
        });
    }

    @Test
    void deleteUser() {

        users.add(u1);
        users.add(u2);

        when(userRepository.findAll()).thenReturn(users);
        when(userRepository.existsById(u2.getId())).thenReturn(true);
        doAnswer(invocation -> {
            users.remove(u2);
            return null;
        }).when(userRepository).deleteById(u2.getId());

        userService.deleteUser(u2.getId());
        List<User> userList = userService.getAllUsers();

        assertEquals(1, userList.size());
        verify(userRepository, times(1)).deleteById(u2.getId());
    }

    @Test
    public void deleteUser2() {
        assertThrows(IllegalStateException.class, () -> {
            when(userRepository.findById(100)).thenReturn(Optional.empty());

            userService.deleteUser(100);
        });
    }

}
