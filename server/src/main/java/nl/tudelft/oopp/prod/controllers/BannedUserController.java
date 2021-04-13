package nl.tudelft.oopp.prod.controllers;

import java.util.List;
import nl.tudelft.oopp.prod.entities.BannedUser;
import nl.tudelft.oopp.prod.services.BannedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "bannedusers")
public class BannedUserController {

    private final BannedUserService bannedUserService;

    @Autowired
    public BannedUserController(BannedUserService bannedUserService) {
        this.bannedUserService = bannedUserService;
    }

    @GetMapping("/check/{lectureId}/{userId}")
    public boolean checkIfUserBanned(@PathVariable long lectureId, @PathVariable long userId) {
        return bannedUserService.checkIfUserBanned(lectureId, userId);
    }

    @PutMapping("/ban/{lectureId}/{userId}")
    public BannedUser banUser(@PathVariable long lectureId, @PathVariable long userId) {
        return bannedUserService.banUser(lectureId, userId);
    }

    @GetMapping("/{lectureId}")
    public List<BannedUser> getAllBannedUsersInLecture(@PathVariable long lectureId) {
        return bannedUserService.getAllBannedUsersInLecture(lectureId);
    }

}
