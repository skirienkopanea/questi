package nl.tudelft.oopp.prod.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import nl.tudelft.oopp.prod.entities.Lecture;
import nl.tudelft.oopp.prod.repositories.LectureRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

//OUTDATED TEST

@DataJpaTest
public class LectureJpaTest {

    @Autowired
    private LectureRepository lectureRepository;

    @Test
    public void saveRetrieveLectureTest() {

        Lecture insertLecture = new Lecture(
                1,
                "moderator",
                "student",
                "test lecture",
                true,
                new Date(System.currentTimeMillis()),
                new Date(System.currentTimeMillis())
        );

        //INSERT
        insertLecture = lectureRepository.saveAndFlush(insertLecture);

        //GET
        Lecture getLecture = lectureRepository.getOne(insertLecture.getId());

        //ASSERT
        assertEquals(insertLecture, getLecture);
    }

}
