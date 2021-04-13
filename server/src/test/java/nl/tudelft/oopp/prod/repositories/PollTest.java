package nl.tudelft.oopp.prod.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import nl.tudelft.oopp.prod.entities.Poll;
import nl.tudelft.oopp.prod.repositories.PollRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class PollTest {

    @Autowired
    private PollRepository pollRepository;

}
