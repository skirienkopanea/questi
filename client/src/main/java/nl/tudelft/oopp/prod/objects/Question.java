package nl.tudelft.oopp.prod.objects;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Question {
    private long id;
    private long lectureId;
    private long userId;
    private String text;
    private int votes;
    private String answer;
    private boolean answered;
    private String userAlias;
    private long replierId;
    private String replierAlias;
    private int seconds;
    private boolean hidden;
    private Set<String> votedUsers;


    /**
     * This constructor is used
     * when a question is created at first.
     *
     * @param text    Textual description of the question.
     * @param lecture The lecture the question is associated with.
     */
    public Question(String text,
                    Lecture lecture,
                    long userId,
                    String userAlias,
                    long replierId,
                    String replierAlias) {
        this.text = text;
        this.lectureId = lecture.getId();
        this.userId = userId;
        this.votes = 0;
        this.answer = "";
        this.answered = false;
        this.userAlias = userAlias;
        this.seconds = 0;
        this.hidden = false;
        this.replierId = replierId;
        this.replierAlias = replierAlias;
        this.votedUsers = new HashSet<>();
    }

    public Question() {

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isAnswered() {
        return this.answered;
    }

    public void setAnswered(boolean ans) {
        this.answered = ans;
    }

    public String getUserAlias() {
        return userAlias;
    }

    public void setUserAlias(String userAlias) {
        this.userAlias = userAlias;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public boolean getHidden() {
        return this.hidden;
    }

    public void setHidden(boolean hide) {
        this.hidden = hide;
    }

    @Override
    public String toString() {
        if (this.text == null) {
            return "-----";
        }
        return this.getText() + " ";
    }

    public long getReplierId() {
        return replierId;
    }

    public void setReplierId(long replierId) {
        this.replierId = replierId;
    }

    public String getReplierAlias() {
        return replierAlias;
    }

    public void setReplierAlias(String replierAlias) {
        this.replierAlias = replierAlias;
    }

    public Set<String> getVotedUsers() {
        return this.votedUsers;
    }

    public void addVotedUser(String userAlias) {
        this.votedUsers.add(userAlias);
    }
}
