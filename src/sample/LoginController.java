package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class LoginController {

    @FXML
    private void handleButtonAction() {
        System.exit(0);
    }

    public static Stage stageLogin;

    @FXML
    private void closeButtonAction() throws IOException {
        Stage s = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Loading.fxml"));
        s.setScene(new Scene(root));
        s.initStyle(StageStyle.TRANSPARENT);
        stageLogin = s;
        s.show();
        Main.mainLoginScene.close();
    }
}


