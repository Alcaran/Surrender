package controllers;

import business.Match;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AnalysesController {
    @FXML
    private BorderPane bp;

    @FXML
    private void handleButtonAction() {
        System.exit(0);
    }

    @FXML
    private void backButtonAction() throws IOException {
        Stage stage  = (Stage) bp.getScene().getWindow();
        stage.close();
    }

    void initData(Match playerMatch, Stage analysesStage, Stage profileStage){




    }



}
