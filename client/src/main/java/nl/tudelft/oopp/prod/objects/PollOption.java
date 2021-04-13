package nl.tudelft.oopp.prod.objects;

public class PollOption {
    private long id;
    private long pollId;
    private String text;
    private int score;

    /**
     * Option constructor.
     * @param id - Update this javadoc.
     * @param pollId - Update this javadoc.
     * @param text - Update this javadoc.
     * @param score - Update this javadoc.
     */
    public PollOption(long id, long pollId, String text, int score) {
        this.id = id;
        this.pollId = pollId;
        this.text = text;
        this.score = 0;
    }

    public long getId() {
        return id;
    }

    public long getPollId() {
        return pollId;
    }

    public String getText() {
        return text;
    }

    public int getScore() {
        return score;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPollId(long pollId) {
        this.pollId = pollId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
