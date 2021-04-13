package nl.tudelft.oopp.prod.controllers;

import java.util.Comparator;
import java.util.Date;

import nl.tudelft.oopp.prod.objects.Question;
import nl.tudelft.oopp.prod.objects.Session;

/**
 * The type New comparator.
 */
public class NewComparator implements Comparator<Question> {

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
        return Integer.compare(q1.getVotes(), q2.getVotes());
    }

    /**
     * Formula for NEW Sorting.
     *
     * @param q question to applied the formula on
     * @return the priority of the question
     */
    public double formula(Question q) {
        return q.getSeconds();
    }

}
