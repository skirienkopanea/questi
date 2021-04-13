package nl.tudelft.oopp.dev.config;

import java.util.List;
import java.util.Optional;
import nl.tudelft.oopp.dev.entities.Song;
import nl.tudelft.oopp.dev.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Load postgre.
 */

@Service
public class DataBaseLoader {
    private SongRepository songRepository;

    @Autowired
    public DataBaseLoader(@Qualifier("SongRepository") SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    /**
     * Song queries.
     */
    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    public List<Song> searchSongs(String q) {
        return songRepository.findAllByLyricsContains(q);
    }

    /**
     * Insert song query.
     * @param s - song object
     */
    public void insertSong(Song s) {
        Optional<Song> songOptional = songRepository.findById(s.getId());
        if (songOptional.isPresent()) {
            throw new IllegalArgumentException("id already in use");
        }
        songRepository.save(s);
        System.out.println(s);
    }

}