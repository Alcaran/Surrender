package controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class RegisterController {

    @FXML
    private BorderPane bp;

    @FXML
    private void handleButtonAction() {
        System.exit(0);
    }

    @FXML
    private void minimizeButtonAction() {
        Stage stage = (Stage) bp.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void backButtonAction() throws IOException {

        Stage s = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../screens/Login.fxml"));
        s.setScene(new Scene(root));
        s.initStyle(StageStyle.TRANSPARENT);
        s.show();

        Stage stage = (Stage) bp.getScene().getWindow();
        stage.close();
    }
}
