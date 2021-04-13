package nl.tudelft.oopp.prod.repositories;

import java.util.List;
import java.util.Optional;

import nl.tudelft.oopp.prod.entities.PollOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("PollOptionRepository")
public interface PollOptionRepository extends JpaRepository<PollOption, Long> {

    //Explicit definition of known methods

    @Override
    List<PollOption> findAll();

    List<PollOption> findAllByPollId(long id);

    Optional<PollOption> findById(long id);

    boolean existsById(long id);

    void deleteById(long id);


}


