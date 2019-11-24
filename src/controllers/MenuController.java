package controllers;

import business.Player;
import com.jfoenix.controls.*;
import data.enums.Servers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.GraphicUtils;
import utils.LogicUtils;


import javax.net.ssl.SNIServerName;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MenuController implements Initializable {

    private Executor exec;
    private Stage profileStage;
    private ArrayList<String> linkedAccount;

    @FXML
    private JFXComboBox<Servers> comboBox;
    @FXML
    private BorderPane bp;
    @FXML
    private JFXDrawer drwMenu;
    @FXML
    private JFXTextField summonerSearch;
    @FXML
    private JFXSpinner loaderSpinner;

    public void initialize(URL url, ResourceBundle rb) {
        comboBox.getItems().setAll(Servers.values());
        comboBox.setStyle(
                "-fx-font: 12px \"Josefin Sans Regular\"; -fx-text-fill: WHITE; -fx-prompt-text-fill: WHITE;"
        );

        exec = Executors.newCachedThreadPool(runnable -> {
            Thread t = new Thread(runnable);
            t.setDaemon(true);
            return t;
        });
    }

    void initData(ArrayList<String> linkedAccount) {
        this.linkedAccount = linkedAccount;
        try {
            ArrayList<JFXButton> buttons = new ArrayList<>();

            VBox box = FXMLLoader.load(getClass().getResource("/screens/Drawer.fxml"));
            JFXButton btn = generateDrawerButton("Back", "btnBack");


            for (int i = 0; i < linkedAccount.size(); i++) {
                buttons.add(generateDrawerButton(linkedAccount.get(i), "btn" + i));
            }

            buttons.add(btn);
            box.getChildren().addAll(buttons);

            ObservableList<Node> workingCollection = FXCollections.observableArrayList(box.getChildren());
            Collections.swap(workingCollection, 0, setToOneIfItIsZero(linkedAccount.size()));
            box.getChildren().setAll(workingCollection);

            drwMenu.setSidePane(box);
            int i = 0;
            for (Node node : box.getChildren()) {
                String name = LogicUtils.removeLastChar(node.getId());
                int finalI = i;
                node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                    if (name.equals("btn"))
                        loadSearchedProfile(linkedAccount.get(finalI - 1), Servers.br1);
                    else if (node.getId().equals("btnBack"))
                        drawCloseButton();
                    else if (node.getId().equals("btnLogin")) {
                        try {
                            Stage s = new Stage();
                            Parent root = FXMLLoader.load(getClass().getResource("/screens/Login.fxml"));
                            s.setScene(new Scene(root));
                            s.initStyle(StageStyle.TRANSPARENT);
                            s.show();
                            Stage stage = (Stage) bp.getScene().getWindow();
                            stage.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                  i++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void keyPressed(KeyEvent k) {
        if (k.getCode() == KeyCode.ENTER) {
            loadSearchedProfile(summonerSearch.getText().replace(" ", ""), comboBox.getValue());
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

    public void loadSearchedProfile(String summonerName, Servers server) {
        Task<Player> profileSummonerTask = new Task<Player>() {
            @Override
            protected Player call() throws Exception {
                loaderSpinner.setVisible(true);
                // Search player in riot api
                return new Player(summonerName, server);
            }
        };

        profileSummonerTask.setOnFailed(e -> {
            GraphicUtils.callSnackbar("Player not found", bp);
            loaderSpinner.setVisible(false);
        });

        profileSummonerTask.setOnSucceeded(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/screens/Profile.fxml")
                );

                // Set profile page to load
                profileStage = new Stage(StageStyle.TRANSPARENT);
                profileStage.setScene(new Scene(loader.load()));

                // Pass summoner to profile page as parameter
                ProfileController controller = loader.getController();

                // Set menu stage
                Stage menuStage = (Stage) bp.getScene().getWindow();
                profileStage.initStyle(StageStyle.TRANSPARENT);

                // Call initialize data of profile screen
                controller.initData(profileSummonerTask.getValue(), menuStage, profileStage, server, linkedAccount);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        exec.execute(profileSummonerTask);
    }

    private JFXButton generateDrawerButton(String buttonText, String buttonName) {
        JFXButton back = new JFXButton(buttonText);
        back.setId(buttonName);
        back.setPrefHeight(80);
        back.setPrefWidth(250);
        back.setTextFill(Paint.valueOf("WHITE"));
        back.setFont(Font.font("Josefin Sans Regular"));
        back.setStyle("-fx-font-size: 20");
        return back;
    }

    private int setToOneIfItIsZero(int number) {
        return number <= 0 ? 1 : number;
    }
}
