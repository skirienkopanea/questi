package nl.tudelft.oopp.prod.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "banned_user")
public class BannedUser implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "lectureId")
    private long lectureId;

    @Column(name = "userId")
    private long userId;

    public BannedUser() {

    }

    public BannedUser(long lectureId, long userId) {
        this.lectureId = lectureId;
        this.userId = userId;
    }

    /**
     * Constructor.
     * @param id Checkstyle
     * @param lectureId Checkstyle
     * @param userId Checkstyle
     */
    public BannedUser(long id, long lectureId, long userId) {
        this.id = id;
        this.lectureId = lectureId;
        this.userId = userId;
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BannedUser that = (BannedUser) o;
        return id == that.id && lectureId == that.lectureId && userId == that.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lectureId, userId);
    }

    @Override
    public String toString() {
        return "BannedUser{" + "id="
                + id
                + ", lectureId="
                + lectureId
                + ", userId="
                + userId
                + '}';
    }

}
