package nz.ac.auckland.se206.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import nz.ac.auckland.se206.App;

public class LockController {

    @FXML
    private Button exitbutton;

    @FXML
    public void initialize() {
        // Initialization code goes here
    }

    @FXML
    public void switchToRoom() throws IOException {
        System.out.println("Switching to room");
        App.setRoot("room");
    }

    @FXML
    public void wrongAnswer() throws IOException {
        System.out.println("Wrong answer");
        showDialog("Info", "Alert", "Wrong Answer!");
    }

     @FXML
    public void correctAnswer() throws IOException {
        System.out.println("Correct answer");
        showDialog("Info", "Win", "You won the game!");
    }

    /**
   * Displays a dialog box with the given title, header text, and message.
   *
   * @param title the title of the dialog box
   * @param headerText the header text of the dialog box
   * @param message the message content of the dialog box
   */
  private void showDialog(String title, String headerText, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(headerText);
    alert.setContentText(message);
    alert.showAndWait();
  }
    
}
