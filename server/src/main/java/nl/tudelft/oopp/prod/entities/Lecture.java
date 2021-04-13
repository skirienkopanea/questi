package nl.tudelft.oopp.prod.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name = "lecture")
public class Lecture implements Serializable {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "moderator_link", unique = true)
    private String moderatorLink;

    @Column(name = "public_link", unique = true)
    private String publicLink;

    @Column(name = "name")
    private String name;

    @Column(name = "is_ongoing")
    private boolean ongoing;

    @Column(name = "rate_limited")
    private boolean rateLimited;

    @Column(name = "start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Column(name = "end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;


    /**
     * Empty constructor.
     */
    public Lecture() {

    }

    /**
     * This is the constructor for the Lecture used to send.
     * A lecture with id -1 to the client side to denote "Not found"
     *
     * @param id   ID of the lecture.
     * @param name Name of the lecture.
     */
    public Lecture(long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * This is a constructor for the Lecture used for.
     * JPA Tests on the server side
     *
     * @param id            - lecture id PK
     * @param moderatorLink - moderator link UNIQUE
     * @param publicLink    - public link UNIQUE
     * @param name          - lecture name
     * @param isOngoing     - ongoing boolean
     * @param startTime     - start time Date/TIMESTAMP
     * @param endTime       - end time Date/TIMESTAMP
     */
    public Lecture(long id,
                   String moderatorLink,
                   String publicLink,
                   String name,
                   boolean isOngoing,
                   Date startTime,
                   Date endTime) {
        this.id = id;
        this.moderatorLink = moderatorLink;
        this.publicLink = publicLink;
        this.name = name;
        this.ongoing = isOngoing;
        this.startTime = startTime;
        this.endTime = endTime;
        this.rateLimited = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getModeratorLink() {
        return moderatorLink;
    }

    public void setModeratorLink(String moderatorLink) {
        this.moderatorLink = moderatorLink;
    }

    public String getPublicLink() {
        return publicLink;
    }

    public void setPublicLink(String publicLink) {
        this.publicLink = publicLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOngoing() {
        return ongoing;
    }

    public void setOngoing(boolean ongoing) {
        this.ongoing = ongoing;
    }

    public boolean isRateLimited() {
        return rateLimited;
    }

    public void setRateLimited(boolean rateLimited) {
        this.rateLimited = rateLimited;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Lecture{"
                + "id=" + id
                + ", moderatorLink='" + moderatorLink + '\''
                + ", publicLink='" + publicLink + '\''
                + ", name='" + name + '\''
                + ", isOngoing=" + ongoing
                + ", startTime=" + startTime
                + ", endTime=" + endTime
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Lecture lecture = (Lecture) o;
        return id == lecture.id && ongoing == lecture.ongoing
                && Objects.equals(moderatorLink, lecture.moderatorLink)
                && Objects.equals(publicLink, lecture.publicLink)
                && Objects.equals(name, lecture.name)
                && Objects.equals(startTime, lecture.startTime)
                && Objects.equals(endTime, lecture.endTime);
    }
}
