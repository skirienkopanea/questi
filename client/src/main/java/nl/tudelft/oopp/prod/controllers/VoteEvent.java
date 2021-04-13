package nl.tudelft.oopp.prod.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.control.Button;
import nl.tudelft.oopp.prod.communication.QuestionRequest;
import nl.tudelft.oopp.prod.objects.Question;

public class VoteEvent implements EventHandler<ActionEvent> {
    private Question question;
    private Button vote;
    private String userAlias;

    /**
     * Vote event constructor for the student screen.
     * @param question Question object associated with event.
     * @param vote The vote button associated with the question.
     */
    public VoteEvent(Question question, Button vote, String userAlias) {
        this.question = question;
        this.vote = vote;
        this.userAlias = userAlias;
        this.vote.setStyle("-fx-background-color: orange");
    }

    @Override
    public void handle(ActionEvent event) {
        this.vote.setDisable(true);
        QuestionRequest.upVoteQuestion(this.question, this.userAlias);
    }
}
