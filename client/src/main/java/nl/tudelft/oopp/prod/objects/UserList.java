package nl.tudelft.oopp.prod.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * List of users.
 */
public class UserList {
    private List<User> users;


    /**
     * Constructor for UserList.
     */
    public UserList() {
        this.users = new ArrayList<User>();
    }

    /**
     * Getter for UserList.
     */
    public List<User> getUsers() {
        return users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserList userList = (UserList) o;
        return Objects.equals(users, userList.users);
    }

    /**
     * toString method.
     */
    public String toString() {
        String result = "Participants list:";
        int i = 0;
        if (this.users.size() != 0) {
            for (i = 0; i < this.users.size(); i++) {
                result += this.users.get(i);
                result += ", ";
            }
            result += this.users.get(i);
        }
        return result;
    }

}
