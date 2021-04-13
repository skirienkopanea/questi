package nl.tudelft.oopp.prod.communication;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import nl.tudelft.oopp.prod.objects.PollOption;
import nl.tudelft.oopp.prod.objects.Session;

public class PollOptionRequest {

    private static final HttpClient client = HttpClient.newBuilder().build();
    private static final String URL = "http://localhost:8080/";
    private static final Gson gson = new Gson();

    /**
     * Creates three speed options for the speed poll.
     */
    public static void addSpeedOptions(long id) throws Exception {
        for (int i = 1; i <= 3; i++) {
            PollOption pollOption = new PollOption(getNewPollOptionId(), id, "" + i, 0);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL + "pollOption/save"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(pollOption)))
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
    }

    /**
     * Returns the amount of votes a poll option has.
     * @Param id: id of the poll
     * @Param i: the index of the poll option
     */

    public static int getVotes(long id, int i) {
        String temp = id + "/" + i;
        HttpRequest request = HttpRequest.newBuilder().GET()
                .uri(URI.create(URL + "/pollOption/votes?id=" + temp))
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
        return Integer.parseInt(response.body());
    }

    /**
     * Downvotes the pollOption with index i.
     */
    public static int downVote(int i) throws Exception {
        String bodyRequest = Session.getLecture().getId() + "/" + i;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL  + "pollOption/downVote"))
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
        return Integer.parseInt(response.body());
    }

    /**
     * Upvotes a polloption with index i.
     */
    public static int upVote(int i) throws Exception {
        String bodyRequest = Session.getLecture().getId() + "/" + i;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL  + "pollOption/upVote"))
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
        return Integer.parseInt(response.body());
    }

    /**
     * Get the number of options for a poll.
     */
    public static int getNumberOfOptions(long pollId) throws Exception {
        HttpRequest request = HttpRequest.newBuilder().GET()
                .uri(URI.create(URL + "/pollOption/amount?pollId=" + pollId))
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
        return Integer.parseInt(response.body());
    }

    /**
     * save a new poll option in the database.
     */
    public static void save(PollOption option) throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL + "/pollOption/save"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(option)))
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
     * delete all poll options for a specific poll.
     */
    public static void deleteCurrentPoll(long id) throws Exception {

        HttpRequest request = HttpRequest.newBuilder().DELETE()
                .uri(URI.create("http://localhost:8080/pollOption/delete/" + id))
                .build();

        //TODO: Refactor this repetitive code into a static method.
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
     * Get the current list of all possible options for a specific poll.
     */
    public static List<PollOption> getListOfOptions(long pollId) {
        HttpRequest request = HttpRequest.newBuilder().GET()
                .uri(URI.create(URL + "/pollOption/inpoll/" + pollId))
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
        return gson.fromJson(response.body(), new TypeToken<List<PollOption>>() {
        }.getType());
    }

    /**
     * This method retrieves a new PollOptionID.
     */
    public static long getNewPollOptionId() throws Exception {

        HttpRequest request = HttpRequest.newBuilder().GET()
                .uri(URI.create("http://localhost:8080/pollOption/pollId"))
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
     * Downvotes the pollOption with index i.
     */
    public static int downVoteQuestion(long id) throws Exception {
        String bodyRequest = String.valueOf(id);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL  + "pollOption/downVoteQuestion"))
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
        return Integer.parseInt(response.body());
    }

    /**
     * Upvotes a polloption with index i.
     */
    public static int upVoteQuestion(long id) throws Exception {
        String bodyRequest = String.valueOf(id);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL  + "pollOption/upVoteQuestion"))
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
        return Integer.parseInt(response.body());
    }

    /**
     * This method retrieves the number of votes on a certain poll option.
     * in a lecture with its corresponding lectureId.
     *
     * @param id the ID of the poll option
     * @return string of the number of votes.
     */
    public static String getPollVotes(String id) {

        HttpRequest request = HttpRequest.newBuilder().GET()
                .uri(URI.create(URL + "/pollOption/pollvotes?id=" + id))
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
        return response.body();
    }
}
