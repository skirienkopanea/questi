package nl.tudelft.oopp.prod.repositories;

import java.util.List;
import java.util.Optional;
import nl.tudelft.oopp.prod.entities.Lecture;
import nl.tudelft.oopp.prod.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("UserRepository")
public interface UserRepository extends JpaRepository<User, Long> {

    //Explicit definition of known methods

    @Override
    List<User> findAll();

    List<User> findAllByLecture(Lecture lecture);

    Optional<User> findById(long id);

    boolean existsById(long id);

    Optional<User> findByAlias(String alias);

    void deleteById(long id);
}



