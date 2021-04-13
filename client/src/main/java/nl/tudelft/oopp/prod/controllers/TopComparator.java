package nl.tudelft.oopp.prod.controllers;

import java.util.Comparator;
import java.util.Date;

import nl.tudelft.oopp.prod.objects.Question;

/**
 * The type Top comparator.
 */
public class TopComparator implements Comparator<Question> {

    /**
     * The comparator to be implemented by the priority queue.
     * @param q1 first question
     * @param q2 second question
     * @return the correct order
     */
    public int compare(Question q1, Question q2) {
        if (formula(q1) > formula(q2)) {
            return 1;
        } else if (formula(q1) < formula(q2)) {
            return -1;
        }
        return 0;
    }

    /**
     * Formula for TOP Sorting.
     *
     * @param q question to applied the formula on
     * @return the priority of the question
     */
    public int formula(Question q) {
        return q.getVotes();
    }
}
