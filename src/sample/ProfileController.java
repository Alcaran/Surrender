package sample;

import com.jfoenix.controls.JFXScrollPane;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    @FXML
    private void handleButtonAction() {
        System.exit(0);
    }

    @FXML
    private void minimizeButtonAction() {
        Stage stage  = (Stage) bp.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private BorderPane bp;

    @FXML
    private void analysesButtonAction() throws IOException {
        Stage s = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Analyses.fxml"));
        s.setScene(new Scene(root));
        s.initStyle(StageStyle.TRANSPARENT);

        s.show();
        Stage stage  = (Stage) bp.getScene().getWindow();
        stage.close();
    }

    public void initialize(URL url, ResourceBundle rb) {}

    @FXML
    private void backButtonAction() throws IOException{

        Stage s = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        s.setScene(new Scene(root));
        s.initStyle(StageStyle.TRANSPARENT);
        s.show();

        Stage stage  = (Stage) bp.getScene().getWindow();
        stage.close();
    }
}
