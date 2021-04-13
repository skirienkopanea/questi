package nl.tudelft.oopp.prod.services;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import nl.tudelft.oopp.prod.entities.Lecture;
import nl.tudelft.oopp.prod.entities.User;
import nl.tudelft.oopp.prod.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * The type User service.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    /**
     * Instantiates a new User service and autowires it.
     *
     * @param userRepository the user repository
     */
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Searches in the database for a user with a specific id and retrieves it.
     *
     * @param id the id of the user
     * @return the user
     */
    public User getUser(long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            return new User();
        }
        return user.get();
    }

    /**
     * Method returns a list with all the users.
     * @return a list of users
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Method returns all the users in a specific lecture.
     * @param lecture in which to search the users
     * @return A list of users in a specific lecture
     */
    public List<User> getAllUsersInLecture(Lecture lecture) {
        return userRepository.findAllByLecture(lecture);
    }

    /**
     * This method takes the user and the lecture ID.
     * It finds the corresponding lecture object and
     * sets it as a variable and adds user to the set
     * of Users. (FK constraints)
     * @param user The user that needs to be saved
     */
    public User insertUser(User user) {
        Optional<User> userOptional = userRepository.findById(user.getId());

        //If the Id is already used, throw an exception
        if (userOptional.isPresent()) {
            throw new IllegalArgumentException("id already in use");
        }

        return userRepository.save(user);

    }

    /**
     * This removes a user from database
     * using the ID of the user.
     * @param userId The ID of the user
     */
    public void deleteUser(long userId) {
        boolean exists = userRepository.existsById(userId);

        //If the Id does not exist, throw an exception
        if (!exists) {
            throw new IllegalStateException("User with id " + userId + " does not exist.");
        }
        userRepository.deleteById(userId);
    }

    /**
     * This method generates a new random user ID.
     */
    public long getUserId() {
        Random generator = new Random();
        String numbers = "0123456789";
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            temp.append(numbers.charAt(generator.nextInt(numbers.length())));
        }
        long id = Long.parseLong(temp.toString());
        if (userRepository.findById(id).isPresent()) {
            return getUserId();
        }
        return id;
    }
}
