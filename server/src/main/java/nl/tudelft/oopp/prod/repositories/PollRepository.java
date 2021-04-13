package nl.tudelft.oopp.prod.repositories;

import java.util.List;
import java.util.Optional;

import nl.tudelft.oopp.prod.entities.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("PollRepository")
public interface PollRepository extends JpaRepository<Poll, Long> {

    List<Poll> findAll();

    List<Poll> findByLectureId(long id);

    Optional<Poll> findById(long id);

    boolean existsById(long id);

    void deleteById(long id);

}
