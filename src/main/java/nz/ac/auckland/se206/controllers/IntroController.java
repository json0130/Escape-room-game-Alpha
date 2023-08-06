package nz.ac.auckland.se206.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.SceneManager.AppUi;

public class IntroController {

    @FXML
    private Button gameStart;

    @FXML
    public void initialize() {
        // Initialization code goes here
        // TextToSpeech textToSpeech = new TextToSpeech();
        // textToSpeech.speak("Worker: Hello welcome to SE206 perfume shop. Please look around and let me know if you need any help. 
        //Jay: Hmmm, I am looking for the perfume for myself. Could you please recommend the scent to me? 
        //Worker: No worries, this perfume was just recently released. Would you like to try it?
        //Jay: Sure!!   ….. What is this smell…. 
        //When I opened my eyes, I was locked in the storage room and needed to escape as soon as possible, and I don't have enough time !!! 
    }

    @FXML
    public void gameStart() throws IOException {
        System.out.println("Switching to room");
        //App.setRoot("room");
        App.setScene(AppUi.ROOM);
    }
}
