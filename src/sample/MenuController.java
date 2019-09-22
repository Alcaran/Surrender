package sample;

import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    @FXML
    private void handleButtonAction() {
        System.exit(0);
    }
    @FXML
    public Stage stageMenu;
    @FXML
    private void closeButtonAction() throws IOException {
        Stage s = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Loading.fxml"));
        s.setScene(new Scene(root));
        s.initStyle(StageStyle.TRANSPARENT);
        stageMenu = s;
        s.show();
        Main.mainLoginScene.close();
    }
}
