package nl.tudelft.oopp.prod.entities;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "poll_option")
public class PollOption {

    @Id
    private long  id;
    private long  pollId;
    private String text;
    private int score;

    /**
     * Empty constructor.
     */
    public PollOption() {

    }

    /**
     * This is the constructor for the poll option class.
     * @param id ID of the poll option
     * @param pollId The ID of the poll that it belongs to
     * @param text The textual description of the option
     * @param score The textual description of the option
     */
    public PollOption(long id, long  pollId, String text, int score) {
        this.id = id;
        this.pollId = pollId;
        this.text = text;
        this.score = score;
    }

    /**
     * This is the constructor for the poll option class.
     * @param pollId The ID of the poll that it belongs to
     * @param text The textual description of the option
     * @param score The textual description of the option
     */
    public PollOption(long  pollId, String text, int score) {
        this.pollId = pollId;
        this.text = text;
        this.score = score;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPollId() {
        return pollId;
    }

    public void setPollId(long pollId) {
        this.pollId = pollId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void downVote() {
        this.score--;
    }

    public void upVote() {
        this.score++;
    }

    @Override
    public String toString() {
        return "PollOption{"
                + "id=" + id
                + ", pollId=" + pollId
                + ", text='" + text + '\''
                + ", score=" + score + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PollOption)) {
            return false;
        }
        PollOption that = (PollOption) o;
        return getId() == that.getId()
                && getPollId() == that.getPollId()
                && getScore() == that.getScore()
                && Objects.equals(getText(), that.getText());
    }

}
