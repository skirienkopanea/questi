package nl.tudelft.oopp.prod.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import nl.tudelft.oopp.prod.objects.Question;

public class Sorter {

    /**
     * Sort list.
     *
     * @param questions   the questions
     * @param sortingType the sorting type
     * @return the list
     */
    public static List<Question> sort(List<Question> questions, int sortingType) {

        PriorityQueue<Question> pq;

        if (questions.isEmpty()) {
            return questions;
        }

        switch (sortingType) {
            case 0:
                pq = new PriorityQueue<Question>(questions.size(), new TopComparator());
                break;

            case 1:
                pq = new PriorityQueue<Question>(questions.size(), new HotComparator());
                break;

            case 2:
                pq = new PriorityQueue<Question>(questions.size(), new NewComparator());
                break;

            default:
                throw new IllegalStateException("To be implemented in future release");
        }

        pq.addAll(questions);

        List<Question> newList = new ArrayList<>();

        while (!pq.isEmpty()) {
            newList.add(0, pq.poll());
        }

        return newList;
    }

}
