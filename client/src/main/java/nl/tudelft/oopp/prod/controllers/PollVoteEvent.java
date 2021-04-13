package nl.tudelft.oopp.prod.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import nl.tudelft.oopp.prod.communication.PollOptionRequest;
import nl.tudelft.oopp.prod.communication.PollRequest;
import nl.tudelft.oopp.prod.objects.PollOption;

public class PollVoteEvent implements EventHandler<ActionEvent> {
    private long pollOptionId;
    private CheckBox vote;
    private Label votes;

    /**
     * Vote event constructor for the student screen.
     *
     * @param optionId PollOption object associated with event.
     * @param vote   The vote button associated with the poll.
     */
    public PollVoteEvent(long optionId, CheckBox vote, Label votes) {
        this.pollOptionId = optionId;
        this.vote = vote;
        this.votes = votes;
    }

    /**
     * handle the select event for the checkbox.
     * either votes, or downvotes a question
     */
    @Override
    public void handle(ActionEvent event) {
        if (vote.isSelected()) {

            try {
                PollOptionRequest.upVoteQuestion(pollOptionId);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {

            try {
                PollOptionRequest.downVoteQuestion(pollOptionId);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}