package nl.tudelft.oopp.prod.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "poll")
public class Poll implements Serializable {

    @Id
    private long id;
    private long lectureId;
    private String description;
    private boolean active;

    /**
     * Empty Constructor.
     */
    public Poll() {

    }

    /**
     * This is the constructor for the Poll class.
     * @param id the ID of the poll
     * @param lectureId the lectureId of the poll
     * @param description the description of the poll
     */
    public Poll(long  id, long  lectureId, String description) {
        this.id = id;
        this.lectureId = lectureId;
        this.description = description;
    }

    /**
     * This constructor includes a boolean active.
     * The idea is that a user may only vote when a poll is active
     */
    public Poll(long id, long lectureId, String description, boolean active) {
        this.id = id;
        this.lectureId = lectureId;
        this.description = description;
        this.active = active;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getLectureId() {
        return lectureId;
    }

    public void setLectureId(long lectureId) {
        this.lectureId = lectureId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Poll{"
                + "id="
                + id
                + ", lectureId="
                + lectureId
                + ", description='"
                + description + '\''
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Poll)) {
            return false;
        }
        Poll poll = (Poll) o;
        return getId() == poll.getId()
                && getLectureId() == poll.getLectureId()
                && Objects.equals(getDescription(), poll.getDescription());
    }
}
