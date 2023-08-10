package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.Optional;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.gpt.GptPromptEngineering;
import nz.ac.auckland.se206.speech.TextToSpeech;

/** Controller class for the room view. */
public class RoomController {

  @FXML private Rectangle door;
  @FXML private Rectangle doorLock;
  @FXML private ImageView letter;
  @FXML private Label timerLabel;
  @FXML private Label label;
  @FXML private ProgressBar progressBar;
  @FXML private Label playerNameLabel;
  @FXML private Button hintButton;

  @FXML private ImageView perfume1;
  @FXML private ImageView perfume2;
  @FXML private ImageView perfume3;
  @FXML private ImageView perfume4;
  @FXML private ImageView perfume5;
  @FXML private ImageView perfume6;
  @FXML private ImageView perfume7;

  /*
  @FXML private ImageView perfumeImage = new ImageView(perfume7);
  @FXML private ImageView perfumeImage1 = new ImageView(perfume6);
  @FXML private ImageView perfumeImage2 = new ImageView(perfume5);
  @FXML private ImageView perfumeImage3 = new ImageView(perfume4);
  @FXML private ImageView perfumeImage4 = new ImageView(perfume3);
  @FXML private ImageView perfumeImage5 = new ImageView(perfume2);

  */

  // private Image perfume1 = new Image("images/perfume1.png");
  // @FXML private ImageView perfumeImage6 = new ImageView(perfume1);

  private int counter = 0;
  private int seconds = 100;
  private Timeline timeline;
  private double progressSize = 0.0;
  private boolean firstClickOccurred = false;
  private boolean firstClickOccurred1 = false;

  /** Initializes the room view, it is called when the room loads. */
  public void initialize() {

    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Welcome!");
    alert.setHeaderText("SE206 Perfume Shop");
    alert.setContentText(
        "When I opened my eyes, I was locked in the storage room and needed to escape as soon"
            + "\n as possible, and I don't have enough time !!!");
    alert.showAndWait();

    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Welcome!");
    dialog.setHeaderText("Welcome to the SE206 perfume shop. Please enter your name:");
    Optional<String> result = dialog.showAndWait();
    String playerName = result.orElse("");
    GameState.playerName = playerName; // Store the user's name

    setPlayerNameLabel();
    timeline = new Timeline(new KeyFrame(Duration.seconds(1), this::updateTimer));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();

    // Set mouse hover event handlers for perfumes
    // perfume1.setOnMouseEntered(this::perfumeMouseEntered);
    // perfume1.setOnMouseExited(this::perfumeMouseExited);
    // perfume2.setOnMouseEntered(this::perfumeMouseEntered);
    // perfume2.setOnMouseExited(this::perfumeMouseExited);
    // perfume3.setOnMouseEntered(this::perfumeMouseEntered);
    // perfume3.setOnMouseExited(this::perfumeMouseExited);
    // perfume4.setOnMouseEntered(this::perfumeMouseEntered);
    // perfume4.setOnMouseExited(this::perfumeMouseExited);
    // perfume5.setOnMouseEntered(this::perfumeMouseEntered);
    // perfume5.setOnMouseExited(this::perfumeMouseExited);
    // perfume6.setOnMouseEntered(this::perfumeMouseEntered);
    // perfume6.setOnMouseExited(this::perfumeMouseExited);
    // perfume7.setOnMouseEntered(this::perfumeMouseEntered);
    // perfume7.setOnMouseExited(this::perfumeMouseExited);
  }

  private void setPlayerNameLabel() {
    Task<Void> playerNameTask =
        new Task<>() {
          @Override
          protected Void call() {
            String playerName = GameState.playerName;
            Platform.runLater(() -> playerNameLabel.setText("Welcome, " + playerName));
            return null;
          }
        };

    Thread playerNameThread = new Thread(playerNameTask);
    playerNameThread.start();
  }

  private void updateTimer(ActionEvent event) {
    if (!GameState.isGameFinished) {
      seconds--;
      timerLabel.setText("Time: " + seconds + " seconds");
      if (seconds == 0) {
        timeline.stop();
        showDialog("Info", "Time Out", "You ran out of time!");
      } else if (seconds == 30) {
        showDialog("Info", "30 seconds Left", "You only got 30 seconds to escape the room!");
      }
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
    // toggleVisibility();
    if (counter == 5) {
      counter = 5;
      // toggleVisibility1();
      // perfume1.setOpacity(0);
      GameState.isPerfumeColledted = true;
      letter.setVisible(true);
      showDialog("Info", "Perfume Collected", "You collected all of the Perfume!");
      perfume1.setVisible(false);
      // Make the perfumeImage ImageView disappear
    }

    // if (GameState.isRiddleResolved && !GameState.isKeyFound) {
    //   showDialog("Info", "Key Found", "You found a key under the vase!");
    //   GameState.isKeyFound = true;
    // }
  }

  /**
   * Handles the click event on the perfume.
   *
   * @param event the mouse event
   */
  @FXML
  private void perfumeClicked() {
    System.out.println("Perfume clicked");
    counter++;
    progressBar.setProgress(progressSize += 0.2);
    System.out.println("counter: " + counter);
  }

  @FXML
  private void perfumeMouseEntered(MouseEvent event) {
    // ImageView perfume = (ImageView) event.getSource();
    perfume1.setVisible(true);
  }

  @FXML
  private void perfumeMouseExited(MouseEvent event) {
    // ImageView perfume = (ImageView) event.getSource();
    perfume1.setVisible(false);
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
    } else if (!GameState.isRiddleResolved) {
      showDialog("Info", "Door Locked", "You need to solve the riddle!");
    } else {
      System.out.println("door is clicked");
      GameState.isDoorUnlocked = true;
      App.setScene(AppUi.LOCK);
    }
  }

  @FXML
  public void clickletter(MouseEvent event) throws IOException {
    GameState.isLetterFound = true;
    Task<Void> task =
        new Task<>() {
          @Override
          protected Void call() throws Exception {
            // Change to the chat scene
            Platform.runLater(
                () -> {
                  App.setScene(AppUi.CHAT);
                });

            // Run GPT and get the response
            String gptResponse =
                GptPromptEngineering.getRiddleWithGivenWord(
                    "candle"); // Call a method to run GPT and get the response

            // Find the first sentence in the response
            int periodIndex = gptResponse.indexOf('.');
            if (periodIndex != -1) {
              String firstSentence = gptResponse.substring(0, periodIndex + 1);

              // Text-to-speech the first sentence only if it hasn't been spoken before
              if (!firstClickOccurred) {
                TextToSpeech textToSpeech = new TextToSpeech();
                textToSpeech.speak(firstSentence);
                firstClickOccurred = true;
              }
            }
            return null;
          }
        };

    // Execute the task in a new thread
    Thread thread = new Thread(task);
    thread.start();
  }

  @FXML
  public void clickHintButton(ActionEvent event) throws IOException {
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

            String gptResponse =
                GptPromptEngineering
                    .getHintWithGivenWord(); // Call a method to run GPT and get the response

            // Find the first sentence in the response
            int periodIndex = gptResponse.indexOf('.');
            if (periodIndex != -1) {
              String firstSentence = gptResponse.substring(0, periodIndex + 1);

              // Text-to-speech the first sentence only if it hasn't been spoken before
              if (!firstClickOccurred1) {
                TextToSpeech textToSpeech = new TextToSpeech();
                textToSpeech.speak(firstSentence);
                firstClickOccurred1 = true;
              }
            }

            return null;
          }
        };

    // Execute the task in a new thread
    Thread thread = new Thread(task);
    thread.start();
  }
}
