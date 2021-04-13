package nl.tudelft.oopp.prod.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import nl.tudelft.oopp.prod.objects.Question;

public class EditEvent implements EventHandler<ActionEvent> {
    private TextArea textArea;
    private Button submitButton;
    private Question question;
    private Label original;
    private Pane editPane;

    /**
     * This is the event associated with the EDIT button.
     * @param question The question object that needs editing
     * @param text The text area for typing a question
     * @param submit The submit button associated with the edit
     * @param orig the original text label of the question
     */
    public EditEvent(Question question, TextArea text, Button submit, Label orig, Pane editPane) {
        this.question = question;
        this.textArea = text;
        this.submitButton = submit;
        this.original = orig;
        this.editPane = editPane;
    }

    @Override
    public void handle(ActionEvent event) {
        submitButton.setDisable(false);
        textArea.setEditable(true);
        this.editPane.setVisible(true);

        submitButton.setOnAction(new QuestionEditEvent(question,
                textArea,
                submitButton,
                original,
                editPane));
    }
}
