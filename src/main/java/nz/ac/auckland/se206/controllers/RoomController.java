package nz.ac.auckland.se206.controllers;

import java.io.IOException;

import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.speech.TextToSpeech;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

/** Controller class for the room view. */
public class RoomController {

  @FXML private Rectangle door;
  @FXML private Rectangle perfume;

  @FXML private Rectangle doorLock;
  @FXML private ImageView letter;
  @FXML private Label timerLabel;
  @FXML private Label label;
  @FXML private ProgressBar progressBar;

  private int counter = 0;
  private int seconds = 0;
  private Timeline timeline;
  private double progressSize = 0.0;

  /** Initializes the room view, it is called when the room loads. */
  public void initialize() {
    timeline = new Timeline(new KeyFrame(Duration.seconds(1), this::updateTimer));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
    // timeline = new Timeline(
    //     new KeyFrame(Duration.seconds(2), event -> {
    //         label.setVisible(false);
    //         label.setText(""); // Optional: Clear the label's text
    //     })
    // );
    // timeline.setCycleCount(1);
    // timeline.setOnFinished(event -> {
    //     label.setVisible(true);
    // });
  }

  private void updateTimer(ActionEvent event) {
    seconds++;
    timerLabel.setText("Time: " + seconds + " seconds");
    // if (seconds == 20) {
    //   timeline.stop();
    //   showDialog("Info", "Time Out", "You ran out of time!");
    // } else if (seconds == 10) {
    //   showLabel("Hello, world!");
    // }
  }

  // public void showLabel(String message) {
  //   label.setText(message);
  //   label.setVisible(true);
  //   timeline.playFromStart();
  // }

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
    alert.showAndWait();
  }

  /**
   * Handles the click event on the door.
   *
   * @param event the mouse event
   * @throws IOException if there is an error loading the chat view
   */
  @FXML
  public void clickDoor(MouseEvent event) throws IOException {
    System.out.println("door clicked");

    if (!GameState.isRiddleResolved) {
      showDialog("Info", "Riddle", "You need to resolve the riddle!");
      App.setRoot("chat");
      return;
    }

    if (!GameState.isKeyFound) {
      showDialog(
          "Info", "Find the key!", "You resolved the riddle, now you know where the key is.");
    } else {
      showDialog("Info", "You Won!", "Good Job!");
    }
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
    if(counter == 5){
      counter = 5;
      toggleVisibility1();
      GameState.isPerfumeColledted = true;
      letter.setVisible(true);
      showDialog("Info", "Perfume Collected", "You collected all of the Perfume!");
    }

    // if (GameState.isRiddleResolved && !GameState.isKeyFound) {
    //   showDialog("Info", "Key Found", "You found a key under the vase!");
    //   GameState.isKeyFound = true;
    // }
  }

  private void perfumeClicked(){
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
    if(!GameState.isRiddleResolved || !GameState.isPerfumeColledted){
      showDialog("Info", "Door Locked", "You need to collect all of the Perfume!");
    }
    else{
      System.out.println("door is clicked");
      //App.setRoot("lock");
      App.setScene(AppUi.LOCK);
    }
  }

  @FXML
  public void clickletter(MouseEvent event) throws IOException {
    // TextToSpeech textToSpeech = new TextToSpeech();
    // textToSpeech.speak("Help me escape!");
    System.out.println("letter is clicked");
    //App.setRoot("chat");
    App.setScene(AppUi.CHAT);
  }
}
