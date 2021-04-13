package nl.tudelft.oopp.prod.controllers;

import java.util.Comparator;
import java.util.Date;

import nl.tudelft.oopp.prod.objects.Lecture;
import nl.tudelft.oopp.prod.objects.Question;
import nl.tudelft.oopp.prod.objects.Session;

/**
 * The type Hot comparator.
 */
public class HotComparator implements Comparator<Question> {

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
    public double formula(Question q) {
        int timeDiff = Math.abs(q.getSeconds() - passedTime());
        if (timeDiff == 0) {
            timeDiff = 1;
        }
        return Math.log(q.getVotes()) / Math.log(2) + 20 / (timeDiff * 1.0 / 60);
    }

    /**
     * Passed time.
     *
     * @return the int
     */
    public static int passedTime() {
        long secondsSinceStart;
        long now = System.currentTimeMillis();
        long then = ModeratorScreenController.getStartTime();
        secondsSinceStart = (now - then) / 1000;
        return (int) secondsSinceStart;
    }

}
