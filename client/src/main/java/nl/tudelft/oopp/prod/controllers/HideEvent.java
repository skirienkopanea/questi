package nl.tudelft.oopp.prod.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import nl.tudelft.oopp.prod.communication.QuestionRequest;
import nl.tudelft.oopp.prod.objects.Question;


public class HideEvent implements EventHandler<ActionEvent> {
    private Label text;
    private Button votes;
    private Button answered;
    private Button hide;
    private Button delete;
    private Button edit;
    private Button reply;
    private Question question;

    /**
     * Constructor for hide event.
     * @param text text of question
     * @param answered whether the question is answered
     * @param votes number of votes
     * @param edit edit button
     * @param delete delete button
     * @param reply reply button
     * @param hide hide button
     */
    public HideEvent(Question question,
                     Label text, Button answered,
                     Button votes, Button edit,
                     Button delete, Button reply,
                     Button hide) {
        this.question = question;
        this.text = text;
        this.answered = answered;
        this.votes = votes;
        this.edit = edit;
        this.delete = delete;
        this.reply = reply;
        this.hide = hide;
    }

    @Override
    public void handle(ActionEvent event) {
        this.question.setHidden(true);
        QuestionRequest.hideQuestion(this.question);

        this.text.setVisible(false);
        this.answered.setVisible(false);
        this.votes.setVisible(false);
        this.edit.setVisible(false);
        this.delete.setVisible(false);
        this.reply.setVisible(false);
        this.hide.setVisible(false);


        this.text.setManaged(false);
        this.answered.setManaged(false);
        this.votes.setManaged(false);
        this.edit.setManaged(false);
        this.delete.setManaged(false);
        this.reply.setManaged(false);
        this.hide.setManaged(false);
    }
}
