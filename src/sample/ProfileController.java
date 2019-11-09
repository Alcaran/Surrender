package sample;

import business.Ranked;
import data.api.ApiHelper;
import data.api.Enums.ImagesUrl;
import data.api.Enums.Tiers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.JSONUtils;
import utils.RiotUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    @FXML
    Label playerLevel;

    @FXML
    Circle imageCircle;

    @FXML
    Label summonerName;

    @FXML
    ImageView tier;

    @FXML
    Label leaguePoints;

    @FXML
    Label rankedWins;

    @FXML
    Label rankedLosses;

    @FXML
    Circle championTop1;

    @FXML
    Circle championTop2;

    @FXML
    Circle championTop3;

    @FXML
    Circle playedMatchChampionIcon1;

    @FXML
    Label kdaMatchistory1;

    @FXML
    private void handleButtonAction() {
        System.exit(0);
    }

    @FXML
    private void minimizeButtonAction() {
        Stage stage  = (Stage) bp.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private BorderPane bp;

    @FXML
    private void analysesButtonAction() throws IOException {
        Stage s = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Analyses.fxml"));
        s.setScene(new Scene(root));
        s.initStyle(StageStyle.TRANSPARENT);

        s.show();
        Stage stage  = (Stage) bp.getScene().getWindow();
        stage.close();
    }

    public void initialize(URL url, ResourceBundle rb) {
        ApiHelper apiHelper = new ApiHelper();
        try {

            // Get first details of searched summoner profile
            JSONObject summonerInfo = apiHelper.getSumonerInfo("Alcarann");

            // Get summoner ranked data
            Ranked rankedSummonerInfo = new Ranked(summonerInfo.getString("id"));
            // Get top played champions info of searched summoner
            JSONArray championsSummonerInfo =
                    apiHelper.getChampionsSummonerInfo(summonerInfo.getString("id"))
                    .getJSONArray("array");

            ArrayList<JSONObject> topPlayedChampions = RiotUtils.getChampionsNameById(
                    JSONUtils.getNElementsOfJSONArrayAsStringArray(3, championsSummonerInfo, "championId")
            );

            // Get match history of searched summoner
            JSONArray matchHistory =
                    apiHelper.getUserMatchHistory(summonerInfo.getString("accountId"), 5)
                    .getJSONArray("matches");

            ArrayList<JSONObject> MatchHistoryPlayedChampions = RiotUtils.getChampionsNameById(
                    JSONUtils.getNElementsOfJSONArrayAsStringArray(5, matchHistory, "champion")
            );

            JSONObject fullMatchDetails = apiHelper
                    .getMatchDetails(
                            String.valueOf(((JSONObject) matchHistory.get(0)).getInt("gameId"))
                    );
            String participantId = RiotUtils
                    .getMatchParticipantId(
                            summonerInfo.getString("accountId"),
                            fullMatchDetails.getJSONArray("participantIdentities")
                            );
            JSONObject matchPlayerParticipant = RiotUtils
                    .getParticipant(
                            participantId,
                            fullMatchDetails.getJSONArray("participants")
                    );

            JSONObject matchPlayerStats = matchPlayerParticipant.getJSONObject("stats");

            // Display match stats

            kdaMatchistory1.setText(
                    matchPlayerStats.getInt("kills") + "/" +
                            matchPlayerStats.getInt("deaths") + "/" +
                            matchPlayerStats.getInt("assists")
            );

            // Display top played champions image icon
            Image championTop1Image = new Image(
                    RiotUtils
                            .getImageChampionBuiltUrl(topPlayedChampions.get(0), ImagesUrl.SQUARE));
            championTop1.setFill(new ImagePattern(championTop1Image));

            Image championTop2Image = new Image(
                    RiotUtils
                            .getImageChampionBuiltUrl(topPlayedChampions.get(1), ImagesUrl.SQUARE));
            championTop2.setFill(new ImagePattern(championTop2Image));

            Image championTop3Image = new Image(
                    RiotUtils
                            .getImageChampionBuiltUrl(topPlayedChampions.get(2), ImagesUrl.SQUARE));
            championTop3.setFill(new ImagePattern(championTop3Image));

            Image championMatchHistory1 = new Image(
                    RiotUtils
                            .getImageChampionBuiltUrl(MatchHistoryPlayedChampions.get(0), ImagesUrl.SQUARE));
            playedMatchChampionIcon1.setFill(new ImagePattern(championMatchHistory1));

            // Set profile image icon
            String profileIconId = String.valueOf(summonerInfo.getInt("profileIconId"));
            Image image = new Image(
                    "http://ddragon.leagueoflegends.com/cdn/9.21.1/img/profileicon/"
                            + profileIconId + ".png", false);
            imageCircle.setFill(new ImagePattern(image));

            // Set league points text
            leaguePoints.setText(String.valueOf(rankedSummonerInfo.getLeaguePoints()) + " LP");

            // Set league points text
            rankedWins.setText(String.valueOf(rankedSummonerInfo.getWins()) + " W");

            // Set league points text
            rankedLosses.setText(String.valueOf(rankedSummonerInfo.getLosses()) + " L");

            // Set summoner level text
            playerLevel.setText(String.valueOf(summonerInfo.getInt("summonerLevel")));

            // Set summoner name text
            summonerName.setText(summonerInfo.getString("name"));


            // Set tier image
            File file = new File(Tiers.valueOf(rankedSummonerInfo.getTier()).getImageEloPath());
            tier.setImage(new Image(file.toURI().toString()));
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @FXML
    private void backButtonAction() throws IOException{

        Stage s = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("MenuSearch.fxml"));
        s.setScene(new Scene(root));
        s.initStyle(StageStyle.TRANSPARENT);
        s.show();

        Stage stage  = (Stage) bp.getScene().getWindow();
        stage.close();
    }
}
