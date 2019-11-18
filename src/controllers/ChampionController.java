package controllers;

import business.Champion;
import business.Performance;
import business.Player;
import business.SummonerMatchList;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.GraphicUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ChampionController implements Initializable {

    @FXML
    Label ward1;
    @FXML
    Label ward2;

    @FXML
    Label kda1;
    @FXML
    Label kda2;

    @FXML
    Label gold1;
    @FXML
    Label gold2;

    @FXML
    Label damage1;
    @FXML
    Label damage2;

    @FXML
    Label score1;
    @FXML
    Label score2;

    @FXML
    private BorderPane bp;

    @FXML
    JFXTextField searchedPlayer;

    @FXML
    JFXSpinner loading;

    @FXML
    Label result;

    @FXML
    Label searchedPlayerResult;

    @FXML
    AreaChart areaChart;

    private List<Object> analyzedSummonerPerformance;
    private List<Object> searchedSummonerPerformance;
    private Champion champion;
    private Executor exec;

    @FXML
    private void keyPressed(KeyEvent k) {
        if (k.getCode() == KeyCode.ENTER) {
            Task<Player> profileSummonerTask = new Task<Player>() {
                @Override
                protected Player call() throws Exception {
                    loading.setVisible(true);
                    // Search player in riot api
                    return new Player(
                            searchedPlayer.getText().replace(" ", "")
                    );
                }
            };

            profileSummonerTask.setOnFailed(e -> {
                GraphicUtils.callSnackbar("Player not found", bp);
                loading.setVisible(false);
            });

            profileSummonerTask.setOnSucceeded(e -> {
                Task<List<Object>> performanceTask =
                        getSummonerMatchListFilteredByChampion(profileSummonerTask.getValue());
                performanceTask.setOnFailed(s -> {
                    GraphicUtils.callSnackbar("Player does not have matches with this champion", bp);
                    loading.setVisible(false);
                });

                performanceTask.setOnSucceeded(s -> {
                    result.setVisible(true);
                    searchedPlayerResult.setVisible(true);
                    searchedPlayerResult.setText(profileSummonerTask.getValue().getSummonerName());
                    ward2.setText(performanceTask.getValue().get(0) + " wards");
                    kda2.setText(performanceTask.getValue().get(1) + " KDA");
                    gold2.setText(performanceTask.getValue().get(2) + " gold");
                    damage2.setText(performanceTask.getValue().get(3) + " damage");
                    score2.setText(performanceTask.getValue().get(4) + " score");
                    this.searchedSummonerPerformance = performanceTask.getValue();

                    XYChart.Series series1 = new XYChart.Series();
                    series1.setName(profileSummonerTask.getValue().getSummonerName());
                    ArrayList<Double> last5GamesPerformance = (ArrayList<Double>) performanceTask.getValue().get(5);
                    series1.getData().add(new XYChart.Data("1", last5GamesPerformance.get(0)));
                    series1.getData().add(new XYChart.Data("2", last5GamesPerformance.get(1)));
                    series1.getData().add(new XYChart.Data("3", last5GamesPerformance.get(2)));
                    series1.getData().add(new XYChart.Data("4", last5GamesPerformance.get(3)));
                    series1.getData().add(new XYChart.Data("5", last5GamesPerformance.get(4)));

                    areaChart.getData().add(series1);

                    try {
                        analyzeSummonersPerformance();
                    } catch (NoSuchFieldException ex) {
                        ex.printStackTrace();
                    } catch (IllegalAccessException ex) {
                        ex.printStackTrace();
                    }
                    loading.setVisible(false);
                });
                exec.execute(performanceTask);
            });
            exec.execute(profileSummonerTask);
        }
    }

    @FXML
    private void handleButtonAction() {
        System.exit(0);
    }

    @FXML
    private void backButtonAction() throws IOException {
        Stage stage = (Stage) bp.getScene().getWindow();
        stage.close();
    }

    public void initialize(URL url, ResourceBundle rb) {
        exec = Executors.newCachedThreadPool(runnable -> {
            Thread t = new Thread(runnable);
            t.setDaemon(true);
            return t;
        });
    }

    void initData(Champion champion, Player player) {
        this.champion = champion;
        Task<List<Object>> performanceTask = getSummonerMatchListFilteredByChampion(player);
        performanceTask.setOnFailed(e -> performanceTask.getException().printStackTrace());

        performanceTask.setOnSucceeded(e -> {
            //Defining the X axis
            CategoryAxis xAxis = new CategoryAxis();

            //Defining the y Axis
            NumberAxis yAxis = new NumberAxis(0, 50, 7.5);
            yAxis.setLabel("Score");

            XYChart.Series series1 = new XYChart.Series();
            series1.setName("You");
            ArrayList<Double> last5GamesPerformance = (ArrayList<Double>) performanceTask.getValue().get(5);
            series1.getData().add(new XYChart.Data("1", last5GamesPerformance.get(0)));
            series1.getData().add(new XYChart.Data("2", last5GamesPerformance.get(1)));
            series1.getData().add(new XYChart.Data("3", last5GamesPerformance.get(2)));
            series1.getData().add(new XYChart.Data("4", last5GamesPerformance.get(3)));
            series1.getData().add(new XYChart.Data("5", last5GamesPerformance.get(4)));

            areaChart.getData().add(series1);

            ward1.setText(performanceTask.getValue().get(0) + " wards");
            kda1.setText(performanceTask.getValue().get(1) + " KDA");
            gold1.setText(performanceTask.getValue().get(2) + " gold");
            damage1.setText(performanceTask.getValue().get(3) + " damage");
            score1.setText(performanceTask.getValue().get(4) + " score");
            this.analyzedSummonerPerformance = performanceTask.getValue();
        });
        exec.execute(performanceTask);
    }

    private Task<List<Object>> getSummonerMatchListFilteredByChampion(Player player) {
        return new Task<List<Object>>() {
            @Override
            protected List<Object> call() throws Exception {
                SummonerMatchList summonerMatchList = new SummonerMatchList(
                        player.getAccountId(),
                        5,
                        null,
                        new int[]{Integer.valueOf(champion.getChampionData().getString("key"))},
                        false
                );
                return new Performance(champion).calculatePerformanceScoreWithMultipleMatches(
                        summonerMatchList,
                        player.getAccountId()
                );

            }
        };
    }

    private void analyzeSummonersPerformance() throws NoSuchFieldException, IllegalAccessException {
        String[] labels = new String[]{"ward", "kda", "gold", "damage", "score"};
        for (int i = 0; i < analyzedSummonerPerformance.size() - 1; i++) {
            boolean analyzedWins = false;
            if ((double) analyzedSummonerPerformance.get(i) > (double) searchedSummonerPerformance.get(i))
                analyzedWins = true;
            setFillLabelColorForWinnersSummoner(analyzedWins, labels[i]);
        }
    }


    private void setFillLabelColorForWinnersSummoner(boolean isAnalyzedSummonerWinner, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        if (isAnalyzedSummonerWinner) {
            Field field = getClass().getDeclaredField(fieldName + 1);
            Label label = (Label) field.get(this);
            label.setTextFill(Paint.valueOf("#31ab47"));
            Field field2 = getClass().getDeclaredField(fieldName + 2);
            Label label2 = (Label) field2.get(this);
            label2.setTextFill(Paint.valueOf("#bf616a"));
            result.setText("Victory");
            result.setTextFill(Paint.valueOf("#31ab47"));
        } else {
            Field field = getClass().getDeclaredField(fieldName + 2);
            Label label = (Label) field.get(this);
            label.setTextFill(Paint.valueOf("#31ab47"));
            Field field2 = getClass().getDeclaredField(fieldName + 1);
            Label label2 = (Label) field2.get(this);
            label2.setTextFill(Paint.valueOf("#bf616a"));
            result.setText("Defeat");
            result.setTextFill(Paint.valueOf("#bf616a"));
        }
        Field field = getClass().getDeclaredField(fieldName + 2);
        Label label = (Label) field.get(this);
        label.setVisible(true);
    }
}
