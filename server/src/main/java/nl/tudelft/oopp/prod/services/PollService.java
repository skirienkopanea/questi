package nl.tudelft.oopp.prod.services;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import nl.tudelft.oopp.prod.entities.Poll;
import nl.tudelft.oopp.prod.entities.PollOption;
import nl.tudelft.oopp.prod.entities.User;
import nl.tudelft.oopp.prod.repositories.PollRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * The type Poll service.
 */
@Service
public class PollService {

    private final PollRepository pollRepository;

    /**
     * Instantiates a new Poll service and autowires it.
     *
     * @param pollRepository the poll repository
     */
    @Autowired
    public PollService(@Qualifier("PollRepository") PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }

    /**
     * Gets all polls in the database.
     *
     * @return A list of all polls
     */
    public List<Poll> getAllPolls() {
        return pollRepository.findAll();
    }

    /**
     * This returns the current live poll in a lecture.
     *
     * @param id The poll object that needs to be returned.
     * @return the live poll
     */
    public Poll getLivePoll(long id) {
        List<Poll> poll = pollRepository.findByLectureId(id);
        if (poll.size() > 1) {
            return pollRepository.findByLectureId(id).get(1);
        }
        return new Poll(-1, -1, "");
    }

    /**
     * This saves a poll in the database.
     *
     * @param poll The poll object that needs to be saved.
     */
    public Poll insertPoll(Poll poll) {
        Optional<Poll> pollOptional = pollRepository.findById(poll.getId());
        if (pollOptional.isPresent()) {
            throw new IllegalArgumentException("id already in use");
        }
        return pollRepository.save(poll);
    }

    /**
     * This removes a poll with the Poll ID.
     *
     * @param pollId The ID of the poll that needs to removed
     */
    public void deletePoll(long pollId) {
        boolean exists = pollRepository.existsById(pollId);
        if (!exists) {
            throw new IllegalStateException("Poll with id " + pollId + " does not exist.");
        }
        pollRepository.deleteById(pollId);
    }

    /**
     * This updates a poll according
     * to the parameters passed.
     *
     * @param pollId      The ID of the poll that needs to be updated.
     * @param lectureId   The lecture ID of the poll that needs to be updated.
     * @param description The updated description of the poll
     */
    public Poll updatePoll(Long pollId, long lectureId, String description) {
        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new IllegalStateException((
                "Question with id " + pollId + "does not exist"
        )));

        poll.setLectureId(lectureId);
        if (description != null) {
            poll.setDescription(description);
        }
        return pollRepository.save(poll);
    }

    /**
     * This generates a new unique poll ID.
     *
     * @return the poll id
     */
    public long getPollId() {
        Random generator = new Random();
        String numbers = "0123456789";
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            temp.append(numbers.charAt(generator.nextInt(numbers.length())));
        }
        long id = Long.parseLong(temp.toString());
        if (pollRepository.findById(id).isPresent()) {
            return getPollId();
        }
        return id;
    }

    /**
     * This method changes the status of a poll to not active.
     *
     * @param id the ID of the poll
     */
    public Poll deactivate(String id) {
        Poll poll = pollRepository
                .findById(Long.parseLong(id)).orElseThrow(() -> new IllegalStateException((
                        "Question with id " + id + "does not exist"
                )));

        poll.setActive(false);
        return pollRepository.save(poll);
    }

    /**
     * This method retrieves the status of a poll.
     *
     * @param id the ID of the poll
     * @return a boolean active/inactive
     */
    public boolean getStatus(long id) {
        Poll poll = pollRepository
                .findById(id).orElseThrow(() -> new IllegalStateException((
                        "Question with id " + id + "does not exist"
                )));

        return poll.isActive();
    }
}