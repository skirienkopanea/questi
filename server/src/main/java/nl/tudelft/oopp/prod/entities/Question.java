package nl.tudelft.oopp.prod.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "question")
public class Question {

    @Id
    @SequenceGenerator(
            name = "question_sequence",
            sequenceName = "question_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "question_sequence"
    )

    private long id;
    private long lectureId;
    private long userId;
    private long replierId;
    private String text;
    private int votes;
    private String answer;

    @Column(name = "is_answered")
    private boolean answered;
    @Column(name = "user_alias")
    private String userAlias;
    @Column(name = "replier_alias")
    private String replierAlias;

    @Column(name = "seconds")
    private int seconds;
    @Column(name = "is_hidden")
    private boolean hidden;

    @ElementCollection(targetClass = String.class)
    private Set<String> votedUsers;

    /**
     * Initialisation constructor
     * for Question objects.
     */
    public Question() {
        this.votes = 0;
        this.answer = "";
        this.answered = false;
        this.seconds = 0;
        this.hidden = false;
        this.votedUsers = new HashSet<>();
    }


    /**
     * This is the constructor for the Question class.
     *
     * @param id        The ID of the question
     * @param lectureId The ID of the lecture that it belongs to
     * @param userId    The user ID of the Question
     * @param text      The textual description of the question
     * @param votes     The number of votes the question received
     * @param answer    The textual description of the answer
     */
    public Question(long id, long lectureId, long userId,
                    String text, int votes, String answer) {
        this.id = id;
        this.lectureId = lectureId;
        this.userId = userId;
        this.text = text;
        this.votes = votes;
        this.answer = answer;
        this.answered = false;
        this.seconds = 0;
        this.hidden = false;
        this.votedUsers = new HashSet<>();
    }

    /**
     *  This is the constructor for the Question class.
     * @param id ID of question
     * @param lectureId lectureID of question
     * @param userId userId of question
     * @param text textual part of question
     * @param votes the number of votes
     * @param answer the textual answer
     * @param userAlias the user alias of who asked the question.
     */
    public Question(long id, long lectureId, long userId,
                    String text, int votes, String answer, String userAlias) {
        this.id = id;
        this.lectureId = lectureId;
        this.userId = userId;
        this.text = text;
        this.votes = votes;
        this.answer = answer;
        this.userAlias = userAlias;
        this.answered = false;
        this.seconds = 0;
        this.hidden = false;
        this.votedUsers = new HashSet<>();
    }

    /**
     * This is the constructor for the Question class.
     *
     * @param lectureId The ID of the lecture that it belongs to
     * @param userId    The user ID of the Question
     * @param text      The textual description of the question
     * @param votes     The number of votes the question received
     * @param answer    The textual description of the answer
     */
    public Question(long lectureId, long userId, String text, int votes, String answer) {
        this.lectureId = lectureId;
        this.userId = userId;
        this.text = text;
        this.votes = votes;
        this.answer = answer;
        this.answered = false;
        this.seconds = 0;
        this.hidden = false;
        this.votedUsers = new HashSet<>();
    }



    /**
     * Instantiates a new Question.
     *
     * @param id           the id
     * @param lectureId    the lecture id
     * @param userId       the user id
     * @param text         the text
     * @param votes        the votes
     * @param answer       the answer
     * @param userAlias    the user alias
     * @param replierId    the user id
     * @param replierAlias the user alias
     */
    public Question(long id, long lectureId, long userId, String text, int votes,
                    String answer, String userAlias,
                    long replierId, String replierAlias) {
        this.id = id;
        this.lectureId = lectureId;
        this.userId = userId;
        this.text = text;
        this.votes = votes;
        this.answer = answer;
        if (answer != null && answer.length() > 0) {
            this.answered = true;
        }
        this.userAlias = userAlias;
        this.replierId = replierId;
        this.replierAlias = replierAlias;
        this.seconds = 0;
        this.hidden = false;
        this.votedUsers = new HashSet<>();
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
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
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

    public void upVote() {
        this.votes = this.votes + 1;
    }

    public boolean getHidden() {
        return this.hidden;
    }

    public void setHidden(boolean hide) {
        this.hidden = hide;
    }

    public Set<String> getVotedUsers() {
        return this.votedUsers;
    }

    public void setVotedUsers(Set<String> votedUsers) {
        this.votedUsers = votedUsers;
    }

    public void addVotedUser(String userAlias) {
        this.votedUsers.add(userAlias);
    }

    @Override
    public String toString() {
        if (this.text == null) {
            return "-------";
        }
        return this.getText();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Question)) {
            return false;
        }
        Question question = (Question) o;
        return getId() == question.getId()
                && getLectureId() == question.getLectureId()
                && getUserId() == question.getUserId()
                && getVotes() == question.getVotes()
                && isAnswered() == question.isAnswered()
                && Objects.equals(getText(), question.getText())
                && Objects.equals(getAnswer(), question.getAnswer())
                && Objects.equals(getUserAlias(), question.getUserAlias());
    }

    public long getReplierId() {
        return replierId;
    }

    public String getReplierAlias() {
        return replierAlias;
    }

    public void setReplierId(long replierId) {
        this.replierId = replierId;
    }

    public void setReplierAlias(String replierAlias) {
        this.replierAlias = replierAlias;
    }
}
