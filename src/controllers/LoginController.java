package controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import data.database.UserDao;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.GraphicUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LoginController {
    private UserDao userAccessor;
    private Executor exec;

    @FXML
    private BorderPane bp;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXSpinner spinner;

    @FXML
    private void initialize() {
        userAccessor = new UserDao();

        exec = Executors.newCachedThreadPool(runnable -> {
            Thread t = new Thread(runnable);
            t.setDaemon(true);
            return t;
        });
    }

    @FXML
    private void handleButtonAction() {
        System.exit(0);
    }

    @FXML
    private void closeButtonAction() throws IOException {
        this.goToLoadingScreen(new ArrayList<>());
    }

    @FXML
    private void registerButtonAction() throws IOException {
        Stage s = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/screens/Register.fxml"));
        s.setScene(new Scene(root));
        s.initStyle(StageStyle.TRANSPARENT);
        s.show();

        Stage stage = (Stage) bp.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void loginButtonAction() {
            Task<ArrayList<String>> linkedAccountsTask = new Task<ArrayList<String>>() {
                @Override
                protected ArrayList<String> call() throws Exception {
                    spinner.setVisible(true);
                    return userAccessor.getUser(
                            username.getText(),
                            password.getText()
                    );
                }
            };

            linkedAccountsTask.setOnFailed(e -> {
                linkedAccountsTask.getException().printStackTrace();
            });

            linkedAccountsTask.setOnSucceeded(e -> {
                if (linkedAccountsTask.getValue().size() > 0) {
                    try {
                        this.goToLoadingScreen(linkedAccountsTask.getValue());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                else
                    GraphicUtils.callSnackbar("Credentials not found", bp, "red");
                spinner.setVisible(false);
            });

            exec.execute(linkedAccountsTask);
    }

    @FXML
    private void minimizeButtonAction() {
        Stage stage = (Stage) bp.getScene().getWindow();
        stage.setIconified(true);
    }

    private void goToLoadingScreen(ArrayList<String> linkedAccounts) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/screens/Loading.fxml")
        );

        // Set profile page to load
        Stage loadingStage = new Stage(StageStyle.TRANSPARENT);
        loadingStage.setScene(new Scene(loader.load()));

        LoadingController controller = loader.getController();
        controller.initData((linkedAccounts));

        Stage stage = (Stage) bp.getScene().getWindow();
        stage.close();
        loadingStage.show();


    }

    public void callSuccessOnRegisteredMessage() {
        GraphicUtils.callSnackbar("User successfully registered", bp, "green");
    }
}


