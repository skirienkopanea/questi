package nl.tudelft.oopp.prod.services;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import nl.tudelft.oopp.prod.entities.PollOption;
import nl.tudelft.oopp.prod.repositories.PollOptionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * The type Poll option service.
 */
@Service
public class PollOptionService {

    private final PollOptionRepository pollOptionRepository;

    /**
     * Instantiates a new Poll option service and autowires it.
     *
     * @param pollOptionRepository the poll option repository
     */
    @Autowired
    public PollOptionService(@Qualifier("PollOptionRepository")
                                     PollOptionRepository pollOptionRepository) {
        this.pollOptionRepository = pollOptionRepository;
    }

    /**
     * Gets all poll options.
     *
     * @return a list of all poll options
     */
    public List<PollOption> getAllPollOptions() {
        return pollOptionRepository.findAll();
    }

    /**
     * Gets all poll options by poll id.
     *
     * @param id the id of the poll
     * @return a list of poll options for a specific poll
     */
    public List<PollOption> getAllPollOptionsByPollId(long id) {
        return pollOptionRepository.findAllByPollId(id);
    }

    /*

    public Optional<PollOption> getPollOptionById(long id) {
        return pollOptionRepository.findById(id);
    }
    */

    /**
     * This method saves a new poll option.
     *
     * @param pollOption A poll option object that needs to be saved.
     */
    public PollOption insertPollOption(PollOption pollOption) {
        Optional<PollOption> pollOptional = pollOptionRepository.findById(pollOption.getId());
        if (pollOptional.isPresent()) {
            throw new IllegalArgumentException("id already in use");
        }
        return pollOptionRepository.save(pollOption);
    }

    /**
     * This deletes a poll option using
     * the ID of the option.
     *
     * @param pollId The ID of the option
     */
    public void deletePollOption(long pollId) {
        List<PollOption> pollOptions = pollOptionRepository.findAllByPollId(pollId);
        if (pollOptions.size() != 0) {
            for (PollOption option : pollOptions) {
                long temp = option.getId();
                pollOptionRepository.deleteById(temp);
            }
        }
    }

    /**
     * This updates the score of a poll option.
     *
     * @param string The string containing the pollId and the number
     * @return the updated score
     */
    public int downVote(String string) {
        String[] parts = string.split("/");
        long pollId = Long.parseLong(parts[0]);
        int index = Integer.parseInt(parts[1]) + 1;
        List<PollOption> pollOptions = pollOptionRepository.findAllByPollId(pollId);
        int newScore = -1;
        for (PollOption pollOption : pollOptions) {
            if (index == Integer.parseInt(pollOption.getText())) {
                pollOption.downVote();
                pollOptionRepository.save(pollOption);
                newScore = pollOption.getScore();
            }
        }
        return newScore;
    }

    /**
     * This upvotes a poll option.
     *
     * @param string containing the poll id and the index of the option.
     * @return the updated score
     */
    public int upVote(String string) {
        String[] parts = string.split("/");
        long pollId = Long.parseLong(parts[0]);
        int index = Integer.parseInt(parts[1]) + 1;
        List<PollOption> pollOptions = pollOptionRepository.findAllByPollId(pollId);
        int newScore = -1;
        for (PollOption pollOption : pollOptions) {
            if (index == Integer.parseInt(pollOption.getText())) {
                pollOption.upVote();
                pollOptionRepository.save(pollOption);
                newScore = pollOption.getScore();
            }
        }
        return newScore;
    }

    /**
     * This downvotes a poll option.
     * @param id containing the poll option ID.
     * @return the updated score
     */
    public int downVoteQuestion(String id) {
        PollOption option = pollOptionRepository
                .findById(Long.parseLong(id)).orElseThrow(() -> new IllegalStateException((
                        "Question with id " + id + "does not exist"
                )));
        option.downVote();

        pollOptionRepository.save(option);
        return option.getScore();
    }

    /**
     * This upvotes a poll option.
     *
     * @param id containing the poll option ID.
     * @return the updated score
     */
    public int upVoteQuestion(String id) {
        PollOption option = pollOptionRepository
                .findById(Long.parseLong(id)).orElseThrow(() -> new IllegalStateException((
                        "Question with id " + id + "does not exist"
                )));
        option.upVote();

        pollOptionRepository.save(option);
        return option.getScore();
    }

    /**
     * This returns the current amount of votes for a poll option.
     *
     * @param string The poll id and index of poll option.
     * @return the votes
     */
    public int getVotes(String string) {
        String[] parts = string.split("/");
        long pollId = Long.parseLong(parts[0]);
        int index = Integer.parseInt(parts[1]);
        List<PollOption> pollOptions = pollOptionRepository.findAllByPollId(pollId);
        int newScore = -1;
        for (PollOption pollOption : pollOptions) {
            if (index == Integer.parseInt(pollOption.getText())) {
                newScore = pollOption.getScore();
            }
        }
        return newScore;
    }

    //
    //    public void updatePollText(Long optionId, String text) {
    //        PollOption option = pollOptionRepository
    //                .findById(optionId).orElseThrow(() -> new IllegalStateException((
    //                        "Question with id " + optionId + "does not exist"
    //                )));
    //
    //        if (text != null) {
    //            option.setText(text);
    //        }
    //    }

    /**
     * Gets the amount of poll options for a specific poll.
     *
     * @param pollId the poll id
     * @return the amount of poll options
     */
    public int getAmount(long pollId) {
        List<PollOption> pollOptions = pollOptionRepository.findAllByPollId(pollId);
        return pollOptions.size();
    }

    /**
     * This generates a new unique poll ID.
     *
     * @return the poll id
     */
    public long getPollOptionId() {
        Random generator = new Random();
        String numbers = "0123456789";
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            temp.append(numbers.charAt(generator.nextInt(numbers.length())));
        }
        long id = Long.parseLong(temp.toString());
        if (pollOptionRepository.findById(id).isPresent()) {
            return getPollOptionId();
        }
        return id;
    }

    /**
     * This returns the amount of votes for a poll option.
     *
     * @return the number of votes
     */
    public int getPollVotes(long id) {
        PollOption option = pollOptionRepository
                .findById(id).orElseThrow(() -> new IllegalStateException((
                        "Question with id " + id + "does not exist"
                )));
        return option.getScore();
    }
}
