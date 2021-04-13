package nl.tudelft.oopp.prod.controllers;

import java.net.URI;
import java.util.List;

import nl.tudelft.oopp.prod.entities.PollOption;
import nl.tudelft.oopp.prod.entities.Question;
import nl.tudelft.oopp.prod.entities.User;
import nl.tudelft.oopp.prod.services.PollOptionService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/*
|----------------------------------------------------------------------
|                    == The PollOption Entity ==
|----------------------------------------------------------------------
|
|The PollOption entity allows for flexibility in number of choices of
a poll. Thus, a poll can have <technically> an unlimited number of
option.
Each PollOption has an id, the id of the poll it's related, a text
description and a number of votes.

| ----------------------------------------------------------------------
|   ==Structure==
|
|        PollOptionController(+Tests) -------------\
|            |___PollOptionService(+Tests) --------| > PollOption(Entity)
|                |___PollOptionRepository --------/
|
| ----------------------------------------------------------------------
|    ==Database Schema Dependencies==
|
|        PollId <------------ PollOption
|
 */

/**
 * The type Poll option controller.
 */
@RestController
@RequestMapping(path = "pollOption")
public class PollOptionController {

    private final PollOptionService pollOptionService;

    /**
     * Instantiates a new Poll option controller and autowires it.
     *
     * @param pollOptionService the poll option service
     */
    @Autowired
    public PollOptionController(PollOptionService pollOptionService) {
        this.pollOptionService = pollOptionService;
    }

    /**
     * Method that returns all poll options.
     * @return A list of poll options
     */
    @GetMapping
    public List<PollOption> getAllOptions() {
        return pollOptionService.getAllPollOptions();
    }

    /**
     * Generates a new poll Id.
     *
     * @return the new poll id
     */
    @GetMapping("/pollId")
    public long getNewPollId() {
        return pollOptionService.getPollOptionId();
    }

    /**
     * Gets all options from a specific poll.
     *
     * @param id the id of the poll
     * @return A list of poll options for a specific poll
     */
    @GetMapping("/inpoll/{id}")
    public List<PollOption> getAllOptionsFromPoll(@PathVariable long id) {
        return pollOptionService.getAllPollOptionsByPollId(id);
    }

    /**
     * Method that retrieves the number of votes.
     *
     * @param id the id
     * @return the number of votes
     */
    @GetMapping("/votes")
    public int getVotes(@RequestParam String id) {
        return pollOptionService.getVotes(id);
    }

    @GetMapping("/pollvotes")
    public int getPollVotes(@RequestParam long id) {
        return pollOptionService.getPollVotes(id);
    }

    /**
     * Method that retrieves the amount of poll options for a specific poll.
     *
     * @param pollId the poll id
     * @return the amount of poll options
     */
    @GetMapping("/amount")
    public int getAmount(@RequestParam long pollId) {
        return pollOptionService.getAmount(pollId);
    }

    /**
     * Insert a poll option.
     *
     * @param option the poll option to be inserted
     */
    @PostMapping("/save")
    public PollOption insertPollOption(@RequestBody PollOption option) {
        return pollOptionService.insertPollOption(option);
    }

    /**
     * Downvote a poll option.
     *
     * @param string containing the pollID
     * @return updated score
     */
    @PutMapping(path = "/downVote")
    public int downVoteQuestionPollOption(@RequestBody String string) {
        return pollOptionService.downVote(string);
    }

    /**
     * Upvote a poll option.
     *
     * @param string containing the pollOptionID
     * @return updated score
     */
    @PutMapping(path = "/upVoteQuestion")
    public int upVoteQuestionPollOption(@RequestBody String string) {
        return pollOptionService.upVoteQuestion(string);
    }

    /**
     * Downvote a poll option.
     *
     * @param string containing the pollOptionID
     * @return updated score
     */
    @PutMapping(path = "/downVoteQuestion")
    public int downVotePollOption(@RequestBody String string) {
        return pollOptionService.downVoteQuestion(string);
    }

    /**
     * Upvote a poll option.
     *
     * @param string containing the pollID
     * @return updated score
     */
    @PutMapping(path = "/upVote")
    public int upVotePollOption(@RequestBody String string) {
        return pollOptionService.upVote(string);
    }

    /**
     * Delete a poll option.
     *
     * @param pollId the poll option id
     */
    @DeleteMapping(path = "delete/{pollId}")
    public void deletePollOption(@PathVariable("pollId") long pollId) {
        pollOptionService.deletePollOption(pollId);
    }

}
