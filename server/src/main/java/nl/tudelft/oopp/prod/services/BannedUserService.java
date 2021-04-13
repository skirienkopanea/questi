package nl.tudelft.oopp.prod.services;

import java.util.List;
import java.util.Optional;
import nl.tudelft.oopp.prod.entities.BannedUser;
import nl.tudelft.oopp.prod.entities.Lecture;
import nl.tudelft.oopp.prod.entities.User;
import nl.tudelft.oopp.prod.repositories.BannedUserRepository;
import nl.tudelft.oopp.prod.repositories.LectureRepository;
import nl.tudelft.oopp.prod.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BannedUserService {

    private final BannedUserRepository bannedUserRepository;
    private final LectureRepository lectureRepository;
    private final UserRepository userRepository;

    /**
     * Constructor.
     * @param bannedUserRepository checkstyle
     * @param lectureRepository checkstyle
     * @param userRepository checkstyle
     */
    @Autowired
    public BannedUserService(BannedUserRepository bannedUserRepository,
                             LectureRepository lectureRepository,
                             UserRepository userRepository) {
        this.bannedUserRepository = bannedUserRepository;
        this.lectureRepository = lectureRepository;
        this.userRepository = userRepository;
    }

    /**
     * Check if a user has been banned.
     * @param lectureId lectureId
     * @param userId userId
     * @return boolean
     */
    public boolean checkIfUserBanned(long lectureId, long userId) {

        Optional<BannedUser> bannedUser = bannedUserRepository
                .findBannedUserByUserIdAndLectureId(userId, lectureId);
        if (bannedUser.isEmpty()) {
            return false;
        } else {
            return true;
        }

    }

    /**
     * Ban user.
     * @param lectureId lectureId
     * @param userId userId.
     */
    public BannedUser banUser(long lectureId, long userId) {

        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(() ->
                new IllegalStateException((
                        "Lecture with id " + lectureId + "does not exist"
                )));
        User user = userRepository.findById(userId).orElseThrow(() ->
                new IllegalStateException((
                        "User with id " + userId + "does not exist"
                )));
        BannedUser bannedUser = new BannedUser();
        bannedUser.setLectureId(lecture.getId());
        bannedUser.setUserId(user.getId());
        return bannedUserRepository.save(bannedUser);

    }

    /**
     * Gets all the banned users in a lecture.
     * @param lectureId The id of the lecture
     * @return List of all banned users
     */
    public List<BannedUser> getAllBannedUsersInLecture(long lectureId) {

        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(() ->
                 new IllegalStateException((
                         "Lecture with id " + lectureId + "does not exist"
                 )));

        return bannedUserRepository.findBannedUsersByLectureId(lectureId);

    }

}
