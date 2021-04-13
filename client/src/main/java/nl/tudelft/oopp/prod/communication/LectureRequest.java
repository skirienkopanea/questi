package nl.tudelft.oopp.prod.communication;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

import nl.tudelft.oopp.prod.objects.Lecture;
import nl.tudelft.oopp.prod.objects.Question;
import nl.tudelft.oopp.prod.objects.User;

public class LectureRequest {

    private static HttpClient client = HttpClient.newBuilder().build();

    private static Gson gson = new Gson();

    /**
     * Retrieves lecture.
     *
     * @param publicLink public link
     * @return lecture object
     * @throws Exception an Exception on server communication failure
     */
    public static Lecture getLecture(String publicLink) throws Exception {

        //Prepare a get request with the query parameters
        HttpRequest request = HttpRequest.newBuilder().GET()
                .uri(URI.create("http://localhost:8080/lecture/get?link=" + publicLink))
                .build();

        //Declare response object to hold later a response from the server.
        HttpResponse<String> response;

        try {
            //send the get request
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }

        if (response.statusCode() != 200) {
            //log the response
            System.out.println("Status: " + response.statusCode());
        }

        //parse the response and return the list of songs!
        return gson.fromJson(response.body(), new TypeToken<Lecture>() {
        }.getType());
    }

    /**
     * Searches lecture id with moderator Link.
     */
    public static Lecture getModeratorLecture(String moderatorLink) throws Exception {

        //Prepare a get request with the query parameters
        HttpRequest request = HttpRequest.newBuilder().GET()
                .uri(URI.create("http://localhost:8080/lecture/getm?link=" + moderatorLink))
                .build();

        //Declare response object to hold later a response from the server.
        HttpResponse<String> response;

        try {
            //send the get request
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }

        if (response.statusCode() != 200) {
            //log the response
            System.out.println("Status: " + response.statusCode());
        }

        //parse the response and return the list of songs!
        return gson.fromJson(response.body(), new TypeToken<Lecture>() {
        }.getType());
    }

    /**
     * Gets all user aliases from database.
     */
    public static List<User> getAllUserAliases(long lectureId) throws Exception {

        HttpRequest request = HttpRequest.newBuilder().GET()
                .uri(URI.create("http://localhost:8080/lecture/allUsers/" + lectureId))
                .build();

        HttpResponse<String> response;

        try {
            //send the get request
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }

        if (response.statusCode() != 200) {
            //log the response
            System.out.println("Status: " + response.statusCode());
        }

        List<User> userList = null;
        if (gson != null) {
            userList = gson.fromJson(response.body(), new TypeToken<List<User>>() {
            }.getType());
        }

        return userList;

    }

    /**
     * Requests DB for a random unique lecture id.
     */
    public static long getGeneratedLectureId() throws Exception {

        //Prepare a get request with the query parameters
        HttpRequest request = HttpRequest.newBuilder().GET()
                .uri(URI.create("http://localhost:8080/lecture/make/id"))
                .build();

        //Declare response object to hold later a response from the server.
        HttpResponse<String> response;

        try {
            //send the get request
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }

        if (response.statusCode() != 200) {
            //log the response
            System.out.println("Status: " + response.statusCode());
        }

        //parse the response and return the list of songs!
        return gson.fromJson(response.body(), new TypeToken<Long>() {
        }.getType());
    }

    /**
     * Requests the db for a random unique token.
     */
    public static String getGeneratedToken() throws Exception {

        //Prepare a get request with the query parameters
        HttpRequest request = HttpRequest.newBuilder().GET()
                .uri(URI.create("http://localhost:8080/lecture/make/token"))
                .build();

        //Declare response object to hold later a response from the server.
        HttpResponse<String> response;

        try {
            //send the get request
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }

        if (response.statusCode() != 200) {
            //log the response
            System.out.println("Status: " + response.statusCode());
        }

        //parse the response and return the list of songs!
        return gson.fromJson(response.body(), new TypeToken<String>() {
        }.getType());
    }

    /**
     * Posts a lecture in lecture table.
     */
    public static void postLecture(Lecture lecture) throws Exception {

        //Prepare a get request with the query parameters
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/lecture/post"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(lecture.toJson()))
                .build();

        //Declare response object to hold later a response from the server.
        HttpResponse<String> response;

        try {
            //send the get request
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }

        if (response.statusCode() != 200) {
            //log the response
            System.out.println("Status: " + response.statusCode());
        }
    }

    /**
     * Post request for starting a direct lecture.
     * @param lecture Lecture object
     */
    public static void postDirectLecture(Lecture lecture) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/lecture/post"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(lecture)))
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
    }

    /**
     * PUT request for ending the lecture.
     * @param lecture Lecture object
     */
    public static void endLecture(Lecture lecture) {
        String bodyRequest = String.valueOf(lecture.getId());
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/lecture/stop"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(bodyRequest))
                .build();
        HttpResponse<String> response = null;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        if (response.statusCode() != 200) {
            System.out.println("The status code is: " + response.statusCode());
        }
    }

    /**
     * GET request for check if the lecture has been started.
     * @param id of the lecture that needs to be checked
     */
    public static boolean isOngoing(long id) throws Exception {

        HttpRequest request = HttpRequest.newBuilder().GET()
                .uri(URI.create("http://localhost:8080/lecture/ongoing?link=" + id))
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

        return Boolean.parseBoolean(response.body());
    }

    /**
     * PUT request for starting the lecture.
     * @param lecture Lecture object
     */
    public static void startLecture(Lecture lecture) {
        String bodyRequest = String.valueOf(lecture.getId());
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/lecture/start"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(bodyRequest))
                .build();

        HttpResponse<String> response = null;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        if (response.statusCode() != 200) {
            System.out.println("The status code is: " + response.statusCode());
        }
    }

    /**
     * DELETE request for deleting the users after ending the lecture.
     * @param lecture Lecture object
     */
    public static void deleteUsers(Lecture lecture) throws Exception {
        long id = lecture.getId();
        HttpRequest request = HttpRequest.newBuilder().DELETE()
                .uri(URI.create("http://localhost:8080/lecture/deleteusers/" + id))
                .build();

        HttpResponse<String> response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Could not delete users");
        }

        if (response.statusCode() != 200 && response.statusCode() != 204) {
            System.out.println("Status: " + response.statusCode());
        }
    }

    /**
     * Requests all logs from server.
     * @param filename name of file.
     * @return String with all logs
     * @throws Exception error
     */
    public static String getServerLogs(String filename) throws Exception {

        //Prepare a get request with the query parameters
        HttpRequest request = HttpRequest.newBuilder().GET()
                .uri(URI.create("http://localhost:8080/lecture/getlog/" + filename))
                .build();

        //Declare response object to hold later a response from the server.
        HttpResponse<String> response;

        try {
            //send the get request
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }

        if (response.statusCode() != 200) {
            //log the response
            System.out.println("Status: " + response.statusCode());
        }

        return response.body();

    }

    /**
     * This method checks if the lecture has been rate limited.
     * @param lecture lecture
     * @throws Exception exception
     */
    public static boolean getRateLimited(Lecture lecture) throws Exception {

        long lectureId =  lecture.getId();

        HttpRequest request = HttpRequest.newBuilder().GET()
                .uri(URI.create("http://localhost:8080/lecture/isratelimited/" + lectureId))
                .build();

        HttpResponse<String> response;

        try {
            //send the get request
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }

        if (response.statusCode() != 200) {
            //log the response
            System.out.println("Status: " + response.statusCode());
        }

        return Boolean.parseBoolean(response.body());

    }

    /**
     * This method enables a lecture's rate limit.
     * @param lecture lecture
     * @throws Exception exception
     */
    public static void enableRateLimit(Lecture lecture) throws Exception {

        long lectureId =  lecture.getId();

        String bodyRequest = new String("");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/lecture/enableratelimit/" + lectureId))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(bodyRequest))
                .build();

        //Declare response object to hold later a response from the server.
        HttpResponse<String> response;

        try {
            //send the get request
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }

        if (response.statusCode() != 200) {
            //log the response
            System.out.println("Status: " + response.statusCode());
        }

    }

    /**
     * This method disables a lecture's rate limit.
     * @param lecture lecture
     * @throws Exception exception
     */
    public static void disableRateLimit(Lecture lecture) throws Exception {

        long lectureId =  lecture.getId();

        String bodyRequest = new String("");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/lecture/disableratelimit/" + lectureId))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(bodyRequest))
                .build();

        //Declare response object to hold later a response from the server.
        HttpResponse<String> response;

        try {
            //send the get request
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }

        if (response.statusCode() != 200) {
            //log the response
            System.out.println("Status: " + response.statusCode());
        }

    }

}


