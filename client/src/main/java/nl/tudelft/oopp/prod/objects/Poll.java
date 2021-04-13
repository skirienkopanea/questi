package nl.tudelft.oopp.prod.objects;

public class Poll {
    private long id;
    private long lectureId;
    private String description;
    private boolean active;

    /**
     * Poll constructor.
     * @param id - Update this javadoc.
     * @param lectureId - Update this javadoc.
     * @param description - Update this javadoc.
     */
    public Poll(long id, long lectureId, String description, boolean active) {
        this.id = id;
        this.lectureId = lectureId;
        this.description = description;
        this.active = active;
    }

    /**
     * Testing constructor.
     */
    public Poll(long id, long lectureId, String description) {
        this.id = id;
        this.lectureId = lectureId;
        this.description = description;
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public long getId() {
        return id;
    }

    public long getLectureId() {
        return lectureId;
    }

    public String getDescription() {
        return description;
    }
}
