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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.JSONObject;
import utils.NumberUtils;
import utils.RiotUtils;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
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

    // Top played champions components
    @FXML
    Circle championTop1;
    @FXML
    Circle championTop2;
    @FXML
    Circle championTop3;

    // Match history champion icons components
    @FXML
    Circle playedMatchChampionIcon1;
    @FXML
    Circle playedMatchChampionIcon2;
    @FXML
    Circle playedMatchChampionIcon3;
    @FXML
    Circle playedMatchChampionIcon4;
    @FXML
    Circle playedMatchChampionIcon5;

    @FXML
    Label kdaMatchHistory1;
    @FXML
    Label kdaMatchHistory2;
    @FXML
    Label kdaMatchHistory3;
    @FXML
    Label kdaMatchHistory4;
    @FXML
    Label kdaMatchHistory5;

    @FXML
    Label championName1;
    @FXML
    Label championName2;
    @FXML
    Label championName3;
    @FXML
    Label championName4;
    @FXML
    Label championName5;

    @FXML
    Label calculatedKDA1;
    @FXML
    Label calculatedKDA2;
    @FXML
    Label calculatedKDA3;
    @FXML
    Label calculatedKDA4;
    @FXML
    Label calculatedKDA5;

    @FXML
    Label gameDuration1;
    @FXML
    Label gameDuration2;
    @FXML
    Label gameDuration3;

    @FXML
    Label result1;
    @FXML
    Label result2;
    @FXML
    Label result3;



    @FXML
    HBox playedMatch1;

    @FXML
    VBox container;

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
            Player summonerInfo = new Player("LeDragonNoir");

            // Get summoner ranked data
            Ranked rankedSummonerInfo = new Ranked(summonerInfo.getSummonerId());

            // Get top played champions info of searched summoner
            SummonerChampions summonerChampions = new SummonerChampions(summonerInfo.getSummonerId());


            // Get match history of searched summoner
            SummonerMatchList summonerMatchList = new SummonerMatchList(
                    summonerInfo.getAccountId(),
                    3
            );




            int matchSize = summonerMatchList.getPureMatchHistory().length();
            // Display match stats
            for (int i = 0 ; i < matchSize ; i++) {

                Match summonerMatch = new Match(
                        String.valueOf(
                                ((JSONObject)summonerMatchList.getPureMatchHistory().get(i))
                                        .getInt("gameId")
                        )
                );

                JSONObject matchPlayerStats = summonerMatch.
                        getParticipantDtoBySummonerAccountId(summonerInfo.getAccountId())
                        .getJSONObject("stats");

                JSONObject participantChampion = summonerMatch.
                        getParticipantDtoBySummonerAccountId(summonerInfo.getAccountId());

                Champion champion = new Champion(
                        String.valueOf(participantChampion.getInt("championId"))
                );

                String matchResult = summonerMatch.getMatchResultByParticipantId(
                        String.valueOf(participantChampion.getInt("participantId")));

                String resultLabelColor = matchResult.equals("Victory")
                        ? "#31ab47"
                        : "#bf616a";

                // Set game result match label
                Field resultField = getClass().getDeclaredField("result" + (i + 1));
                Label resultLabel = (Label) resultField.get(this);
                resultLabel.setText(matchResult);
                resultLabel.setTextFill(Paint.valueOf(resultLabelColor));

                // Set game duration label
                Field gameDurationField = getClass().getDeclaredField("gameDuration" + (i + 1));
                Label gameDurationLabel = (Label) gameDurationField.get(this);
                gameDurationLabel.setText(String.valueOf(summonerMatch.getGameDuration()));

                // Set champion name
                Field championNameField = getClass().getDeclaredField("championName" + (i + 1));
                Label championNameLabel = (Label) championNameField.get(this);
                championNameLabel.setText(
                        champion.getChampionData().getString("name")
                );

                // Set icon played champion
                Field championIconField = getClass().getDeclaredField("playedMatchChampionIcon" + (i + 1));
                Circle circle = (Circle) championIconField.get(this);
                Image championImage = new Image(champion.getImageChampionBuiltUrl(ImagesUrl.SQUARE));
                circle.setFill(new ImagePattern(championImage));

                // Set kda match
                Field kdaPlayerField = getClass().getDeclaredField("kdaMatchHistory" + (i + 1));
                Label kdaLabel = (Label) kdaPlayerField.get(this);
                kdaLabel.setText(
                        matchPlayerStats.getInt("kills") + "/" +
                                matchPlayerStats.getInt("deaths") + "/" +
                                matchPlayerStats.getInt("assists")
                );

                Field calculatedKdaPlayerField = getClass().getDeclaredField("calculatedKDA" + (i + 1));
                Label calculatedKdaLabel = (Label) calculatedKdaPlayerField.get(this);
                double calculatedKDA =  NumberUtils.round(
                        (
                                (matchPlayerStats.getDouble("kills") + matchPlayerStats.getDouble("assists"))
                                        / summonerMatch.setDeathToWhenItIsZero(matchPlayerStats.getDouble("deaths"))
                        ),
                        2
                );
                calculatedKdaLabel.setText("KDA " + calculatedKDA);
                int a = 8;
            }


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
