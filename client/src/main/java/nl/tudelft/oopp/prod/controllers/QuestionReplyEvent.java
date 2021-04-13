package nl.tudelft.oopp.prod.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import nl.tudelft.oopp.prod.communication.QuestionRequest;
import nl.tudelft.oopp.prod.objects.Question;

public class QuestionReplyEvent implements EventHandler<ActionEvent> {
    private Question question;
    private TextArea textReply;
    private Button reply;
    private Label original;
    private Pane replyPane;

    /**
     * javadoc.
     */
    public QuestionReplyEvent(Question question, TextArea textReply,
                              Button reply, Label original, Pane replyPane) {
        this.question = question;
        this.textReply = textReply;
        this.reply = reply;
        this.original = original;
        this.replyPane = replyPane;
    }

    @Override
    public void handle(ActionEvent event) {
        String repliedQuestion = this.textReply.getText();

        //take the first few characters
        if (repliedQuestion.length() > 200) {
            repliedQuestion = repliedQuestion.substring(0, 199);

        }

        this.question.setAnswer(repliedQuestion);
        this.question.setReplierAlias(null);
        this.question.setReplierId(-1);
        this.original.setText(repliedQuestion);
        QuestionRequest.replyQuestion(this.question);

        textReply.clear();
        textReply.setEditable(false);
        reply.setDisable(true);
        this.replyPane.setVisible(false);
    }
}
