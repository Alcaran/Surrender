package controllers;

import business.Champion;
import business.Performance;
import business.Player;
import business.SummonerMatchList;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import data.enums.Servers;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.json.JSONObject;
import utils.GraphicUtils;

//import java.awt.*;
import java.io.File;
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

    @FXML
    TextArea infoCard;
    @FXML
    TextArea infoCard1;
    @FXML
    ImageView imgTips;
    @FXML
    ImageView imgTips1;

    private List<Object> analyzedSummonerPerformance;
    private List<Object> searchedSummonerPerformance;
    private Champion champion;
    private Executor exec;
    private Enum server;

    @FXML
    private void keyPressed(KeyEvent k) {
        if (k.getCode() == KeyCode.ENTER) {
            Task<Player> profileSummonerTask = new Task<Player>() {
                @Override
                protected Player call() throws Exception {
                    loading.setVisible(true);
                    // Search player in riot api
                    return new Player(
                            searchedPlayer.getText().replace(" ", ""),server
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

                    XYChart.Series series = new XYChart.Series();
                    series.setName(profileSummonerTask.getValue().getSummonerName());
                    ArrayList<Double> last5GamesPerformance = (ArrayList<Double>) performanceTask.getValue().get(5);
                    int i = 1;
                    for(double score : last5GamesPerformance) {
                        series.getData().add(new XYChart.Data(String.valueOf(i), score));
                        i++;
                    }

                    areaChart.getData().add(series);

                    try {
                        analyzeSummonersPerformance();
                    } catch (NoSuchFieldException | IllegalAccessException ex) {
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
    private void backButtonAction() {
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

    void initData(Champion champion, Player player, Enum s) {
        server = s;
        this.champion = champion;
        Task<List<Object>> performanceTask = getSummonerMatchListFilteredByChampion(player);
        performanceTask.setOnFailed(e -> performanceTask.getException().printStackTrace());

        performanceTask.setOnSucceeded(e -> {
            //Defining the X axis
            CategoryAxis xAxis = new CategoryAxis();

            //Defining the y Axis
            NumberAxis yAxis = new NumberAxis(0, 50, 7.5);
            yAxis.setLabel("Score");

            XYChart.Series series = new XYChart.Series();
            series.setName("You");
            ArrayList<Double> last5GamesPerformance = (ArrayList<Double>) performanceTask.getValue().get(5);
            int i = 1;
            for(double score : last5GamesPerformance) {
                series.getData().add(new XYChart.Data(String.valueOf(i), score));
                i++;
            }

            areaChart.getData().add(series);

            ward1.setText(performanceTask.getValue().get(0) + " wards");
            kda1.setText(performanceTask.getValue().get(1) + " KDA");
            gold1.setText(performanceTask.getValue().get(2) + " gold");
            damage1.setText(performanceTask.getValue().get(3) + " damage");
            score1.setText(performanceTask.getValue().get(4) + " score");
            this.analyzedSummonerPerformance = performanceTask.getValue();
            final List<Object> value = performanceTask.getValue();
            Task<JSONObject[]> tipsTask = new Task<JSONObject[]>() {
                @Override
                protected JSONObject[] call() throws Exception {
                    return new Performance(champion).getWorstPerformanceAttributesTips(
                            value
                    );
                }
            };

            tipsTask.setOnFailed(ti -> tipsTask.getException().printStackTrace());

            tipsTask.setOnSucceeded(ti -> {
                infoCard.setText(tipsTask.getValue()[0].getString("text"));
                String src = "/assets/tips-images/" + tipsTask.getValue()[0].getString("url");
                imgTips.setImage(new Image(src));
                infoCard1.setText(tipsTask.getValue()[1].getString("text"));
                String src1 = "/assets/tips-images/" + tipsTask.getValue()[1].getString("url");
                imgTips1.setImage(new Image(src1));
            });
            exec.execute(tipsTask);
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
                        new int[]{Integer.parseInt(champion.getChampionData().getString("key"))},
                        server
                );

                return new Performance(champion).calculatePerformanceScoreWithMultipleMatches(
                        summonerMatchList,
                        player.getAccountId(),
                        server
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
