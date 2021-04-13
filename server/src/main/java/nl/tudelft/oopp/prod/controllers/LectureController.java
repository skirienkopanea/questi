package nl.tudelft.oopp.prod.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import nl.tudelft.oopp.prod.entities.Lecture;
import nl.tudelft.oopp.prod.entities.Question;
import nl.tudelft.oopp.prod.entities.User;
import nl.tudelft.oopp.prod.services.LectureService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
@RequestMapping(path = "lecture")
public class LectureController {

    private final LectureService lectureService;

    /**
     * Autowires the controller through the constructor.
     *
     * @param lectureService the lecture service
     */
    @Autowired
    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }


    // GET REQUESTS

    /**
     * Gets all questions in a specific lecture.
     *
     * @param lectureId the lecture id
     * @return all questions in a lecture
     */

    @GetMapping("/getAllQuestions/{lectureId}")
    public List<Question> getAllQuestions(@PathVariable("lectureId") Long lectureId) {
        return lectureService.getAllQuestions(lectureId);
    }

    /**
     * Return a lecture that can be accessed to a String link.
     * Used for the student view.
     *
     * @param link a String specific to a lecture
     * @return the lecture
     */
    @GetMapping(path = "/get")
    public Lecture getLecture(@RequestParam String link) {
        return lectureService.getLecture(link);
    }

    /**
     * Return a lecture that can be accessed to a String link.
     * Used for the moderator link view.
     *
     * @param link the link
     * @return the mod lecture
     */
    @GetMapping(path = "/getm")
    public Lecture getModLecture(@RequestParam String link) {
        return lectureService.getModLecture(link);
    }

    /**
     * Gets all users in a specific lecture.
     *
     * @param lectureId the lecture id
     * @return all users in a lecture
     */
    @GetMapping(path = "/allUsers/{lectureId}")
    public List<User> getAllUsers(@PathVariable long lectureId) {
        return lectureService.getAllUsers(lectureId);
    }

    /**
     * Gets all lectures stored in the database.
     *
     * @return All lectures
     */
    @GetMapping
    public List<Lecture> getAllLectures() {
        return lectureService.getAllLectures();
    }

    /**
     * Generate a new id for the lecture.
     *
     * @return A new id
     */
    @GetMapping("/make/id")
    public long getNewId() {
        return lectureService.generateId();
    }

    /**
     * Generate a new random link in order to access a lecture.
     *
     * @return the token as a String
     */
    @GetMapping("/make/token")
    public String getNewToken() {
        return lectureService.generateToken();
    }

    @GetMapping(path = "/ongoing")
    public boolean getOngoing(@RequestParam long link) {
        return lectureService.isOngoing(link);
    }

    /**
     * POST Request for lecture.
     *
     * @param lecture to be posted
     */

    @PostMapping(path = "/post")
    public Lecture postLecture(@RequestBody Lecture lecture) {
        return lectureService.postLecture(lecture);
    }

    @PutMapping(path = "/start")
    public Lecture startLecture(@RequestBody String id) {
        return lectureService.startLecture(Long.parseLong(id));
    }

    /**
     * PUT Request to end a lecture.
     * @param id of the lecture to be ended
     */
    @PutMapping(path = "/stop")
    public Lecture endLecture(@RequestBody String id) {
        return lectureService.endLecture(Long.parseLong(id));
    }

    @DeleteMapping(path = "/deleteusers/{lectureId}")
    public void deleteUser(@PathVariable("lectureId") long lectureId) {
        lectureService.deleteUsers(lectureId);
    }

    @GetMapping(path = "/getlog/{filename}")
    public ResponseEntity<Resource> getLogs(@PathVariable String filename) {
        return lectureService.getLogs(filename);
    }

    @GetMapping(path = "/isratelimited/{lectureId}")
    public boolean getRateLimited(@PathVariable long lectureId) {
        return lectureService.getRateLimited(lectureId);
    }

    @PutMapping(path = "/enableratelimit/{lectureId}")
    public boolean enableRateLimit(@PathVariable long lectureId) {
        return lectureService.enableRateLimit(lectureId);
    }

    @PutMapping(path = "/disableratelimit/{lectureId}")
    public boolean disableRateLimit(@PathVariable long lectureId) {
        return lectureService.disableRateLimit(lectureId);
    }

}
