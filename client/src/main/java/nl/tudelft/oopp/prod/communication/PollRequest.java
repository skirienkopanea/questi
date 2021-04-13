package nl.tudelft.oopp.prod.communication;

import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import nl.tudelft.oopp.prod.objects.Poll;
import nl.tudelft.oopp.prod.objects.Session;



public class PollRequest {

    private static final HttpClient client = HttpClient.newBuilder().build();
    private static final Gson gson = new Gson();

    /**
     * Adds a new Poll to the current lecture.
     */
    public static Poll addPoll(long lectureId, String text) throws Exception {

        long pollId = getNewPollId();
        Poll poll = new Poll(pollId, lectureId, text, true);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/poll/add"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(poll)))
                .build();

        HttpResponse<String> response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Could not post user");
        }

        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }

        return poll;
    }

    /**
     * Returns a unique poll ID.
     */
    public static long getNewPollId() throws Exception {

        HttpRequest request = HttpRequest.newBuilder().GET()
                .uri(URI.create("http://localhost:8080/poll/pollId"))
                .build();

        HttpResponse<String> response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }

        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }
        return Long.parseLong(response.body());
    }

    /**
     * Gets the current pollQuestion of the lecture.
     */
    public static String getPollQuestion(long id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder().GET()
                .uri(URI.create("http://localhost:8080/poll/lecture/" + id))
                .build();

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }

        Poll poll = gson.fromJson(response.body(), new TypeToken<Poll>() {}.getType());
        if (poll.getId() != -1) {
            Session.setCurrentPoll(poll);
        } else {
            Session.setCurrentPoll(null);
        }
        return poll.getDescription();
    }

    /**
     * Gets the current poll of the lecture.
     */
    public static Poll getPoll(long id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder().GET()
                .uri(URI.create("http://localhost:8080/poll/lecturePoll/" + id))
                .build();

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }

        Poll poll = gson.fromJson(response.body(), new TypeToken<Poll>() {}.getType());

        return poll;
    }

    /**
     * Deletes the current pollQuestion of the lecture.
     */
    public static void deleteCurrentPoll(long id) throws Exception {

        HttpRequest request = HttpRequest.newBuilder().DELETE()
                .uri(URI.create("http://localhost:8080/poll/delete/" + id))
                .build();

        HttpResponse<String> response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Could not post user");
        }

        if (response.statusCode() != 200 && response.statusCode() != 204) {
            System.out.println("Status: " + response.statusCode());
        }

    }

    /**
     * Creates the speedPoll for the lectures.
     */

    public static void addSpeedPoll(long pollId, long lectureId) throws Exception {

        Poll poll = new Poll(pollId, lectureId, "lecturespeed", true);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/poll/add"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(poll)))
                .build();

        HttpResponse<String> response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Could not post user");
        }

        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }
    }

    /**
     * This method deactivates a poll.
     *
     * @param id the ID of the poll
     */
    public static void deactivateCurrentPoll(long id) {
        String bodyRequest = String.valueOf(id);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/poll/deactivate"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(bodyRequest))
                .build();

        HttpResponse<String> response = null;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (response.statusCode() != 200) {
            System.out.println("The status code is: " + response.statusCode());
        }
    }
}
