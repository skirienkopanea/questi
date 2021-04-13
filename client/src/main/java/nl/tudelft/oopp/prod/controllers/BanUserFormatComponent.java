package nl.tudelft.oopp.prod.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import nl.tudelft.oopp.prod.communication.UserRequest;
import nl.tudelft.oopp.prod.objects.User;


public class BanUserFormatComponent extends VBox {

    @FXML
    private Text username;

    @FXML
    private Button banButton;

    User user;

    /**
     * User format component for each user.
     * @param user user.
     */
    public BanUserFormatComponent(User user) {

        try {

            URL url = new File("client/src/main/resources/banUserFormat.fxml").toURI().toURL();

            FXMLLoader fxmlLoader = new FXMLLoader(url);
            fxmlLoader.load();

        } catch (IOException exception) {

            exception.printStackTrace();

        }

        this.user = user;
        // username.setText(user.getAlias());

    }

    /**
     * Banning a user.
     */
    public void ban() {

        try {
            UserRequest.banUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
