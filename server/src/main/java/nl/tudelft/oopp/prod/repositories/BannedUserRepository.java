package nl.tudelft.oopp.prod.repositories;

import java.util.List;
import java.util.Optional;
import nl.tudelft.oopp.prod.entities.BannedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("BannedUserRepository")
public interface BannedUserRepository extends JpaRepository<BannedUser, Long> {

    Optional<BannedUser> findBannedUserByUserIdAndLectureId(long userId, long lectureId);

    List<BannedUser> findBannedUsersByLectureId(long lectureId);

}
