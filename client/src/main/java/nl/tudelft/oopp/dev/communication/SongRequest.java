package nl.tudelft.oopp.dev.communication;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.oopp.dev.objects.Song;

public class SongRequest {

    private static HttpClient client = HttpClient.newBuilder().build();

    private static Gson gson = new Gson();

    /**
     * Retrieves a list of songs from the server.
     * @param q the parameters for the get request to the server.
     * @return the parsed response
     */

    public static List<Song> getSongs(String q) {

        //Prepare a get request with the query parameters
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/jcole/search?q=" + q)).build();

        //Declare response object to hold later a response from the server.
        HttpResponse<String> response;

        try {
            //send the get request
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            e.printStackTrace();

            //return an empty list in case of error
            return List.of();

        }

        if (response.statusCode() != 200) {
            //log the response
            System.out.println("Status: " + response.statusCode());
        }

        //parse the response and return the list of songs!
        return gson.fromJson(response.body(), new TypeToken<List<Song>>(){}.getType());
    }

    /**
     * Returns a song from /test endpoint, which does not rely on a database query.
     * @return - a List of songs containing 1 song
     */
    public static List<Song> mockSong() {

        //Prepare a get request with the query parameters
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/test")).build();

        //Declare response object to hold later a response from the server.
        HttpResponse<String> response;

        List<Song> songs = new ArrayList<>();

        try {
            //send the get request
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String name = response.body();
            Song song = new Song(name, "KOD", 2021);
            songs.add(song);

        } catch (Exception e) {
            e.printStackTrace();

            //return an empty list in case of error
            return List.of();

        }

        if (response.statusCode() != 200) {
            //log the response
            System.out.println("Status: " + response.statusCode());
        }

        return songs;
    }
}
