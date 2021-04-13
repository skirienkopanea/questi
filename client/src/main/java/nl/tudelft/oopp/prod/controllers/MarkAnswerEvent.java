package nl.tudelft.oopp.prod.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import nl.tudelft.oopp.prod.communication.QuestionRequest;
import nl.tudelft.oopp.prod.objects.Question;

public class MarkAnswerEvent implements EventHandler<ActionEvent> {
    private Question question;
    private Label text;
    private Button mark;

    /**
     * Mark as answered event for the moderator.
     * @param question The Question associated with this event.
     * @param text The text label of storing the question.
     * @param mark The mark button.
     */
    public MarkAnswerEvent(Question question, Label text, Button mark) {
        this.question = question;
        this.text = text;
        this.mark = mark;
        this.mark.setStyle("-fx-background-color: #50fa7b");
    }

    @Override
    public void handle(ActionEvent event) {
        this.mark.setDisable(true);
        this.text.setStyle("-fx-background-color: #50fa7b");
        QuestionRequest.markAsAnswered(this.question);
    }
}
