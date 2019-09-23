package sample;

import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML
    JFXComboBox<String> comboBox;

    public void initialize(URL url, ResourceBundle rb){

        comboBox.getItems().add("BR");
        comboBox.getItems().add("LAS");
        comboBox.getItems().add("KR");
        comboBox.getItems().add("EU");
        comboBox.getItems().add("NA");
        comboBox.setStyle("-fx-font: 12px \"Josefin Sans Regular\"; -fx-text-fill: WHITE; -fx-prompt-text-fill: WHITE;");
    }


    public static Stage stageProfile;

    @FXML
    private void keyPressed(KeyEvent k) throws IOException{
         if(k.getCode() == KeyCode.ENTER){
             Stage s = new Stage();
             Parent root = FXMLLoader.load(getClass().getResource("Profile.fxml"));
             s.setScene(new Scene(root));
             s.initStyle(StageStyle.TRANSPARENT);
             stageProfile = s;
             s.show();
             LoadingController.stageMenu.close();
         }
    }

    @FXML
    private void handleButtonAction() {
        System.exit(0);
    }
}
