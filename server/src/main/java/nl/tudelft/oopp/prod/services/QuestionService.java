package nl.tudelft.oopp.prod.services;

import java.util.List;
import java.util.Optional;

import nl.tudelft.oopp.prod.entities.Question;
import nl.tudelft.oopp.prod.repositories.QuestionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


/**
 * The type Question service.
 */
@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    /**
     * Instantiates a new Question service and autowires it.
     *
     * @param questionRepository the question repository
     */
    @Autowired
    public QuestionService(@Qualifier("QuestionRepository") QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    /**
     * Gets a list of all questions.
     *
     * @return A list containing all the questions
     */
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    /**
     * Returns question by id, used for testing.
     * @param id Id of the question.
     * @return Question object with id specified, null if not found.
     */
    public Question findById(long id) {
        boolean exists = questionRepository.existsById(id);
        if (!exists) {
            return null;
        }
        return questionRepository.findById(id).get();
    }

    /**
     * Gets a list of all questions in a given lecture.
     *
     * @param id of the lecture targeted
     * @return A list of all questions in the lecture
     */
    public List<Question> getAllQuestionsInLecture(long id) {
        return  questionRepository.findAllByLectureId(id);
    }

    /**
     * This saves a question into the database.
     * If a question with the same id already exists in the database,
     * the save method acts as an update method (never creating duplicates).
     *
     * @param question The question object that needs to be saved.
     */
    public Question insertQuestion(Question question) {
        return questionRepository.save(question);
    }

    /**
     * This removes a question using its ID.
     * @param questionId The ID of the question.
     */
    public void deleteQuestion(long questionId) {
        boolean exists = questionRepository.existsById(questionId);
        if (!exists) {
            throw new IllegalStateException("Question with id " + questionId + " does not exist.");
        }
        questionRepository.deleteById(questionId);
    }

    /**
     * This increments the number of votes
     * of a question.
     * @param question the question object that needs to be updated
     */
    public Question upVoteQuestion(Question question, String userAlias) {
        Question q = questionRepository.findById(question.getId())
                .orElseThrow(() -> new IllegalStateException((
                        "This question does not exist"
                )));
        q.upVote();
        q.addVotedUser(userAlias);
        return questionRepository.save(q);
    }

    /**
     * This method performs an edit on the text description
     * of a question.
     * @param question the question object that needs to be updated
     */
    public Question editQuestion(Question question) {
        Question q = questionRepository.findById(question.getId())
                .orElseThrow(() -> new IllegalStateException((
                        "This question does not exist"
                )));
        q.setText(question.getText());
        return questionRepository.save(q);
    }

    /**
     * javadoc.
     */
    public Question replyQuestion(Question question) {
        Question q = questionRepository.findById(question.getId())
                .orElseThrow(() -> new IllegalStateException((
                        "This question does not exist"
                )));
        q.setAnswer(question.getAnswer());
        q.setReplierAlias(question.getReplierAlias());
        q.setReplierId(question.getReplierId());
        //This is not the same as creating a new question. This is a transaction.
        return questionRepository.save(q);
    }


    /**
     * This method sets the state of a question to answered = true.
     *
     * @param question the question object that needs to have its
     *                 answered field set to be true
     */
    public Question markAnswered(Question question) {
        Question q = questionRepository.findById(question.getId())
                .orElseThrow(() -> new IllegalStateException((
                        "This question does not exist"
                )));
        q.setAnswered(true);
        return questionRepository.save(q);
    }

    /**
     * Hides question for the moderator.
     * @param question Question object that needs to be hidden
     */
    public Question hideQuestion(Question question) {
        Question q = questionRepository.findById(question.getId())
                .orElseThrow(() -> new IllegalStateException((
                        "This question does not exist"
                )));
        q.setHidden(true);
        return questionRepository.save(q);
    }

    /**
     * This method retrieves all questions from a lecture that are
     * already answered. (answered = true)
     * @param id of the lecture in which we search the questions
     * @return A list of answered questions in a lecture
     */
    public List<Question> getAllAnsweredQuestionsInLecture(long id) {
        return questionRepository.findAllByAnsweredAndLectureId(true, id);
    }
}