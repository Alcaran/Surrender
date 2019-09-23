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



import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    @FXML
    private void handleButtonAction() {
        System.exit(0);
    }

    public static Stage stageProfile;

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private BorderPane borderPane;

    public void initialize(URL url, ResourceBundle rb) {



    }
}
