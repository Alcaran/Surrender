package controllers;

import business.Player;
import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.GraphicUtils;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MenuController implements Initializable {

    private Executor exec;
    private Stage stage;

    @FXML
    private JFXComboBox<String> comboBox;
    @FXML
    private BorderPane bp;
    @FXML
    private JFXDrawer drwMenu;
    @FXML
    private JFXTextField summonerSearch;
    @FXML
    private JFXSpinner loaderSpinner;

    public void initialize(URL url, ResourceBundle rb) {
        try {
            VBox box = FXMLLoader.load(getClass().getResource("../screens/Drawer.fxml"));
            drwMenu.setSidePane(box);
            for (Node node : box.getChildren()) {
                node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                    switch (node.getId()) {
                        case "btnBack":
                            drawCloseButton();
                            break;
                        case "btnLogin":
                            Stage s = new Stage();
                            try {
                                Parent root = FXMLLoader.load(getClass().getResource("../screens/Login.fxml"));
                                s.setScene(new Scene(root));
                                s.initStyle(StageStyle.TRANSPARENT);
                                s.show();
                                Stage stage = (Stage) bp.getScene().getWindow();
                                stage.close();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            break;
                    }
                });
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        comboBox.getItems().add("BR");
        comboBox.getItems().add("LAS");
        comboBox.getItems().add("KR");
        comboBox.getItems().add("EU");
        comboBox.getItems().add("NA");
        comboBox.setStyle(
                "-fx-font: 12px \"Josefin Sans Regular\"; -fx-text-fill: WHITE; -fx-prompt-text-fill: WHITE;"
        );

        exec = Executors.newCachedThreadPool(runnable -> {
            Thread t = new Thread(runnable);
            t.setDaemon(true);
            return t;
        });
    }

    @FXML
    private void keyPressed(KeyEvent k) {
        if (k.getCode() == KeyCode.ENTER) {
            Task<Player> profileSummonerTask = new Task<Player>() {
                @Override
                protected Player call() throws Exception {
                    loaderSpinner.setVisible(true);
                    // Search player in riot api
                    Player searchedSummoner = new Player(
                            summonerSearch.getText().replace(" ", "")
                    );

                    return searchedSummoner;
                }
            };

            profileSummonerTask.setOnFailed(e -> {
                GraphicUtils.callSnackbar("Player not found", bp);
                loaderSpinner.setVisible(false);
            });

            profileSummonerTask.setOnSucceeded(e -> {
                try {
                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource("../screens/Profile.fxml")
                    );

                    // Set profile page to load
                    stage = new Stage(StageStyle.TRANSPARENT);
                    stage.setScene(new Scene(loader.load()));
                    // Pass summoner to profile page as parameter
                    ProfileController controller = loader.getController();
                    controller.initData(profileSummonerTask.getValue(), bp);


                    loaderSpinner.setVisible(false);

                    // Open profile page and close menu page
                    stage.show();
//                    Stage s = (Stage) bp.getScene().getWindow();
//                    s.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            exec.execute(profileSummonerTask);
        }
    }

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
    private void drawButton() {
        drwMenu.open();
        drwMenu.setPrefWidth(200.00);
        drwMenu.setVisible(true);
        drwMenu.setDisable(false);
    }

    @FXML
    private void drawCloseButton() {
        drwMenu.close();
        drwMenu.setDisable(true);
    }
}
