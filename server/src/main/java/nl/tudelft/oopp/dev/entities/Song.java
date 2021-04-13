package nl.tudelft.oopp.dev.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//Here we can structure the database using Java/Spring
@Entity
@Table(name = "song")
public class Song {
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "lyrics")
    private String lyrics;

    @Column(name = "album")
    private String album;

    @Column(name = "year")
    private int year;

    public Song() {
    }

    /**
     * Create a new Song instance.
     */
    public Song(long id, String name, String lyrics, String album, int year) {
        this.id = id;
        this.name = name;
        this.lyrics = lyrics;
        this.album = album;
        this.year = year;
    }

    /**
     * Song constructor for the server side.
     * @param name - song name
     * @param lyrics - rap verse
     * @param album - album name
     * @param year - song release year
     */
    public Song(String name, String lyrics, String album, int year) {
        this.name = name;
        this.lyrics = lyrics;
        this.album = album;
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Song song = (Song) o;

        return id == song.id;
    }

    //You dont need to create a toJSON and return a string in the endpoint if you
    //have getters for the entity!


    public long getId() {
        return id;
    }

    public String getName() {
        return name; //Concatenate + "see what I told you"; and see what happens
    }

    public String getLyrics() {
        return lyrics;
    }

    public String getAlbum() {
        return album;
    }

    public int getYear() {
        return year;
    }
}