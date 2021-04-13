package nl.tudelft.oopp.prod.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import nl.tudelft.oopp.prod.entities.BannedUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class BannedUserJpaTest {

    @Autowired
    private BannedUserRepository bannedUserRepository;

    @Test
    public void saveRetrieveBannedUserTest() {

        BannedUser bannedUser = new BannedUser(1L, 1L, 1L);

        bannedUser = bannedUserRepository.save(bannedUser);

        BannedUser bannedUser2 = bannedUserRepository.getOne(bannedUser.getId());

        assertEquals(bannedUser, bannedUser2);

    }

}
