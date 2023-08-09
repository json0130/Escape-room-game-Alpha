package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.speech.TextToSpeech;

/** Controller class for the room view. */
public class RoomController {

  @FXML private Rectangle door;
  @FXML private Rectangle perfume;
  @FXML private Rectangle doorLock;
  @FXML private ImageView letter;
  @FXML private Label timerLabel;
  @FXML private Label label;
  @FXML private ProgressBar progressBar;
  @FXML private Label playerNameLabel;
  @FXML private Button hintButton;

  private int counter = 0;
  private int seconds = 100;
  private Timeline timeline;
  private double progressSize = 0.0;
  private boolean firstClickOccurred = false;

  /** Initializes the room view, it is called when the room loads. */
  public void initialize() {

    setPlayerNameLabel();
    // playerNameLabel.setText("Welcome, " + GameState.playerName + "!");
    if (GameState.isGameStarted) {
      timeline = new Timeline(new KeyFrame(Duration.seconds(1), this::updateTimer));
      timeline.setCycleCount(Timeline.INDEFINITE);
      timeline.play();
    }
  }

  private void setPlayerNameLabel() {
    Task<Void> playerNameTask =
        new Task<>() {
          @Override
          protected Void call() {
            String playerName = GameState.playerName;
            Platform.runLater(() -> playerNameLabel.setText("Welcome, " + playerName + "!"));
            return null;
          }
        };

    Thread playerNameThread = new Thread(playerNameTask);
    playerNameThread.start();
  }

  private void updateTimer(ActionEvent event) {
    seconds--;
    timerLabel.setText("Time: " + seconds + " seconds");
    if (seconds == 0) {
      timeline.stop();
      showDialog("Info", "Time Out", "You ran out of time!");
    } else if (seconds == 30) {
      showDialog("Info", "30 seconds Left", "You only got 30 seconds to escape the room!");
    }
  }

  /**
   * Handles the key pressed event.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyPressed(KeyEvent event) {
    System.out.println("key " + event.getCode() + " pressed");
  }

  /**
   * Handles the key released event.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyReleased(KeyEvent event) {
    System.out.println("key " + event.getCode() + " released");
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
    alert.show();
  }

  /**
   * Handles the click event on the perfume.
   *
   * @param event the mouse event
   */
  @FXML
  public void clickPerfume(MouseEvent event) {
    perfumeClicked();
    toggleVisibility();
    if (counter == 5) {
      counter = 5;
      toggleVisibility1();
      GameState.isPerfumeColledted = true;
      GameState.isLetterFound = true;
      letter.setVisible(true);
      showDialog("Info", "Perfume Collected", "You collected all of the Perfume!");
    }

    // if (GameState.isRiddleResolved && !GameState.isKeyFound) {
    //   showDialog("Info", "Key Found", "You found a key under the vase!");
    //   GameState.isKeyFound = true;
    // }
  }

  private void perfumeClicked() {
    System.out.println("Perfume clicked");
    counter++;
    progressBar.setProgress(progressSize += 0.2);
    System.out.println("counter: " + counter);
  }

  private void toggleVisibility() {
    perfume.setVisible(!perfume.isVisible());
  }

  private void toggleVisibility1() {
    progressBar.setVisible(!progressBar.isVisible());
  }

  /**
   * Handles the click event on the window.
   *
   * @param event the mouse event
   * @throws IOException
   */
  @FXML
  public void clickdoor(MouseEvent event) throws IOException {
    if (!GameState.isRiddleResolved || !GameState.isPerfumeColledted) {
      showDialog("Info", "Door Locked", "You need to collect all of the Perfume!");
    } else {
      System.out.println("door is clicked");
      App.setScene(AppUi.LOCK);
    }
  }

  @FXML
  public void clickletter(MouseEvent event) throws IOException {
    Task<Void> task =
        new Task<>() {
          @Override
          protected Void call() throws Exception {
            // Change to the chat scene
            Platform.runLater(
                () -> {
                  App.setScene(AppUi.CHAT);
                });

            // Run GPT and text-to-speech concurrently in the chat scene
            // This code will execute after changing to the chat scene
            // Assuming you have a TextToSpeech class or utility
            TextToSpeech textToSpeech = new TextToSpeech();

            // Assuming you have a response from GPT
            String gptResponse = "The first sentence of the response from GPT.";

            // Find the first sentence in the response
            int periodIndex = gptResponse.indexOf('.');
            if (periodIndex != -1) {
              String firstSentence = gptResponse.substring(0, periodIndex + 1);

              // Check if this is the first click
              if (!firstClickOccurred) {
                textToSpeech.speak(firstSentence);
                firstClickOccurred = true; // Set the flag to true after the first click
              }
            }
            return null;
          }
        };

    // Execute the task in a new thread
    Thread thread = new Thread(task);
    thread.start();
  }

  public void clickHintButton(ActionEvent event) throws IOException {
    GameState.isHintClicked = true;
    Task<Void> task =
        new Task<>() {
          @Override
          protected Void call() throws Exception {
            // Change to the chat scene
            Platform.runLater(
                () -> {
                  App.setScene(AppUi.HINT);
                });

            // Run GPT and text-to-speech concurrently in the chat scene
            // This code will execute after changing to the chat scene
            // Assuming you have a TextToSpeech class or utility
            TextToSpeech textToSpeech = new TextToSpeech();

            // Assuming you have a response from GPT
            String gptResponse = "The first sentence of the response from GPT.";

            // Find the first sentence in the response
            // int periodIndex = gptResponse.indexOf('.');
            // if (periodIndex != -1) {
            //   String firstSentence = gptResponse.substring(0, periodIndex + 1);
            //   textToSpeech.speak(firstSentence);
            //   // Check if this is the first click

            // }
            return null;
          }
        };

    // Execute the task in a new thread
    Thread thread = new Thread(task);
    thread.start();
  }
}
