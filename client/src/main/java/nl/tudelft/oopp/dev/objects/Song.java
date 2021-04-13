package nl.tudelft.oopp.dev.objects;

public class Song {

    private String name;
    private String album;
    private int year;

    /**
     * Song object on the client side.
     * @param name - song name
     * @param album - album name
     * @param year - song release date
     */
    public Song(String name, String album, int year) {
        this.name = name;
        this.album = album;
        this.year = year;
    }

    @Override
    public String toString() {
        return name + " - " + album + " (" + year + ")";
    }
}
