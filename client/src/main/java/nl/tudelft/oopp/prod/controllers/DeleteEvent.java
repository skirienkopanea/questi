package nl.tudelft.oopp.prod.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import nl.tudelft.oopp.prod.communication.QuestionRequest;

public class DeleteEvent implements EventHandler<ActionEvent> {
    private long questionId;
    private Label text;
    private Button vote;
    private Button delete;
    private Button edit;

    /**
     * Initialises the Delete event.
     * @param questionId The question ID
     * @param text The text label of question
     * @param vote The vote button
     * @param delete The delete button
     * @param edit The edit button
     */
    public DeleteEvent(long questionId, Label text, Button vote, Button edit, Button delete) {
        this.questionId = questionId;
        this.text = text;
        this.vote = vote;
        this.delete = delete;
        this.edit = edit;
        this.delete.setStyle("-fx-background-color: #ff5555");
    }

    @Override
    public void handle(ActionEvent event) {
        QuestionRequest.deleteQuestion(questionId);
        this.text.setVisible(false);
        this.vote.setVisible(false);
        this.delete.setVisible(false);
        this.edit.setVisible(false);

        this.text.setManaged(false);
        this.vote.setManaged(false);
        this.delete.setManaged(false);
        this.edit.setManaged(false);
    }
}
