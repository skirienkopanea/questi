package nl.tudelft.oopp.prod.controllers;

import java.util.List;
import java.util.stream.Collectors;

import nl.tudelft.oopp.prod.entities.Question;
import nl.tudelft.oopp.prod.services.QuestionService;

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
@RequestMapping(path = "question")
public class QuestionController {

    private final QuestionService questionService;

    /**
     * Instantiates a new Question controller and autowires it.
     *
     * @param questionService the question service
     */
    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    /**
     * Gets a list of all the questions in server.
     *
     * @return A list of all the questions
     */
    @GetMapping
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    /**
     * Gets a list of all the questions in a specific lecture.
     *
     * @param id the id of the lecture
     * @return A list of all the questions in a lecture.
     */
    @GetMapping("/get/{id}")
    public List<Question> getAllQuestionsInLecture(@PathVariable long id) {
        return questionService.getAllQuestionsInLecture(id);
    }

    /**
     * Performs a POST request - inserts a question into the database.
     *
     * @param question the question to be inserted
     * @return the question
     */
    @PostMapping("/askQuestion")
    public Question insertQuestion(@RequestBody Question question) {
        return questionService.insertQuestion(question);
    }

    /**
     * Performs a DELETE request - deletes a question from the database.
     *
     * @param questionId the id of the question to be deleted
     */
    @DeleteMapping(path = "/delete/{questionId}")
    public void deleteQuestion(@PathVariable("questionId") long questionId) {
        questionService.deleteQuestion(questionId);
    }

    /**
     * This method retrieves the text of already answered questions
     * in a specific lecture.
     *
     * @param id the id of the lecture targeted
     * @return a list of strings with answered questions in the lecture
     */
    @GetMapping("/get/{id}/answered")
    public List<String> getAllAnsweredQuestionsInLectureAsString(@PathVariable long id) {
        return questionService.getAllAnsweredQuestionsInLecture(id)
                .stream()
                .map(Question::getText).collect(Collectors.toList());
    }

    // "http://localhost:8080/question/upVote/{userAlias}"
    @PutMapping(path = "/upVote/{userAlias}")
    public Question upVoteQuestion(@RequestBody Question question,
                                   @PathVariable String userAlias) {
        return questionService.upVoteQuestion(question, userAlias);
    }

    // "http://localhost:8080/question/editQuestion"
    @PutMapping(path = "/editQuestion")
    public Question editQuestion(@RequestBody Question question) {
        return questionService.editQuestion(question);
    }

    // "http://localhost:8080/question/replyQuestion"
    @PutMapping(path = "/replyQuestion")
    public Question replyQuestion(@RequestBody Question question) {
        return questionService.replyQuestion(question);
    }

    // "http://localhost:8080/question/answered"
    @PutMapping(path = "/answered")
    public Question markAnswered(@RequestBody Question question) {
        return questionService.markAnswered(question);
    }

    // "http://localhost:8080/question/hide"
    @PutMapping(path = "/hide")
    public Question hideQuestion(@RequestBody Question question) {
        return questionService.hideQuestion(question);
    }

}






















