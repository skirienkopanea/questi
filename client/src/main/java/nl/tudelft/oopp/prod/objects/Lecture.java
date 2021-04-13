package nl.tudelft.oopp.prod.objects;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Lecture {

    private long id;
    private String moderatorLink;
    private String publicLink;
    private String name;
    private boolean ongoing;
    private boolean rateLimited;
    private Date startTime;
    private Date endTime;


    /**
     * Empty constructor used for Question
     * objects.
     */
    public Lecture() {
    }

    public Lecture(long id) {
        this.id = id;
    }

    /**
     * Constructor used for direct lectures.
     */
    public Lecture(long id, String name, String moderatorLink, String publicLink, boolean ongoing) {
        this.id = id;
        this.name = name;
        this.moderatorLink = moderatorLink;
        this.publicLink = publicLink;
        this.ongoing = ongoing;
        this.rateLimited = false;
    }

    /**
     * Constructor for Lecture.
     */
    public Lecture(long id,
                   String moderatorLink,
                   String publicLink,
                   String name,
                   boolean ongoing,
                   Date startTime,
                   Date endTime) {
        this.id = id;
        this.moderatorLink = moderatorLink;
        this.publicLink = publicLink;
        this.name = name;
        this.ongoing = ongoing;
        this.rateLimited = false;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdString() {
        return this.id + "";
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

    public void setOngoing(boolean ong) {
        ongoing = ong;
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

    /**
     * Converts to a json string.
     * with a date format that the database will be able to parse.
     */
    public String toJson() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String start = sdf.format(startTime);
        String end = sdf.format(endTime);

        return ("{\n"
                + "    \"id\": " + id + ",\n"
                + "    \"moderatorLink\": \"" + moderatorLink + "\",\n"
                + "    \"publicLink\": \"" + publicLink + "\",\n"
                + "    \"name\": \"" + name + "\",\n"
                + "    \"startTime\": \"" + start + "\",\n"
                + "    \"endTime\": \"" + end + "\",\n"
                + "    \"ongoing\": " + ongoing + "\n"
                + "}");
    }
}
