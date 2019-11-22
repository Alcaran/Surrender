package controllers;


import business.Player;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import data.database.UserDao;
import data.enums.Servers;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.GraphicUtils;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class RegisterController implements Initializable {

    @FXML
    JFXTextField txtUsername;
    @FXML
    JFXTextField txtAccount;
    @FXML
    JFXPasswordField txtPassword;
    @FXML
    JFXPasswordField txtPassword2;

    private Executor exec;


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
    private void submitButtonAction() {

        Task<Boolean> nicknameTask = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                if(txtUsername.getText() == null) {
                    throw new Exception("Username can't be empty");
                }

                return new UserDao().finUserByUsername(txtUsername.getText());
            }
        };

        nicknameTask.setOnFailed(n -> {
            GraphicUtils.callSnackbar("Username already used", bp);
        });


        nicknameTask.setOnSucceeded(n -> {

            if(!nicknameTask.getValue()) {
                Task<Player> playerTask = new Task<Player>() {
                    @Override
                    protected Player call() throws Exception {
                        if (txtAccount.getText() == null) {
                            throw new Exception("Player name can't be empty");
                        }
                        return new Player(txtAccount.getText().replace(" ", ""), Servers.br1);
                    }
                };

                playerTask.setOnFailed(e -> {
                    GraphicUtils.callSnackbar("Player not found", bp);
                });

                playerTask.setOnSucceeded(e -> {
                    if (txtPassword.getText() != null) {
                        if (txtPassword.getText().equals(txtPassword2.getText())) {
                            UserDao userDao = new UserDao();
                            try {
                                userDao.registerUser(
                                        txtUsername.getText(),
                                        txtPassword.getText(),
                                        txtAccount.getText()
                                );
                                callSuccessActionOnSubmit();
                            } catch (SQLException | ClassNotFoundException | IOException ex) {
                                GraphicUtils.callSnackbar("Unexpected error", bp);
                                ex.printStackTrace();
                            }
                        } else {
                            GraphicUtils.callSnackbar("Passwords does not match", bp);
                        }
                    } else {
                        GraphicUtils.callSnackbar("Password cant't be empty", bp);
                    }

                });
                exec.execute(playerTask);
            } else {
                GraphicUtils.callSnackbar("Username already used", bp);
            }
        });
        exec.execute(nicknameTask);
    }

    public void initialize(URL url, ResourceBundle rb) {
        exec = Executors.newCachedThreadPool(runnable -> {
            Thread t = new Thread(runnable);
            t.setDaemon(true);
            return t;
        });
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

    private void callSuccessActionOnSubmit() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/screens/Login.fxml")
        );

        Stage loginStage = new Stage(StageStyle.TRANSPARENT);
        Stage menuStage = (Stage) bp.getScene().getWindow();

        loginStage.setScene(new Scene(loader.load()));
        LoginController controller = loader.getController();
        controller.callSuccessOnRegisteredMessage();

        loginStage.show();
        menuStage.close();
    }
}
