package nl.tudelft.oopp.prod.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import nl.tudelft.oopp.prod.communication.QuestionRequest;
import nl.tudelft.oopp.prod.objects.Question;
import nl.tudelft.oopp.prod.objects.Session;

public class ReplyEvent implements EventHandler<ActionEvent> {
    private Question question;
    private TextArea textReply;
    private Button replyButton;
    private Label original;
    private Pane replyPane;

    /**
     * javadoc.
     */
    public ReplyEvent(Question question, TextArea textReply,
                      Button replyButton, Label original, Pane replyPane) {
        this.question = question;
        this.textReply = textReply;
        this.replyButton = replyButton;
        this.original = original;
        this.replyPane = replyPane;
    }

    @Override
    public void handle(ActionEvent event) {
        setReplier();
        replyButton.setDisable(false);
        textReply.setEditable(true);
        this.replyPane.setVisible(true);

        replyButton.setOnAction(new QuestionReplyEvent(question,
                textReply,
                replyButton,
                original,
                replyPane));
    }

    private void setReplier() {
        this.question.setReplierAlias(Session.getUser().getAlias());
        this.question.setReplierId(Session.getUser().getId());
        QuestionRequest.replyQuestion(this.question);
    }
}
