package nl.tudelft.oopp.prod.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import nl.tudelft.oopp.prod.communication.QuestionRequest;

public class ModeratorDeleteEvent implements EventHandler<ActionEvent> {
    private long questionId;
    private Label text;
    private Button vote;
    private Button answered;
    private Button hide;
    private Button delete;
    private Button edit;

    /**
     * Instantiates a new Delete event.
     *
     * @param questionId the question id
     * @param text       the text
     * @param vote       the vote
     * @param answered   the answered
     * @param delete     the delete
     */
    public ModeratorDeleteEvent(long questionId, Label text,
                                Button vote, Button answered,
                                Button edit,
                                Button hide,
                                Button delete) {
        this.questionId = questionId;
        this.text = text;
        this.vote = vote;
        this.answered = answered;
        this.edit = edit;
        this.hide = hide;
        this.delete = delete;
    }

    @Override
    public void handle(ActionEvent event) {
        QuestionRequest.deleteQuestion(questionId);
        this.text.setVisible(false);
        this.vote.setVisible(false);
        this.answered.setVisible(false);
        this.hide.setVisible(false);
        this.edit.setVisible(false);
        this.delete.setVisible(false);


        this.text.setManaged(false);
        this.vote.setManaged(false);
        this.answered.setManaged(false);
        this.hide.setManaged(false);
        this.delete.setManaged(false);
        this.edit.setManaged(false);
    }
}
