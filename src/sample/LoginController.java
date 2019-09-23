package sample;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class LoginController {

    @FXML
    private void handleButtonAction() {
        System.exit(0);
    }
    @FXML
    private BorderPane bp;
    @FXML
    private void closeButtonAction() throws IOException {

        Stage s = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Loading.fxml"));
        s.setScene(new Scene(root));
        s.initStyle(StageStyle.TRANSPARENT);
        s.show();

        Stage stage  = (Stage) bp.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void minimizeButtonAction() throws IOException {
        Stage stage  = (Stage) bp.getScene().getWindow();
        stage.setIconified(true);
    }
}


