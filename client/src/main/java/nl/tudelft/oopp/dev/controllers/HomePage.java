package nl.tudelft.oopp.dev.controllers;

import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import nl.tudelft.oopp.dev.communication.SongRequest;
import nl.tudelft.oopp.dev.objects.Song;

//This class was called from the JavaFX homepage.fxml
//This class contains all the event listener methods for the view.

public class HomePage {

    //We need to add the id's and object type of each of the Scene objects to the controller
    @FXML
    private TextField input;

    @FXML
    private ListView<Song> songList;

    @FXML
    private Button submit; //actually we don't need to declare it because we don't use it,
    //you can see in homepage.fxml that 'submit' has onAction="#searchSongs"

    /**
     * Handles clicking the button.
     */
    public void searchSongs() {

        // This calls for a method that makes a query and
        // returns a list of songs (and assigns it to return)
        // getText() is a default javafx method
        List<Song> songs = SongRequest.getSongs(input.getText());

        //This will clear the contents of songList
        songList.getItems().clear(); //these are default javafx methods too

        //This will output the queried song list into the javafx object songList
        songList.getItems().addAll(songs);
    }

    /**
     * Handles clicking the button.
     */
    public void testButton() {

        //This calls for a method that makes a query and
        // returns a list of songs (and assigns it to return)
        // getText() is a default javafx method
        List<Song> songs = SongRequest.mockSong();

        //This will clear the contents of songList
        songList.getItems().clear(); //these are default javafx methods too

        //This will output the queried song list into the javafx object songList
        songList.getItems().addAll(songs);
    }
}
