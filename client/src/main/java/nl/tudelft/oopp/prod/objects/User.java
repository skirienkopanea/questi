package nl.tudelft.oopp.prod.objects;

import java.util.List;
import java.util.Random;

public class User {
    private long  id;
    private String alias;
    private boolean mod; //do not change to isMod (due to JPA)
    private Long lectureId;
    private static List<Question> questions;

    /**
     * User constructor.
     */
    public User(long id, String alias, long lectureId, boolean isMod) {
        this.id = id;
        this.alias = makeAliasUnique(alias);
        this.mod = isMod;
        this.lectureId = lectureId;
    }

    /**
     * Constructor for User object.
     * @param id Id of the user
     * @param isMod True if User is moderator, false otherwise
     * @param lectureId The lectureId of user
     */
    public User(long id, boolean isMod, long lectureId) {
        this.id = id;
        this.mod = isMod;
        this.lectureId = lectureId;
    }

    /**
     * Empty constructor for initialisation.
     */
    public User() {
    }

    /**
     * This method creates a unique user Alias for the user.
     * @param alias Alias as entered by user
     * @return String alias saved into the database.
     */
    public String makeAliasUnique(String alias) {

        String result = alias + "";
        String numbers = "0123456789";

        for (int i = 0; i < 4; i++) {
            Random r = new Random();
            int j = r.nextInt(10);
            result = result + numbers.charAt(j);
        }

        return result;
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

    public Long getLectureId() {
        return lectureId;
    }

    public void setLectureId(Long lectureId) {
        this.lectureId = lectureId;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id
                + ", alias='" + alias + '\''
                + ", mod=" + mod
                + ", lectureId=" + lectureId
                + '}';
    }
}
