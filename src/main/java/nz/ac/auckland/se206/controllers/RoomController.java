package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.Optional;
import java.util.Random;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager.AppUi;
import nz.ac.auckland.se206.speech.TextToSpeech;

/** Controller class for the room view. */
public class RoomController {

  @FXML private Rectangle door;
  @FXML private Rectangle doorLock;
  @FXML private ImageView letterclicked;
  @FXML private Label timerLabel;
  @FXML private Label label;
  @FXML private ProgressBar progressBar;
  @FXML private Label playerNameLabel;
  @FXML private Button hintButton;

  @FXML private ImageView perfume1;
  private Image perfume1Image = new Image(getClass().getResourceAsStream("/images/P1.png"));
  private Image perfume1ChangedImage = new Image(getClass().getResourceAsStream("/images/B5.png"));
  @FXML private ImageView perfume3;
  private Image perfume3Image = new Image(getClass().getResourceAsStream("/images/P3.png"));
  private Image perfume3ChangedImage = new Image(getClass().getResourceAsStream("/images/B2.png"));
  @FXML private ImageView perfume4;
  private Image perfume4Image = new Image(getClass().getResourceAsStream("/images/P4.png"));
  private Image perfume4ChangedImage = new Image(getClass().getResourceAsStream("/images/B7.png"));
  @FXML private ImageView perfume5;
  private Image perfume5Image = new Image(getClass().getResourceAsStream("/images/P5.png"));
  private Image perfume5ChangedImage = new Image(getClass().getResourceAsStream("/images/B1.png"));
  @FXML private ImageView perfume6;
  private Image perfume6Image = new Image(getClass().getResourceAsStream("/images/P6.png"));
  private Image perfume6ChangedImage = new Image(getClass().getResourceAsStream("/images/B6.png"));
  @FXML private ImageView perfume7;
  private Image perfume7Image = new Image(getClass().getResourceAsStream("/images/P2.png"));
  private Image perfume7ChangedImage = new Image(getClass().getResourceAsStream("/images/B4.png"));

  private int counter = 0;
  private int seconds = 100;
  private Timeline timeline;
  private double progressSize = 0.0;
  private boolean firstClickOccurred = false;
  private boolean firstClickOccurred1 = false;
  private Random random = new Random();
  private Timeline perfumeTimeline;

  /** Initializes the room view, it is called when the room loads. */
  public void initialize() {
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Welcome!");
    dialog.setHeaderText("Welcome to the SE206 perfume shop. Please enter your name:");
    Optional<String> result = dialog.showAndWait();
    String playerName = result.orElse("");
    GameState.playerName = playerName; // Store the user's name

    setPlayerNameLabel();

    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Welcome!");
    alert.setHeaderText("Welcome to SE206 Perfume shop Escape room!");
    String alertContentText =
        "You are locked in the storage room of a perfume shop and needed to escape as soon"
            + " as possible.\n"
            + "You only have 100 seconds to escape the room. Best of your luck and find me anytime"
            + "when you need help.";

    alert.getDialogPane().setContentText(alertContentText);
    alert.getDialogPane().setMinHeight(300);
    alert.getDialogPane().setMinWidth(400);

    alert.showAndWait();

    timeline = new Timeline(new KeyFrame(Duration.seconds(1), this::updateTimer));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();

    perfumeTimeline = new Timeline(new KeyFrame(Duration.seconds(1.5), this::toggleRandomPerfume));
    perfumeTimeline.setCycleCount(Timeline.INDEFINITE);
    perfumeTimeline.play();
    initializeImage();

    Platform.runLater(
        () -> {
          Stage stage = (Stage) timerLabel.getScene().getWindow();

          stage.setOnCloseRequest(
              event -> {
                stopBackgroundTasks();
                Platform.exit();
                System.exit(0);
              });
        });
  }

  private void stopBackgroundTasks() {
    if (timeline != null) {
      timeline.stop();
    }
  }

  private void initializeImage() {
    perfume1.setImage(perfume1Image);
    perfume1.setVisible(false);
    perfume3.setImage(perfume3Image);
    perfume3.setVisible(false);
    perfume4.setImage(perfume4Image);
    perfume4.setVisible(false);
    perfume5.setImage(perfume5Image);
    perfume5.setVisible(false);
    perfume6.setImage(perfume6Image);
    perfume6.setVisible(false);
    perfume7.setImage(perfume7Image);
    perfume7.setVisible(false);
  }

  private void setPlayerNameLabel() {
    Task<Void> playerNameTask =
        new Task<>() {
          @Override
          protected Void call() {
            String playerName = GameState.playerName;
            Platform.runLater(() -> playerNameLabel.setText("Welcome, " + playerName + " !"));
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
  public void clickPerfume1(MouseEvent event) {
    if (event.getTarget() instanceof ImageView && ((ImageView) event.getTarget()).isVisible()) {
      perfumeClicked();
      if (counter == 8) {
        counter = 8;
        GameState.isPerfumeColledted = true;
        letterclicked.setVisible(true);
        showDialog("Info", "Perfume Collected", "You collected all of the Perfume!");
      }
    }
  }

  @FXML
  public void clickPerfume(MouseEvent event) {
    ImageView clickedPerfume = (ImageView) event.getTarget();

    perfumeClicked();
    clickedPerfume.setVisible(false);

    if (counter == 8) {
      perfumeTimeline.stop();
      GameState.isPerfumeColledted = true;
      letterclicked.setVisible(true);
      showDialog("Info", "Perfume Collected", "You collected all of the Perfume!");
    }
  }

  private void toggleRandomPerfume(ActionEvent event) {
    int randomPerfumeIndex = random.nextInt(6) + 1; // Generate a random perfume index (1 to 6)
    ImageView randomPerfume = getPerfumeImageView(randomPerfumeIndex);

    if (randomPerfume.isVisible()) {
      randomPerfume.setVisible(false);
    } else {
      randomPerfume.setVisible(true);
      randomPerfume.isDisable();
      Timeline hidePerfumeTimeline =
          new Timeline(new KeyFrame(Duration.seconds(1.0), e -> randomPerfume.setVisible(false)));
      hidePerfumeTimeline.setCycleCount(1);
      hidePerfumeTimeline.play();
    }
  }

  private ImageView getPerfumeImageView(int index) {
    switch (index) {
      case 1:
        return perfume1;
      case 2:
        return perfume3;
      case 3:
        return perfume4;
      case 4:
        return perfume5;
      case 5:
        return perfume6;
      case 6:
        return perfume7;
      default:
        return null;
    }
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
    progressBar.setProgress(progressSize += 0.125);
    System.out.println("counter: " + counter);
  }

  // @FXML
  // private void perfume1MouseEntered() {
  //   perfume1.setImage(perfume1Image);
  // }

  // @FXML
  // private void perfume1MouseExited() {
  //   perfume1.setImage(perfume1ChangedImage);
  // }

  // @FXML
  // private void perfume3MouseEntered() {
  //   perfume3.setImage(perfume3Image);
  // }

  // @FXML
  // private void perfume3MouseExited() {
  //   perfume3.setImage(perfume3ChangedImage);
  // }

  // @FXML
  // private void perfume4MouseEntered() {
  //   perfume4.setImage(perfume4Image);
  // }

  // @FXML
  // private void perfume4MouseExited() {
  //   perfume4.setImage(perfume4ChangedImage);
  // }

  // @FXML
  // private void perfume5MouseEntered() {
  //   perfume5.setImage(perfume5Image);
  // }

  // @FXML
  // private void perfume5MouseExited() {
  //   perfume5.setImage(perfume5ChangedImage);
  // }

  // @FXML
  // private void perfume6MouseEntered() {
  //   perfume6.setImage(perfume6Image);
  // }

  // @FXML
  // private void perfume6MouseExited() {
  //   perfume6.setImage(perfume6ChangedImage);
  // }

  // @FXML
  // private void perfume7MouseEntered() {
  //   perfume7.setImage(perfume7Image);
  // }

  // @FXML
  // private void perfume7MouseExited() {
  //   perfume7.setImage(perfume7ChangedImage);
  // }

  /**
   * Handles the click event on the window.
   *
   * @param event the mouse event
   * @throws IOException
   */
  @FXML
  public void clickdoor(MouseEvent event) throws IOException {
    if (!GameState.isRiddleResolved && !GameState.isPerfumeColledted) {
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

            String firstSentence =
                "Hey" + GameState.playerName + "You have to solve the riddle to unlock the door.";

            // Text-to-speech the first sentence only if it hasn't been spoken before
            if (!firstClickOccurred) {
              TextToSpeech textToSpeech = new TextToSpeech();
              textToSpeech.speak(firstSentence);
              firstClickOccurred = true;
            }
            return null;
          }
        };

    // Execute the task in a new thread
    Thread thread = new Thread(task);
    thread.start();
  }

  @FXML
  private void handleHintButtonClick(ActionEvent event) throws IOException {
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
            if (!firstClickOccurred1) {
              TextToSpeech textToSpeech = new TextToSpeech();
              textToSpeech.speak(firstSentence);
              firstClickOccurred1 = true;
            }

            return null;
          }
        };

    // Execute the task in a new thread
    Thread thread = new Thread(task);
    thread.start();
  }
}
