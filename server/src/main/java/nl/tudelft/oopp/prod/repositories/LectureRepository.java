package nl.tudelft.oopp.prod.repositories;

import java.util.Optional;
import nl.tudelft.oopp.prod.entities.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("LectureRepository")
public interface LectureRepository extends JpaRepository<Lecture, Long> {

    Optional<Lecture> findById(long id);

    Optional<Lecture> findByPublicLink(String publicLink);

    Optional<Lecture> findByModeratorLink(String moderatorLink);

    void deleteById(long id);

}
