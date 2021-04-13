package nl.tudelft.oopp.prod.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import nl.tudelft.oopp.prod.entities.Question;
import nl.tudelft.oopp.prod.repositories.QuestionRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

//Old tests

@DataJpaTest
public class QuestionJpaTests {

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    public void saveRetrieveQuestionTest() {
        Question question = new Question();
        question.setText("What is the favourite food of ducks?");
        question.setVotes(42);
        question.setAnswer("Sweet corn");

        question = questionRepository.save(question);

        Question question2 = questionRepository.getOne(question.getId());
        assertEquals(question, question2);
    }

    @Test
    public void saveAndDeleteQuestionTest() {
        Question question = new Question();
        question.setText("If I try to fail, but succeed, which one did I do?");
        question.setVotes(-98);
        question.setAnswer("Really?");
        question = questionRepository.save(question);
        questionRepository.deleteById(question.getId());
        assertFalse(questionRepository.existsById(question.getId()));
    }

}
