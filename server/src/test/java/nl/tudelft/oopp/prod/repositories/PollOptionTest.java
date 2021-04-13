package nl.tudelft.oopp.prod.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import nl.tudelft.oopp.prod.entities.PollOption;
import nl.tudelft.oopp.prod.repositories.PollOptionRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class PollOptionTest {

    @Autowired
    private PollOptionRepository pollOptionRepository;

    @Test
    public void saveRetrievePollOptionTest() {
        PollOption option = new PollOption();
        option.setScore(42);

        // save option
        option = pollOptionRepository.save(option);

        // retrieve option
        PollOption option2 = pollOptionRepository.getOne(option.getId());
        assertEquals(option, option2);
    }
}
