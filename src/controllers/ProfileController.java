package controllers;

import business.*;
import data.enums.ImagesUrl;
import data.enums.Tiers;
import data.api.ApiHelper;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.JSONObject;
import utils.GraphicUtils;
import utils.NumberUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ProfileController implements Initializable {

    private Executor exec;
    private Stage analysesStage;

    @FXML
    Label playerLevel;

    @FXML
    Circle imageCircle;

    @FXML
    Label summonerName;

    @FXML
    ImageView tier;
    @FXML
    ImageView tierBorder;

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
    Label kdaMatchHistory1;
    @FXML
    Label kdaMatchHistory2;
    @FXML
    Label kdaMatchHistory3;

    @FXML
    Label championName1;
    @FXML
    Label championName2;
    @FXML
    Label championName3;

    @FXML
    Label calculatedKDA1;
    @FXML
    Label calculatedKDA2;
    @FXML
    Label calculatedKDA3;

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
    Label score1;
    @FXML
    Label score2;
    @FXML
    Label score3;

    @FXML
    ImageView avgTier1;
    @FXML
    ImageView avgTier2;
    @FXML
    ImageView avgTier3;


    @FXML
    HBox playedMatch1;

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
    private BorderPane bp;

    private List<Rectangle> rectangles;
    private SummonerChampions summonerChampions;
    private Player summoner;
    private JSONObject championJsonData;
    private ArrayList<Match> listMatches;
    private Enum server;
    private ArrayList<String> linkedAccount;

    private void callChampionScreen(Champion champion) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/screens/Champion.fxml")
        );
        Stage stage = new Stage(StageStyle.TRANSPARENT);
        stage.setScene(new Scene(loader.load()));

        ChampionController controller = loader.getController();
        controller.initData(champion, summoner, server);

        stage.show();
    }

    @FXML
    private void handleButtonAction() {
        System.exit(0);
    }

    @FXML
    private void handleTopPlayedChampionAction1() throws Exception {

        callChampionScreen(
                new Champion(
                        summonerChampions.getChampionByIndex(0).getChampionData().getString("key"),
                        championJsonData
                )
        );
    }

    @FXML
    private void handleTopPlayedChampionAction2() throws Exception {
        callChampionScreen(
                new Champion(
                        summonerChampions.getChampionByIndex(1).getChampionData().getString("key"),
                        championJsonData
                ));
    }

    @FXML
    private void handleTopPlayedChampionAction3() throws Exception {
        callChampionScreen(
                new Champion(
                        summonerChampions.getChampionByIndex(2).getChampionData().getString("key"),
                        championJsonData
                )
        );
    }

    @FXML
    private void analysesButtonAction(ActionEvent event) throws Exception {

        final Node source = (Node) event.getSource();
        String id = source.getId();

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/screens/Analyses.fxml")
        );

        analysesStage = new Stage(StageStyle.TRANSPARENT);
        analysesStage.setScene(new Scene(loader.load()));
        AnalysesController analyses = loader.getController();
        analysesStage.initStyle(StageStyle.TRANSPARENT);

        if(id.equals("btnMatch1"))
            analyses.initData(listMatches.get(0), analysesStage, championJsonData);
        else if(id.equals("btnMatch2"))
            analyses.initData(listMatches.get(1), analysesStage, championJsonData);
        else
            analyses.initData(listMatches.get(2), analysesStage, championJsonData);
    }

    @FXML
    private void minimizeButtonAction() {
        Stage stage = (Stage) bp.getScene().getWindow();
        stage.setIconified(true);
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
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/screens/Menu.fxml")
        );

        // Set profile page to load
        Stage menuStage = new Stage(StageStyle.TRANSPARENT);
        menuStage.setScene(new Scene(loader.load()));

        MenuController controller = loader.getController();
        controller.initData(linkedAccount);

        Stage profileStage = (Stage) bp.getScene().getWindow();

        profileStage.close();
        menuStage.show();
    }

    void initData(Player searchedSummoner, Stage menuStage, Stage profileStage, Enum s, ArrayList<String> linkedAccount) {
        this.linkedAccount = linkedAccount;
        server = s;
        this.summoner = searchedSummoner;
        Task<JSONObject> championArrDataTask = new Task<JSONObject>() {
            @Override
            protected JSONObject call() throws Exception {
                ApiHelper apiHelper = new ApiHelper();
                return apiHelper.getChampionData().getJSONObject("data");
            }
        };

        championArrDataTask.setOnFailed(e -> championArrDataTask.getException().printStackTrace());

        championArrDataTask.setOnSucceeded(e -> {
            JSONObject championArrData = championArrDataTask.getValue();
            this.championJsonData = championArrData;
            // Get summoner ranked data
//            Task<Ranked> rankedSummonerInfoTask = new Task<Ranked>() {
//                @Override
//                protected Ranked call() throws Exception {
//                    return new Ranked(searchedSummoner.getSummonerId(),server);
//                }
//            };
//
//            rankedSummonerInfoTask.setOnFailed(eRanked -> rankedSummonerInfoTask.getException().printStackTrace());
//
//            rankedSummonerInfoTask.setOnSucceeded(eRanked -> {
//                Ranked rankedSummonerInfo = rankedSummonerInfoTask.getValue();
//
//                // Set league points text
//                leaguePoints.setText(rankedSummonerInfo.getLeaguePoints() + " LP");
//
//                // Set league points text
//                rankedWins.setText(rankedSummonerInfo.getWins() + " W");
//
//                // Set league points text
//                rankedLosses.setText(rankedSummonerInfo.getLosses() + " L");
//
//                // Set tier image
//                File file = new File(Tiers.valueOf(rankedSummonerInfo.getTier()).getImageEloPath());
//                tier.setImage(new Image(file.toURI().toString()));
//            });


            // Get top played champions info of searched summoner
            Task<SummonerChampions> summonerChampionsTask = new Task<SummonerChampions>() {
                @Override
                protected SummonerChampions call() throws Exception {
                    return new SummonerChampions(
                            searchedSummoner.getSummonerId(),
                            championArrData,
                            server
                    );
                }
            };

            summonerChampionsTask.setOnFailed(eChampions -> summonerChampionsTask.getException().printStackTrace());

            summonerChampionsTask.setOnSucceeded(eChampions -> {
                SummonerChampions summonerChampions = summonerChampionsTask.getValue();
                this.summonerChampions = summonerChampions;
                // Display top played champions image icon
                Image championTop1Image = new Image(
                        summonerChampions.getChampionByIndex(0).getImageChampionBuiltUrl(ImagesUrl.SQUARE)
                );
                championTop1.setFill(new ImagePattern(championTop1Image));

                Image championTop2Image = new Image(
                        summonerChampions.getChampionByIndex(1).getImageChampionBuiltUrl(ImagesUrl.SQUARE)
                );
                championTop2.setFill(new ImagePattern(championTop2Image));

                Image championTop3Image = new Image(
                        summonerChampions.getChampionByIndex(2).getImageChampionBuiltUrl(ImagesUrl.SQUARE)
                );
                championTop3.setFill(new ImagePattern(championTop3Image));
            });

            listMatches = new ArrayList<>();

            // Get match history of searched summoner
            Task<Void> summonerMatchListTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    Ranked rankedSummonerInfo = new Ranked(searchedSummoner.getSummonerId(), server);

                    // Set league points text
                    leaguePoints.setText(rankedSummonerInfo.getLeaguePoints() + " LP");

                    // Set league points text
                    rankedWins.setText(rankedSummonerInfo.getWins() + " W");

                    // Set league points text
                    rankedLosses.setText(rankedSummonerInfo.getLosses() + " L");

                    // Set tier image
                    File file = new File(Tiers.valueOf(rankedSummonerInfo.getTier()).getImageEloPath());
                    tier.setImage(new Image(file.toURI().toString()));

                    // Set tier image
                    File fileBorder = new File(Tiers.valueOf(rankedSummonerInfo.getTier()).getImageEloBorderPath());
                    tierBorder.setImage(new Image(fileBorder.toURI().toString()));


                    SummonerMatchList summonerMatchList = new SummonerMatchList(
                            searchedSummoner.getAccountId(),
                            3,
                            new int[]{0},
                            server
                    );

                    int matchSize = summonerMatchList.getPureMatchHistory().length();
                    try {
                        for (int i = 0; i < matchSize; i++) {
                            Match summonerMatch = new Match(
                                    String.valueOf(
                                            ((JSONObject) summonerMatchList.getPureMatchHistory().get(i))
                                                    .getLong("gameId")
                                    ),
                                    server
                            );

                            listMatches.add(summonerMatch);

                            JSONObject matchPlayerStats = summonerMatch.
                                    getParticipantDtoBySummonerAccountId(searchedSummoner.getAccountId())
                                    .getJSONObject("stats");

                            JSONObject participantChampion = summonerMatch.
                                    getParticipantDtoBySummonerAccountId(searchedSummoner.getAccountId());

                            Champion champion = new Champion(
                                    String.valueOf(participantChampion.getInt("championId")),
                                    championArrData
                            );

                            Performance summonerPerformance = new Performance(
                                    champion,
                                    matchPlayerStats
                            );

                            String matchResult = summonerMatch.getMatchResultByParticipantId(
                                    String.valueOf(participantChampion.getInt("participantId")));

                            String resultLabelColor = matchResult.equals("Victory")
                                    ? "#31ab47"
                                    : "#bf616a";

                            // Create items rectangles
                            rectangles = new ArrayList<>();
                            for (int j = 1; j <= 2; j++) {
                                rectangles = GraphicUtils.createRectangleItemsRow(
                                        summonerMatch.getItemsSlotsByParticipantId(
                                                String.valueOf(participantChampion.getInt("participantId")), j),
                                        3,
                                        1,
                                        50
                                );

                                setHBoxStyleByFieldCall(
                                        "playedMatchItems" + (i + 1) + "" + j,
                                        rectangles
                                );
                            }

                            // Set tier average
                            setImageViewStyleByFieldCall(
                                    "avgTier" + (i + 1),
                                    Tiers.valueOf(rankedSummonerInfo.getTier()).getImageEloPath()
                            );

                            // Set game score match label
                            setLabelStyleByFieldCall(
                                    "score" + (i + 1),
                                    String.valueOf(summonerPerformance.getPerformanceScore()),
                                    null
                            );

                            // Set result match label
                            setLabelStyleByFieldCall(
                                    "result" + (i + 1),
                                    matchResult,
                                    resultLabelColor
                            );

                            // Set game duration label
                            setLabelStyleByFieldCall(
                                    "gameDuration" + (i + 1), summonerMatch.getGameDuration(), null
                            );

                            // Set champion name
                            setLabelStyleByFieldCall(
                                    "championName" + (i + 1),
                                    champion.getChampionData().getString("name"),
                                    null
                            );

                            setCircleStyleByFieldCall(
                                    "playedMatchChampionIcon" + (i + 1),
                                     new Image(champion.getImageChampionBuiltUrl(ImagesUrl.SQUARE))
                            );

                            // Set kda match
                            String kdaDisplayable = matchPlayerStats.getInt("kills") + "/" +
                                    matchPlayerStats.getInt("deaths") + "/" +
                                    matchPlayerStats.getInt("assists");
                            setLabelStyleByFieldCall(
                                    "kdaMatchHistory" + (i + 1),
                                    kdaDisplayable,
                                    null
                            );

                            double calculatedKDA = NumberUtils.round(
                                    (
                                            (matchPlayerStats.getDouble("kills")
                                                    + matchPlayerStats.getDouble("assists"))
                                                    /
                                                    summonerMatch
                                                            .setDeathToWhenItIsZero(
                                                                    matchPlayerStats
                                                                            .getDouble("deaths")
                                                            )
                                    ),
                                    2
                            );

                            setLabelStyleByFieldCall(
                                    "calculatedKDA" + (i + 1),
                                    "KDA " + calculatedKDA,
                                    null
                            );
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            };

            summonerMatchListTask.setOnFailed(eMatchList -> summonerChampionsTask.getException().printStackTrace());

            summonerMatchListTask.setOnSucceeded(eMatchList -> {
                profileStage.show();
                menuStage.close();

            });


            // Set profile image icon
            String profileIconId = String.valueOf(searchedSummoner.getIconId());
            Image image = new Image(
                    "http://ddragon.leagueoflegends.com/cdn/9.23.1/img/profileicon/"
                            + profileIconId + ".png", false);
            imageCircle.setFill(new ImagePattern(image));

            // Set summoner level text
            playerLevel.setText(String.valueOf(searchedSummoner.getSummonerLevel()));

            // Set summoner name text
            summonerName.setText(searchedSummoner.getSummonerName());

            exec.execute(summonerChampionsTask);

            exec.execute(summonerMatchListTask);

//            exec.execute(rankedSummonerInfoTask);
        });
        exec.execute(championArrDataTask);
    }

    private void setLabelStyleByFieldCall(String fieldName, String fieldValue, String color)
            throws IllegalAccessException, NoSuchFieldException {
        Field field = getClass().getDeclaredField(fieldName);
        Label label = (Label) field.get(this);
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

    private void setImageViewStyleByFieldCall(String fieldName, String fieldValue)
            throws IllegalAccessException, NoSuchFieldException {
        Field field = getClass().getDeclaredField(fieldName);
        ImageView tier = (ImageView) field.get(this);
        File file = new File(fieldValue);
        tier.setImage(new Image(file.toURI().toString()));
    }
}
