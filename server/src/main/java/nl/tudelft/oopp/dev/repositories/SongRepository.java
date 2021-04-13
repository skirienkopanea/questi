package nl.tudelft.oopp.dev.repositories;

import java.util.List;
import nl.tudelft.oopp.dev.entities.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//You can see that we have a Song with ID of type Long

//Define db query for the SongRepository. This is a JPA based interface
//you can make up methods and JPA will create them

@Repository("SongRepository")
public interface SongRepository extends JpaRepository<Song, Long> {
    //This is an interface method. Therefore it does not have a body
    List<Song> findAllByLyricsContains(String query);
}
