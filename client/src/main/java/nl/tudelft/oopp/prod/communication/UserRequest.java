package nl.tudelft.oopp.prod.communication;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import nl.tudelft.oopp.prod.objects.Lecture;
import nl.tudelft.oopp.prod.objects.User;

public class UserRequest {

    private static HttpClient client = HttpClient.newBuilder().build();

    private static Gson gson = new Gson();


    /**
     * Returns a user based on its id, does not need lecture id.
     */
    public static long getNewUserId() throws Exception {

        //Prepare a get request with the query parameters
        HttpRequest request = HttpRequest.newBuilder().GET()
                .uri(URI.create("http://localhost:8080/users/userid"))
                .build();

        //TODO: Refractor this repetitive code into a static method.
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

        //parse the response and return the user
        return Long.parseLong(response.body());
    }

    /**
     * Returns a user based on its id, does not need lecture id.
     */
    public static User getUser(String id) throws Exception {

        //Prepare a get request with the query parameters
        HttpRequest request = HttpRequest.newBuilder().GET()
                .uri(URI.create("http://localhost:8080/users/user?id=" + id))
                .build();

        //TODO: Refractor this repetitive code into a static method.
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

        //parse the response and return the user
        return gson.fromJson(response.body(), new TypeToken<User>() {
        }.getType());
    }

    /**
     * Adds a user to a lecture.
     * It will throw an exception if the FK constraint to an existing Lecture is not met.
     */
    public static User addUser(String alias, long lectureId, boolean mod) throws Exception {

        //This local user id needs to be changed to a random thing.

        User user = new User(getNewUserId(), alias, lectureId, mod);

        //Prepare a get request with the query parameters
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/users/join"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(user)))
                .build();

        //TODO: Refactor this repetitive code into a static method.
        //Declare response object to hold later a response from the server.
        HttpResponse<String> response;

        try {
            //send the get request
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            //Fetch user now.

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Could not post user");
        }

        if (response.statusCode() != 200) {
            //log the response
            System.out.println("Status: " + response.statusCode());
        }

        return user; //since insert was OK, there is no need (and no practical way tbh)
        // to fetch it from the db, the db only validates that the id is unique.
    }

    /**
     * DELETE request for deleting a user after he leaves.
     * @param user User object
     */
    public static void deleteUser(User user) throws Exception {
        long id = user.getId();
        HttpRequest request = HttpRequest.newBuilder().DELETE()
                .uri(URI.create("http://localhost:8080/users/delete/" + id))
                .build();

        HttpResponse<String> response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Could not delete user");
        }

        if (response.statusCode() != 200 && response.statusCode() != 204) {
            System.out.println("Status: " + response.statusCode());
        }
    }

    /**
     * GET request to see if a user has been banned.
     * @param user user to check
     * @return boolean result
     * @throws Exception checkstyle
     */
    public static boolean checkIfUserBanned(User user) throws Exception {

        long userId = user.getId();
        long lectureId = user.getLectureId();

        HttpRequest request = HttpRequest.newBuilder().GET()
                .uri(URI.create("http://localhost:8080/bannedusers/check/" + lectureId + "/" + userId))
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

        return Boolean.parseBoolean(response.body());

    }

    /**
     *  PUT request to ban a user.
     * @param user user to be banned
     * @throws Exception checkstyle
     */
    public static void banUser(User user) throws Exception {

        long userId = user.getId();
        long lectureId = user.getLectureId();

        String bodyRequest = new String("");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/bannedusers/ban/" + lectureId + "/" + userId))
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
