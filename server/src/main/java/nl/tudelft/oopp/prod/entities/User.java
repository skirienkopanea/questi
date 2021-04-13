package nl.tudelft.oopp.prod.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User implements Serializable {

    //Multiple clients wont be able to guess their own id
    //Therefore random id is generated on client side
    //Server only validates that they are unique
    @Id
    private long id;
    private String alias;
    private boolean mod;

    // Lecture object is needed by JPA to make the connection between tables
    @JoinColumn(name = "lecture_id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Lecture lecture;

    // It is cumbersome and duplicative to insert a whole lecture
    // Therefore the database only needs the lecture id to validate the foreign key
    @Column(name = "lecture_id")
    private Long lectureId;

    /**
     * The empty constructor.
     */
    public User() {
    }

    /**
     * This is the constructor of the User class.
     */
    public User(long id, long lectureId, String alias, boolean mod) {
        this.id = id;
        this.lectureId = lectureId;
        this.alias = alias;
        this.mod = mod;
    }


    /**
     * Instantiates a new User.
     *
     * @param id        the id
     * @param alias     the alias
     * @param mod       the mod
     * @param lecture   the lecture
     * @param lectureId the lecture id
     */
    public User(long id, String alias, boolean mod, Lecture lecture, Long lectureId) {
        this.id = id;
        this.alias = alias;
        this.mod = mod;
        this.lecture = lecture;
        this.lectureId = lectureId;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public boolean isMod() {
        return mod;
    }

    public void setMod(boolean mod) {
        this.mod = mod;
    }

    public Lecture getLecture() {
        return lecture;
    }

    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
    }

    public Long getLectureId() {
        return lectureId;
    }

    public void setLectureId(Long lectureId) {
        this.lectureId = lectureId;
    }

    @Override
    public String toString() {
        return "User{"
                + "id=" + id
                + ", alias='" + alias + '\''
                + ", mod=" + mod
                + ", lecture=" + lecture
                + ", lectureId=" + lectureId
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return getId() == user.getId()
                && isMod() == user.isMod()
                && Objects.equals(getAlias(), user.getAlias())
                && Objects.equals(lecture, user.lecture)
                && Objects.equals(getLectureId(), user.getLectureId());
    }

}
