package nl.tudelft.oopp.prod.controllers;

import java.util.List;

import nl.tudelft.oopp.prod.entities.Poll;
import nl.tudelft.oopp.prod.entities.User;
import nl.tudelft.oopp.prod.services.PollService;

import org.springframework.beans.factory.annotation.Autowired;
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
|                    == The Poll Entity ==
|----------------------------------------------------------------------
|
| The poll entity is the main tool in which the moderators can quiz
|students, get the mood of the room and ask meta questions (how is the
|pace of the lecture, do you like the format etc.).
|Each poll has a variable number of poll options, a specific id, a
|description and can't exist without a lecture.
|When creating a new room, a poll about the pace of the lecture will
|be generated at the same time with the lecture. This poll remains
|active throughout the whole lecture and has a special place in both
|the moderator and student screens, always having the id 0.
| ----------------------------------------------------------------------
|   ==Structure==
|
|        PollController(+Tests) -------------\
|            |___PollService(+Tests) --------| > Poll(Entity)
|                |___PollRepository --------/
|
| ----------------------------------------------------------------------
|    ==Database Schema Dependencies==
|
|        LectureId <------------ Poll <------------ PollOptionId
|
 */


/**
 * The type Poll controller.
 */
@RestController
@RequestMapping(path = "poll")
public class PollController {

    private final PollService pollService;

    /**
     * Instantiates a new Poll controller and autowires it.
     *
     * @param pollService the poll service
     */
    @Autowired
    public PollController(PollService pollService) {
        this.pollService = pollService;
    }


    /**
     * Generates a new poll Id.
     *
     * @return the new poll id
     */
    @GetMapping("/pollId")
    public long getNewPollId() {
        return pollService.getPollId();
    }

    /**
     * Gets all polls in the database.
     *
     * @return A list of all polls
     */
    @GetMapping
    public List<Poll> getAllPolls() {
        return pollService.getAllPolls();
    }

    /**
     * Gets a live poll from a specific lecture.
     *
     * @param id the id of the lecture
     * @return the live poll
     */
    @GetMapping("/lecture/{id}")
    public Poll getLivePoll(@PathVariable long id) {
        return pollService.getLivePoll(id);
    }

    /**
     * Insert a poll into the database.
     *
     * @param poll the poll to be inserted
     */
    @PostMapping("/add")
    public Poll insertPoll(@RequestBody Poll poll) {
        return pollService.insertPoll(poll);
    }

    /**
     * Delete a poll from the database.
     *
     * @param pollId the id of the poll to be deleted
     */
    @DeleteMapping(path = "/delete/{pollId}")
    public void deletePoll(@PathVariable("pollId") long pollId) {
        pollService.deletePoll(pollId);
    }

    /**
     * Update a poll.
     *
     * @param pollId      - REQUIRED - the poll id
     * @param lectureId   - OPTIONAL - the lecture id
     * @param description - OPTIONAL - the description
     */
    @PutMapping(path = "{pollId}")
    public Poll updatePoll(
            @PathVariable("pollId") Long pollId,
            @RequestParam(required = false) long lectureId,
            @RequestParam(required = false) String description) {
        return pollService.updatePoll(pollId, lectureId, description);
    }

    /**
     * Deactivate a poll.
     *
     * @param string containing the poll ID
     *
     */
    @PutMapping(path = "/deactivate")
    public Poll deactivatePoll(@RequestBody String string) {
        return pollService.deactivate(string);
    }
}
