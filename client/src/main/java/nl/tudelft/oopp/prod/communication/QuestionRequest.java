package nl.tudelft.oopp.prod.communication;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import nl.tudelft.oopp.prod.objects.Question;

public class QuestionRequest {
    private static HttpClient client = HttpClient.newBuilder().build();
    private static final String URL = "http://localhost:8080/";
    private static Gson gson = new Gson();

    public static long getLastQuestionId() {
        return 0;
    }
    
    /**
     * This method is a  get request
     * for all questions in a lecture by lectureId.
     * endpoint : /lecture/getAllQuestions/{lectureId}
     * @param lectureId long lecture ID
     * @return A list of Questions
     */
    public static List<Question> getAllQuestionsByLectureId(String lectureId) {
        String id = lectureId.replaceAll("\\s+","");
        HttpRequest request = HttpRequest.newBuilder().GET()
                .uri(URI.create(URL + "lecture/getAllQuestions/" + id))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }

        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }

        return gson.fromJson(response.body(), new TypeToken<List<Question>>(){}.getType());
    }

    /**
     * This method posts a question onto the database.
     * endpoint: /question/ask
     * @param question Question object of client
     * @return Question object as saved by the server
     */
    public static Question askQuestion(Question question) {
        String bodyRequest = gson.toJson(question);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL + "question/askQuestion"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(bodyRequest))
                .build();
        HttpResponse<String> response = null;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }

        return gson.fromJson(response.body(), Question.class);
    }

    /**
     * This is a PUT request.
     * This changes the voteCount attribute of the question
     * endpoint: /question/upVote
     * @param question Question object
     */
    public static void upVoteQuestion(Question question, String userAlias) {
        String bodyRequest = gson.toJson(question);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL  + "question/upVote/" + userAlias))
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
     * This is a PUT request.
     * This changes edits the question
     * endpoint: /question/editQuestion
     * @param question Question object
     */
    public static void editQuestion(Question question) {
        String bodyRequest = gson.toJson(question);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL  + "question/editQuestion"))
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
     * javadoc.
     */
    public static void replyQuestion(Question question) {
        String bodyRequest = gson.toJson(question);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL  + "question/replyQuestion"))
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
     * This is a PUT request.
     * This changes the is_answered attribute of the question
     * endpoint: /question/answered
     * @param question Question object
     */
    public static void markAsAnswered(Question question) {
        question.setAnswered(true);
        String bodyRequest = gson.toJson(question);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL  + "question/answered"))
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
     * This is a PUT mapping for hiding a question.
     * @param question Question object that should be hidden.
     */
    public static void hideQuestion(Question question) {
        question.setHidden(true);
        String bodyRequest = gson.toJson(question);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL  + "question/hide"))
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
     * This is a DELETE request.
     * @param id id of the question that needs to be deleted
     */
    public static void deleteQuestion(long id) {
        HttpRequest request = HttpRequest.newBuilder().DELETE()
                .uri(URI.create(URL + "/question/delete/" + id))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (response.statusCode() != 200 && response.statusCode() != 204) {
            System.out.println("Status: " + response.statusCode());
        }
    }

    /**
     * Retrieves all answered question in the lecture.
     * @param lectureId The associated lecture Id
     * @return the List of answered questions, only the text format
     */
    public static List<String> retrieveAllAnsweredQuestionsInLecture(String lectureId) {
        String id = lectureId.replaceAll("\\s+","");
        HttpRequest request = HttpRequest.newBuilder().GET()
                .uri(URI.create(URL + "question/get/" + id + "/answered"))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }

        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }

        return gson.fromJson(response.body(), new TypeToken<List<String>>(){}.getType());
    }
}
