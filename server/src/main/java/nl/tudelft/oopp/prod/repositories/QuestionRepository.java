package nl.tudelft.oopp.prod.repositories;

import java.util.List;
import java.util.Optional;

import nl.tudelft.oopp.prod.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository("QuestionRepository")
public interface QuestionRepository extends JpaRepository<Question, Long> {

    // Explicit naming here is just for organizational reasons, they can be omitted afaik

    @Override
    List<Question> findAll();

    List<Question> findAllByLectureId(long id);

    List<Question> findAllByAnsweredAndLectureId(boolean answered, long id);

    Optional<Question> findById(long id);

    boolean existsById(long id);

    void deleteById(long id);

}
