package controllers;

import business.Champion;
import business.Match;
import business.Player;
import business.Ranked;
import data.api.ApiHelper;
import data.enums.ImagesUrl;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class AnalysesController {
    @FXML
    private BorderPane bp;
    @FXML
    Circle championCircle0;
    @FXML
    Circle championCircle1;
    @FXML
    Circle championCircle2;
    @FXML
    Circle championCircle3;
    @FXML
    Circle championCircle4;
    @FXML
    Circle championCircle5;
    @FXML
    Circle championCircle6;
    @FXML
    Circle championCircle7;
    @FXML
    Circle championCircle8;
    @FXML
    Circle championCircle9;
    @FXML
    Label labelName0;
    @FXML
    Label labelName1;
    @FXML
    Label labelName2;
    @FXML
    Label labelName3;
    @FXML
    Label labelName4;
    @FXML
    Label labelName5;
    @FXML
    Label labelName6;
    @FXML
    Label labelName7;
    @FXML
    Label labelName8;
    @FXML
    Label labelName9;
    @FXML
    Label labelKda0;
    @FXML
    Label labelKda1;
    @FXML
    Label labelKda2;
    @FXML
    Label labelKda3;
    @FXML
    Label labelKda4;
    @FXML
    Label labelKda5;
    @FXML
    Label labelKda6;
    @FXML
    Label labelKda7;
    @FXML
    Label labelKda8;
    @FXML
    Label labelKda9;

    private JSONObject player;
    private JSONObject championJson;

    @FXML
    private void handleButtonAction() {
        System.exit(0);
    }
    @FXML
    private void backButtonAction() throws IOException {
        Stage stage  = (Stage) bp.getScene().getWindow();
        stage.close();
    }
    void initData(Match playerMatch, Stage analysesStage, JSONObject championJsonData) throws Exception {

        this.championJson = championJsonData;
        JSONArray playersArray = playerMatch.getParticipantIdentities();
        for(int a = 0; a < playersArray.length(); a++){

            player = (JSONObject) playersArray.get(a);
            JSONObject participantChampion = playerMatch.
                    getParticipantDtoBySummonerAccountId(player.getJSONObject("player").getString("accountId"));

            Champion champion = new Champion(
                    String.valueOf(participantChampion.getInt("championId")),
                    championJson
            );

            setLabelStyleByFieldCall(
                    "labelName" + a,
                    player.getJSONObject("player").getString("summonerName"),
                    null
            );

            setCircleStyleByFieldCall(
                    "championCircle" + a,
                    new Image(champion.getImageChampionBuiltUrl(ImagesUrl.SQUARE))
            );

            String kdaDisplayable = participantChampion.getJSONObject("stats").getInt("kills") + "/" +
                    participantChampion.getJSONObject("stats").getInt("deaths") + "/" +
                    participantChampion.getJSONObject("stats").getInt("assists");
            setLabelStyleByFieldCall(
                    "labelKda" + a,
                    kdaDisplayable,
                    null
            );



        }
        analysesStage.show();
    }

    private void setLabelStyleByFieldCall(String fieldName, String fieldValue, String color)
            throws IllegalAccessException, NoSuchFieldException {
        Field field = getClass().getDeclaredField(fieldName);
        javafx.scene.control.Label label = (javafx.scene.control.Label) field.get(this);
        label.setText(String.valueOf(fieldValue));
        if (color != null)
            label.setTextFill(Paint.valueOf(color));
    }

    private void setCircleStyleByFieldCall(String fieldName, Image fieldValue)
            throws IllegalAccessException, NoSuchFieldException {
        Field field = getClass().getDeclaredField(fieldName);
        Circle circle = (Circle) field.get(this);
        circle.setFill(new ImagePattern(fieldValue));
    }

    private void setHBoxStyleByFieldCall(String fieldName, List<Rectangle> fieldValue)
            throws IllegalAccessException, NoSuchFieldException {
        Field field = getClass().getDeclaredField(fieldName);
        HBox hbox = (HBox) field.get(this);
        hbox.getChildren().addAll(fieldValue);
    }
}
