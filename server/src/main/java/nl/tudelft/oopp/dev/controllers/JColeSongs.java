package nl.tudelft.oopp.dev.controllers;

import java.util.List;
import nl.tudelft.oopp.dev.config.DataBaseLoader;
import nl.tudelft.oopp.dev.entities.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("jcole") //this is like a directory /jcole
public class JColeSongs {

    private final DataBaseLoader songService;

    @Autowired
    public JColeSongs(DataBaseLoader songService) {
        this.songService = songService;
    }

    /**
     * GET Endpoint to retrieve a random quote.
     *
     * @return "hi"
     */
    @GetMapping("search") //this is actually /jcole/search
    @ResponseBody
    public List<Song> getSongs(@RequestParam String q) {
        return songService.searchSongs(q);
    }

    /**
     * Post request to add song.
     * @param q - name of the song
     */
    @PostMapping("add")
    public void insertSong(@RequestBody String q) {
        String lyrics = "yes";
        String album = "Cooking points";
        int year = 2021;
        songService.insertSong(new Song(q, lyrics, album, year));
    }
}