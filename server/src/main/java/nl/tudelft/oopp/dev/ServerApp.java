package nl.tudelft.oopp.dev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/* Boiler plate code*/
@SpringBootApplication
public class ServerApp {

    //I suppose this will run all classes, which include the endpoints.
    public static void main(String[] args) {
        SpringApplication.run(ServerApp.class, args);
    }
}
