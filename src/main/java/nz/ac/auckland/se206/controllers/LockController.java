package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.speech.TextToSpeech;

public class LockController {

  @FXML private Button exitbutton;
  @FXML private Label checkAnswerLabel;

  private int checkAnswer = 3;

  @FXML
  public void initialize() {
    // Initialization code goes here
    checkAnswerLabel.setText("Answer Check: " + checkAnswer + " seconds");
  }

  @FXML
  protected void onClickSwitchToRoom() throws IOException {
    if (!GameState.isGameFinished) {
      System.out.println("Switching to room");
      App.setScene(AppUi.ROOM);
    }
  }

  @FXML
  private void wrongAnswer() throws IOException {
    if (!GameState.isGameFinished) {
      checkAnswer--;
      checkAnswerLabel.setText("Check Answer: " + checkAnswer + " attempts left");
      if (checkAnswer == 0) {
        GameState.isGameFinished = true;
        showDialog("Info", "Lose", "You used all of your chance to guess the answer!");
      } else if (checkAnswer == 1) {
        showDialog("Info", "Alert", "Wrong Answer! This is your last chance to guess the answer!");
      } else {
        System.out.println("Wrong answer");
        showDialog("Info", "Alert", "Wrong Answer!");
      }
    }
  }

  @FXML
  private void correctAnswer() throws IOException {
    finishTextToSpeech();
    System.out.println("Correct answer");
    showDialog("Info", "Congraturation!", "You have escaped the room!");
    GameState.isGameFinished = true;
  }

  private void finishTextToSpeech() {
    Task<Void> introTask =
        new Task<>() {

          @Override
          protected Void call() throws Exception {
            TextToSpeech textToSpeech = new TextToSpeech();
            textToSpeech.speak("Congraturation! You have escaped the room!");
            return null;
          }
        };
    Thread introThread = new Thread(introTask);
    introThread.start();
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

  @FXML
  private void onClickHintButton(ActionEvent event) throws IOException {
    Task<Void> task =
        new Task<>() {
          @Override
          protected Void call() throws Exception {
            // Change to the chat scene
            Platform.runLater(
                () -> {
                  App.setScene(AppUi.HINT);
                });

            // Run GPT and get the response
            String firstSentence = "How can I help you?, Please let me know if you need and help.";
            // Text-to-speech the first sentence only if it hasn't been spoken before
            if (!GameState.firstClickOccurred1) {
              TextToSpeech textToSpeech = new TextToSpeech();
              textToSpeech.speak(firstSentence);
              GameState.firstClickOccurred1 = true;
            }
            return null;
          }
        };

    // Execute the task in a new thread
    Thread thread = new Thread(task);
    thread.start();
  }
}
