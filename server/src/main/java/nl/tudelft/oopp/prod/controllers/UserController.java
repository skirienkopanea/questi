package nl.tudelft.oopp.prod.controllers;

import java.util.List;

import nl.tudelft.oopp.prod.entities.Lecture;
import nl.tudelft.oopp.prod.entities.User;
import nl.tudelft.oopp.prod.repositories.UserRepository;
import nl.tudelft.oopp.prod.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    /**
     * Instantiates a new User controller and autowires it.
     *
     * @param userService the user service
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Gets a user by id.
     *
     * @param id the id of the user
     * @return the user
     */
    @GetMapping("user")
    public User getUser(@RequestParam long id) {
        return userService.getUser(id);
    }

    /**
     * Gets all users in the database.
     *
     * @return A list of all users in the database.
     */
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * Gets all users in a specific lecture.
     *
     * @param lecture the lecture targeted
     * @return A list of all users in lecture
     */
    @GetMapping("/getLecture")
    public List<User> getAllUsersInLecture(@RequestBody Lecture lecture) {
        return userService.getAllUsersInLecture(lecture);
    }

    /**
     * Generates a new ID for user.
     * @return the new randomly-generated ID
     */
    @GetMapping("/userid")
    public long getNewUserId() {
        return userService.getUserId();
    }

    /**
     * Insert a new user into the database.
     * @param user to be inserted
     */
    @PostMapping(path = "/join")
    public User insertUser(@RequestBody User user) {
        return userService.insertUser(user);
    }

    /**
     * Delete a user by id.
     * @param userId the id of the user to be deleted
     */
    @DeleteMapping(path = "/delete/{userId}")
    public void deleteUser(@PathVariable("userId") long userId) {
        userService.deleteUser(userId);
    }


}
