package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.Optional;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager.AppUi;

public class IntroController {

  @FXML private Button gameStart;

  @FXML
  public void initialize() {
    // runNameInputDialog();
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Welcome!");
    dialog.setHeaderText("Welcome to the SE206 perfume shop. Please enter your name:");
    Optional<String> result = dialog.showAndWait();

    result.ifPresent(
        name -> {
          GameState.playerName =
              name; // Store the user's name in GameState (you need to define the GameState class)
        });
  }

  private void runNameInputDialog() {
    Task<String> nameInputTask =
        new Task<>() {
          @Override
          protected String call() {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Welcome!");
            dialog.setHeaderText("Welcome to the SE206 perfume shop. Please enter your name:");
            Optional<String> result = dialog.showAndWait();
            return result.orElse("");
          }
        };

    nameInputTask.setOnSucceeded(
        event -> {
          String playerName = nameInputTask.getValue();
          GameState.playerName = playerName; // Store the user's name
        });

    Thread nameInputDialogThread = new Thread(nameInputTask);
    nameInputDialogThread.start();
  }

  @FXML
  public void gameStart() throws IOException {
    System.out.println("Switching to room");
    // App.setRoot("room");
    App.setScene(AppUi.ROOM);
  }
}
