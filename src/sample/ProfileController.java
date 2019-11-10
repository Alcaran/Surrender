package sample;

import business.*;
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
            Player summonerInfo = new Player("Alcarann");

            // Get summoner ranked data
            Ranked rankedSummonerInfo = new Ranked(summonerInfo.getSummonerId());

            // Get top played champions info of searched summoner
            SummonerChampions summonerChampions = new SummonerChampions(summonerInfo.getSummonerId());


            // Get match history of searched summoner
            SummonerMatchList summonerMatchList = new SummonerMatchList(
                    summonerInfo.getAccountId(),
                    5
            );

            // Get summoner match

            Match summonerMatch = new Match(
                    String.valueOf(
                            ((JSONObject)summonerMatchList.getPureMatchHistory().get(0))
                                    .getInt("gameId")
                    )
            );

            JSONObject matchPlayerStats = summonerMatch.getStatsBySummonerAccountId(summonerInfo.getAccountId());

            // Display match stats

            kdaMatchistory1.setText(
                    matchPlayerStats.getInt("kills") + "/" +
                            matchPlayerStats.getInt("deaths") + "/" +
                            matchPlayerStats.getInt("assists")
            );

            // Display top played champions image icon
            Image championTop1Image = new Image(
                    RiotUtils
                            .getImageChampionBuiltUrl(
                                    summonerChampions.getTopPlayedChampions().get(0), ImagesUrl.SQUARE
                            )
            );
            championTop1.setFill(new ImagePattern(championTop1Image));

            Image championTop2Image = new Image(
                    RiotUtils
                            .getImageChampionBuiltUrl(
                                    summonerChampions.getTopPlayedChampions().get(1), ImagesUrl.SQUARE
                            )
            );
            championTop2.setFill(new ImagePattern(championTop2Image));

            Image championTop3Image = new Image(
                    RiotUtils
                            .getImageChampionBuiltUrl(summonerChampions.getTopPlayedChampions().get(2), ImagesUrl.SQUARE
                            )
            );
            championTop3.setFill(new ImagePattern(championTop3Image));

            Image championMatchHistory1 = new Image(
                    RiotUtils
                            .getImageChampionBuiltUrl(summonerMatchList.getMatchHistoryPlayedChampions().get(0), ImagesUrl.SQUARE));
            playedMatchChampionIcon1.setFill(new ImagePattern(championMatchHistory1));

            // Set profile image icon
            String profileIconId = String.valueOf(summonerInfo.getIconId());
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
            playerLevel.setText(String.valueOf(summonerInfo.getSummonerLevel()));

            // Set summoner name text
            summonerName.setText(summonerInfo.getSummonerName());


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
        Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        s.setScene(new Scene(root));
        s.initStyle(StageStyle.TRANSPARENT);
        s.show();

        Stage stage  = (Stage) bp.getScene().getWindow();
        stage.close();
    }
}
