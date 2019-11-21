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
import utils.GraphicUtils;


import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
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
    @FXML
    Label labelGold0;
    @FXML
    Label labelGold1;
    @FXML
    Label labelGold2;
    @FXML
    Label labelGold3;
    @FXML
    Label labelGold4;
    @FXML
    Label labelGold5;
    @FXML
    Label labelGold6;
    @FXML
    Label labelGold7;
    @FXML
    Label labelGold8;
    @FXML
    Label labelGold9;
    @FXML
    Label labelDmg0;
    @FXML
    Label labelDmg1;
    @FXML
    Label labelDmg2;
    @FXML
    Label labelDmg3;
    @FXML
    Label labelDmg4;
    @FXML
    Label labelDmg5;
    @FXML
    Label labelDmg6;
    @FXML
    Label labelDmg7;
    @FXML
    Label labelDmg8;
    @FXML
    Label labelDmg9;
    @FXML
    Label labelCs0;
    @FXML
    Label labelCs1;
    @FXML
    Label labelCs2;
    @FXML
    Label labelCs3;
    @FXML
    Label labelCs4;
    @FXML
    Label labelCs5;
    @FXML
    Label labelCs6;
    @FXML
    Label labelCs7;
    @FXML
    Label labelCs8;
    @FXML
    Label labelCs9;
    @FXML
    HBox playedMatchItems01;
    @FXML
    HBox playedMatchItems02;
    @FXML
    HBox playedMatchItems11;
    @FXML
    HBox playedMatchItems12;
    @FXML
    HBox playedMatchItems21;
    @FXML
    HBox playedMatchItems22;
    @FXML
    HBox playedMatchItems31;
    @FXML
    HBox playedMatchItems32;
    @FXML
    HBox playedMatchItems41;
    @FXML
    HBox playedMatchItems42;
    @FXML
    HBox playedMatchItems51;
    @FXML
    HBox playedMatchItems52;
    @FXML
    HBox playedMatchItems61;
    @FXML
    HBox playedMatchItems62;
    @FXML
    HBox playedMatchItems71;
    @FXML
    HBox playedMatchItems72;
    @FXML
    HBox playedMatchItems81;
    @FXML
    HBox playedMatchItems82;
    @FXML
    HBox playedMatchItems91;
    @FXML
    HBox playedMatchItems92;


    private List<Rectangle> rectangles;
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

            int gold = participantChampion.getJSONObject("stats").getInt("goldEarned");
            String sgold = Integer.toString(gold);

            int dmg = participantChampion.getJSONObject("stats").getInt("totalDamageDealtToChampions");
            String sdmg = Integer.toString(dmg);

            int cs1 = participantChampion.getJSONObject("stats").getInt("neutralMinionsKilled");
            int cs2 = participantChampion.getJSONObject("stats").getInt("totalMinionsKilled");
            String scs = Integer.toString(cs1 + cs2);

            setLabelStyleByFieldCall(
                    "labelCs" + a,
                    scs + " CS",
                    null
            );

            setLabelStyleByFieldCall(
                    "labelGold" + a,
                    sgold,
                    null
            );

            setLabelStyleByFieldCall(
                    "labelDmg" + a,
                    sdmg,
                    null
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

            rectangles = new ArrayList<>();
            for (int j = 1; j <= 2; j++) {

                rectangles = GraphicUtils.createRectangleItemsRowMatch(
                        playerMatch.getItemsSlotsByParticipantId(
                                String.valueOf(participantChampion.getInt("participantId")), j),
                        3,
                        1
                );

                setHBoxStyleByFieldCall(
                        "playedMatchItems" + a + "" + j,
                        rectangles
                );
            }


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
        hbox.setSpacing(10.00);
        hbox.getChildren().addAll(fieldValue);
    }
}
