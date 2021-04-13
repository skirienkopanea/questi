package nl.tudelft.oopp.prod.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import nl.tudelft.oopp.prod.communication.QuestionRequest;
import nl.tudelft.oopp.prod.objects.Question;

public class QuestionEditEvent implements EventHandler<ActionEvent> {
    private Question question;
    private TextArea text;
    private Button submit;
    private Label original;
    private Pane editPane;

    /**
     * This associates with submitting the edit.
     * @param question The question object that is edited.
     * @param text The text area for submitting the edit.
     * @param submit The submit button for the edit.
     * @param orig The original text Area that needs to be updated. 
     */
    public QuestionEditEvent(Question question,
                             TextArea text,
                             Button submit,
                             Label orig,
                             Pane editPane) {
        this.text = text;
        this.question = question;
        this.submit = submit;
        this.original = orig;
        this.editPane = editPane;
    }


    @Override
    public void handle(ActionEvent event) {
        String editedQuestion = this.text.getText();

        //take the first few characters
        if (editedQuestion.length() > 200) {
            editedQuestion = editedQuestion.substring(0, 199);

        }

        this.question.setText(editedQuestion);
        this.original.setText(editedQuestion + "\n" + "asked by " + this.question.getUserAlias());
        QuestionRequest.editQuestion(this.question);

        text.clear();
        text.setEditable(false);
        submit.setDisable(true);
        this.editPane.setVisible(false);

    }
}
