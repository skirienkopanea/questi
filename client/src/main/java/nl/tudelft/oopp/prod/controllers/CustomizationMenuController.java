package nl.tudelft.oopp.prod.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import nl.tudelft.oopp.prod.objects.Customization;

public class CustomizationMenuController {
    @FXML
    private ColorPicker fontColor;
    @FXML
    private ColorPicker cellColor;
    @FXML
    private Slider fontSize;
    @FXML
    private Button backgroundButton;

    private boolean changeBackground;

    /**
     * Initalize cusomization menu components.
     */
    @FXML
    public void initialize() {
        fontColor.setValue(Customization.fontColor);
        cellColor.setValue(Customization.cellColor);
        fontSize.setValue(Customization.fontSize);
    }

    /**
     * Prompt user for image url.
     */
    public void updateBackgroundPath() {
        TextInputDialog prompt = new TextInputDialog();
        prompt.setTitle("Enter image URL");
        prompt.setHeaderText("Image url");
        prompt.showAndWait();

        if (prompt.getEditor().getText().length() > 0) {
            Customization.backgroundPath = prompt.getEditor().getText();
            backgroundButton.setText(Customization.backgroundPath);
            changeBackground = true;
        }

    }

    /**
     * Apply changes.
     */
    public void applyChanges() {
        Customization.fontColor = fontColor.getValue();
        Customization.cellColor = cellColor.getValue();
        Customization.fontSize = (int) fontSize.getValue();

        if (changeBackground) {
            Customization.updateBackground = true;
        }

        close();
    }

    /**
     * Close menu window.
     */
    public void close() {
        Stage stage = (Stage) Stage.getWindows().get(1);
        stage.close();
        Customization.alreadyOpenWindow = false;
    }
}
