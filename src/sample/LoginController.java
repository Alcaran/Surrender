package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.event.ActionEvent;

public class LoginController {

    @FXML
    private void handleButtonAction(){
        System.exit(0);
    }

    @FXML
    public Button btnNoLogin;

    @FXML
    private void closeButtonAction()  throws Exception{

        Stage stage = (Stage) btnNoLogin.getScene().getWindow();
        stage.close();

        Stage s = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Loading.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("font.css").toExternalForm());
        s.setScene(scene);
        s.initStyle(StageStyle.TRANSPARENT);
        s.show();
    }
}
