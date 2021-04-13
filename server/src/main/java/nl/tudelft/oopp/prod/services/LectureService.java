package nl.tudelft.oopp.prod.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import nl.tudelft.oopp.prod.entities.Lecture;
import nl.tudelft.oopp.prod.entities.Question;
import nl.tudelft.oopp.prod.entities.User;
import nl.tudelft.oopp.prod.repositories.LectureRepository;
import nl.tudelft.oopp.prod.repositories.QuestionRepository;

import nl.tudelft.oopp.prod.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LectureService {

    private final LectureRepository lectureRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    /**
     * Constructor.
     * @param lectureRepository lectureRepository
     * @param questionRepository questionRepository
     * @param userRepository userRepository
     */
    @Autowired
    public LectureService(@Qualifier("LectureRepository") LectureRepository lectureRepository,
                          @Qualifier("QuestionRepository") QuestionRepository questionRepository,
                          @Qualifier("UserRepository") UserRepository userRepository) {
        this.lectureRepository = lectureRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
    }

    /**
     * Tries to retrieve a lecture using a student link.
     * @param publicLink The string with the access link
     * @return The lecture corresponding to the link,
     *         otherwise returns a dummy object.
     */
    public Lecture getLecture(String publicLink) {
        Optional<Lecture> lecture = lectureRepository.findByPublicLink(publicLink);
        if (lecture.isEmpty()) {
            return new Lecture(-1, "Not found");
        }
        return lecture.get();
    }

    /**
     * Tries to retrieve a lecture using a moderator link.
     * @param moderatorLink The string with the access link
     * @return The lecture corresponding to the link,
     *         otherwise returns a dummy object.
     */
    public Lecture getModLecture(String moderatorLink) {
        Optional<Lecture> lecture = lectureRepository.findByModeratorLink(moderatorLink);
        if (lecture.isEmpty()) {
            return new Lecture(-1, "Not found");
        }
        return lecture.get();
    }

    /**
     * Function that returns a list with all lectures.
     * @return all lectures
     */
    public List<Lecture> getAllLectures() {
        return lectureRepository.findAll();
    }

    /**
     * This method CREATES a lecture, but by default it has not started until the lecturer sets so.
     * by taking a lecture object as parameter and
     * saving the lecture using the repository.
     *
     * @param lecture A lecture object
     */
    public Lecture postLecture(Lecture lecture) {
        Optional<Lecture> lectureOptional = lectureRepository.findById(lecture.getId());
        if (lectureOptional.isPresent()) {
            throw new IllegalArgumentException("id already in use");
        }
        return lectureRepository.save(lecture);
    }

    /**
     * This method ends a lecture
     * using the lecture ID. The repository finds
     * the lecture using the ID and removes it.
     * @param id Lecture id
     */
    public Lecture endLecture(long id) {
        Lecture l = lectureRepository.findById(id).orElseThrow(() ->
                new IllegalStateException((
                        "Lecture with id " + id + "does not exist"
                )));
        l.setOngoing(false);
        return lectureRepository.save(l);
    }

    /**
     * This method does start the lecture by
     * setting the isOngoing boolean to true.
     * @param lectureId id of the lecture that needs to be started
     */
    public Lecture startLecture(long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(() ->
                new IllegalStateException((
                        "Lecture with id " + lectureId + "does not exist"
                )));
        lecture.setOngoing(true);
        return lectureRepository.save(lecture);
    }

    /**
     * This method generates a token for the public/moderator links.
     * @return the token as a String
     */
    public String generateToken() {

        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'

        /*
            The string generated will have 12 characters.
            The sheer amount of possibilities that a 12-character
            generated string can have makes the application
            virtually impossible to be "zoom-bombed".
            The only factor that might require intervention from the moderators would be bad
            behaviour from expected users, not some unexpected users
            joining because they misspelled a letter.

            As a comparison, a bot would take:
                *  54ms to crack a 6-character password
                *  42 minutes to crack a 9-character password
                *  3 years to crack a 12-character password.
         */
        int targetStringLength = 12;

        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        //ensure that only unique tokens are sent
        if (lectureRepository.findByPublicLink(generatedString).isPresent()
                || lectureRepository.findByModeratorLink(generatedString).isPresent()) {
            return generateToken();
        }
        return generatedString;
    }

    /**
     * Method that generates a random ID for the lecture.
     * @return the ID generated
     */

    public long generateId() {
        Random generator = new Random();
        String numbers = "0123456789";
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            temp.append(numbers.charAt(generator.nextInt(numbers.length())));
        }
        long id = Long.parseLong(temp.toString());

        //ensure that only unique tokens are sent
        if (lectureRepository.findById(id).isPresent()) {
            return generateId();
        }
        return id;
    }


    /**
     * This method retrieves a list of all Questions from
     * a lecture with a corresponding lectureId.
     *
     * @param id the Id of the lecture
     * @return the list of questions
     */
    public List<Question> getAllQuestions(Long id) {
        return questionRepository.findAllByLectureId(id);
    }

    /**
     * This method retrieves the list of all users
     * in a lecture with its corresponding lectureId.
     *
     * @param lectureId the Id of the lecture
     * @return the list of users.
     */
    public List<User> getAllUsers(long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(() ->
                new IllegalStateException((
                        "Lecture with id " + lectureId + "does not exist"
                )));
        return userRepository.findAllByLecture(lecture);
    }

    /**
     * This method checks whether a lecture is ongoing.
     * @param l the ID of the lecture that needs to be checked
     */
    public boolean isOngoing(long l) {
        Lecture lecture = lectureRepository.findById(l).orElseThrow(() ->
                new IllegalStateException((
                        "Lecture with id " + l + "does not exist"
                )));
        return lecture.isOngoing();
    }

    /**
     * This method deletes all public users of a lecture.
     * @param lectureId ID of the lecture that has ended
     */
    public void deleteUsers(long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(() ->
                new IllegalStateException((
                        "Lecture with id " + lectureId + "does not exist"
                )));
        List<User> users = userRepository.findAllByLecture(lecture);
        for (User user: users) {
            if (!user.isMod()) {
                userRepository.deleteById(user.getId());
            }
        }
    }


    /**
     * Gets logs from file.
     * @param filename file to write logs to.
     * @return Response Entity
     */
    public ResponseEntity<Resource> getLogs(String filename) {

        File file = new File("server/src/main/resources/spring.log");

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = null;

        try {
            resource = new ByteArrayResource(Files.readAllBytes(path));
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);

    }

    /**
     * This method checks if the lecture has been rate limited.
     * @param lectureId lectureId
     * @return boolean
     */
    public boolean getRateLimited(long lectureId) {

        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(() ->
                new IllegalStateException((
                        "Lecture with id " + lectureId + "does not exist"
                )));

        return lecture.isRateLimited();

    }

    /**
     * This method enables a lecture's rate limit.
     * @param lectureId lectureId
     * @return boolean
     */
    public boolean enableRateLimit(long lectureId) {

        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(() ->
                new IllegalStateException((
                        "Lecture with id " + lectureId + "does not exist"
                )));

        lecture.setRateLimited(true);
        lectureRepository.save(lecture);
        return true;

    }

    /**
     * This method disables a lecture's rate limit.
     * @param lectureId lectureId
     * @return boolean
     */
    public boolean disableRateLimit(long lectureId) {

        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(() ->
                new IllegalStateException((
                        "Lecture with id " + lectureId + "does not exist"
                )));

        lecture.setRateLimited(false);
        lectureRepository.save(lecture);
        return false;

    }

}

