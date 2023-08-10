package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RoomController1 {
    @FXML private ImageView perfume1;
  Image perfume1Image = new Image(getClass().getResourceAsStream("/images/P1.png"));
  Image perfume1ChangedImage = new Image(getClass().getResourceAsStream("/images/B5.png"));
  @FXML private ImageView perfume2;
  Image perfume2Image = new Image(getClass().getResourceAsStream("/images/P2.png"));
  Image perfume2ChangedImage = new Image(getClass().getResourceAsStream("/images/B3.png"));
  @FXML private ImageView perfume3;
  Image perfume3Image = new Image(getClass().getResourceAsStream("/images/P3.png"));
  Image perfume3ChangedImage = new Image(getClass().getResourceAsStream("/images/B2.png"));
  @FXML private ImageView perfume4;
  Image perfume4Image = new Image(getClass().getResourceAsStream("/images/P4.png"));
  Image perfume4ChangedImage = new Image(getClass().getResourceAsStream("/images/B4.png"));
  @FXML private ImageView perfume5;
  Image perfume5Image = new Image(getClass().getResourceAsStream("/images/P5.png"));
  Image perfume5ChangedImage = new Image(getClass().getResourceAsStream("/images/B1.png"));
  @FXML private ImageView perfume6;
  Image perfume6Image = new Image(getClass().getResourceAsStream("/images/P6.png"));
  Image perfume6ChangedImage = new Image(getClass().getResourceAsStream("/images/B6.png"));
  @FXML private ImageView perfume7;
  Image perfume7Image = new Image(getClass().getResourceAsStream("/images/P7.png"));
  Image perfume7ChangedImage = new Image(getClass().getResourceAsStream("/images/P7.png"));


  @FXML
  private void perfume1MouseEntered() {
    perfume1.setImage(perfume1Image);
  }
  @FXML
  private void perfume1MouseExited() {
    perfume1.setImage(perfume1ChangedImage);
  }
  @FXML
  private void perfume2MouseEntered() {
    perfume2.setImage(perfume2Image);
  }
  @FXML
  private void perfume2MouseExited() {
    perfume2.setImage(perfume2ChangedImage);
  }
  @FXML
  private void perfume3MouseEntered() {
    perfume3.setImage(perfume3Image);
  }
  @FXML
  private void perfume3MouseExited() {
    perfume3.setImage(perfume3ChangedImage);
  }
  @FXML
  private void perfume4MouseEntered() {
    perfume4.setImage(perfume4Image);
  }
  @FXML
  private void perfume4MouseExited() {
    perfume4.setImage(perfume4ChangedImage);
  }
  @FXML
  private void perfume5MouseEntered() {
    perfume5.setImage(perfume5Image);
  }
  @FXML
  private void perfume5MouseExited() {
    perfume5.setImage(perfume5ChangedImage);
  }
  @FXML
  private void perfume6MouseEntered() {
    perfume6.setImage(perfume6Image);
  }
  @FXML
  private void perfume6MouseExited() {
    perfume6.setImage(perfume6ChangedImage);
  }
  @FXML
  private void perfume7MouseEntered() {
    perfume7.setImage(perfume7Image);
  }
  @FXML
  private void perfume7MouseExited() {
    perfume7.setImage(perfume7ChangedImage);
  }
}
