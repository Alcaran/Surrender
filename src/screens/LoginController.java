package screens;

import business.Player;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.GraphicUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoginController {
    @FXML
    private BorderPane bp;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;
    @FXML
    private void handleButtonAction() {
        System.exit(0);
    }
    @FXML
    private void closeButtonAction() throws IOException {
        this.goToLoadingScreen(null);
    }

    @FXML
    private void loginButtonAction() {
        try {
            Player player = new Player(username.getText(), password.getText());
            ArrayList<String> linked_accounts = player.getLinkedAccounts();
            if(linked_accounts.size() > 0)
                this.goToLoadingScreen(null);
            else
                GraphicUtils.callSnackbar("Credentials not found", bp);
        } catch (Exception e ) {
            System.out.println(e);
        }
    }

    @FXML
    private void minimizeButtonAction() {
        Stage stage  = (Stage) bp.getScene().getWindow();
        stage.setIconified(true);
    }

    private void goToLoadingScreen(ArrayList<String> linkedAccounts) throws IOException {
        Stage s = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Loading.fxml"));
        s.setScene(new Scene(root));
        s.initStyle(StageStyle.TRANSPARENT);
        s.show();

        Stage stage  = (Stage) bp.getScene().getWindow();
        stage.close();
    }
}


