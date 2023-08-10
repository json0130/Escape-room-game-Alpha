package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.control.TextInputDialog;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.SceneManager.AppUi;

public class IntroController {

  @FXML
  public void initialize() {
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Welcome!");
    dialog.setHeaderText("Welcome to the SE206 perfume shop. Please enter your name:");
    Optional<String> result = dialog.showAndWait();
    String playerName = result.orElse("");
    GameState.playerName = playerName; // Store the user's name
  }

  @FXML
  public void gameStart() throws IOException {
    System.out.println("Switching to room");
    // App.setRoot("room");
    GameState.isGameStarted = true;
    App.setScene(AppUi.ROOM);
  }
}
