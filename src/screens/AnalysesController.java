package screens;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class AnalysesController {

    @FXML
    private void handleButtonAction() {
        System.exit(0);
    }

    @FXML
    private BorderPane bp;

    @FXML
    private void backButtonAction() throws IOException {
        Stage stage  = (Stage) bp.getScene().getWindow();
        stage.close();
    }
}
