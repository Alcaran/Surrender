package business;

import data.database.DataReferenceDao;
import data.database.DataTipsDao;
import data.enums.PerformanceWeightByChampionClass;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import utils.JSONUtils;
import utils.NumberUtils;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Performance {

    private JSONObject participant;
    private Champion champion;
    private double performanceScore;


    public double getPerformanceScore() {
        return performanceScore;
    }


    public Performance(Champion champion, JSONObject participant)
            throws SQLException, ClassNotFoundException, IOException, ParseException {
        this.champion = champion;
        this.participant = participant;
        this.performanceScore = calculatePerformanceScore();
    }

    public Performance(Champion champion) {
        this.champion = champion;
    }

    public List<Object> calculatePerformanceScoreWithMultipleMatches(
            SummonerMatchList summonerMatchList,
            String accountId
    ) throws Exception {
        double wardsPlaced = 0;
        double kda = 0;
        double gold = 0;
        double damage = 0;
        double score = 0;
        ArrayList<Double> playedData = new ArrayList<>();
        for (Object pureJsonMatch : summonerMatchList.getPureMatchHistory()) {

            JSONObject matchResponse = (JSONObject) pureJsonMatch;
            Match match = new Match(String.valueOf(matchResponse.getLong("gameId")));
            participant = match.getParticipantDtoBySummonerAccountId(accountId).getJSONObject("stats");
            wardsPlaced += participant.getInt("wardsPlaced");
            kda += (
                    participant.getInt("kills") + participant.getInt("assists")
            ) / match.setDeathToWhenItIsZero(participant.getInt("deaths"));
            gold += participant.getInt("goldEarned");
            damage += participant.getInt("totalDamageDealt");
            double currentScore = calculatePerformanceScore();
            ;
            score += currentScore;
            playedData.add(currentScore);
        }
        return Stream.of(
                NumberUtils.round(wardsPlaced / 5, 1),
                NumberUtils.round(kda / 5, 1),
                NumberUtils.round(gold / 5, 1),
                NumberUtils.round(damage / 5, 1),
                NumberUtils.round(score / 5, 1),
                playedData
        ).collect(Collectors.toList());
    }

    private double calculatePerformanceScore() throws SQLException, ClassNotFoundException, IOException, ParseException {
        String championTagType = String.valueOf(
                champion.getChampionData().getJSONArray("tags").get(0)
        ).toUpperCase();
        PerformanceWeightByChampionClass numberWeights = PerformanceWeightByChampionClass.valueOf(championTagType);

        double vision = participant.getInt("wardsPlaced");
        double gold = participant.getInt("goldEarned");
        double damage = participant.getInt("totalDamageDealt");
        double utility = participant.getInt("assists");

        DataReferenceDao dataReferenceDao = new DataReferenceDao();
        JSONObject referenceData = dataReferenceDao.getPerformanceData(1);
        JSONObject championReferenceData = referenceData.getJSONObject(championTagType);


        return NumberUtils.round(10 * (
                topicDataScored(numberWeights.getVision(), championReferenceData.getInt("vision"), vision) +
                        topicDataScored(numberWeights.getGold(), championReferenceData.getInt("gold"), gold) +
                        topicDataScored(numberWeights.getDamage(), championReferenceData.getInt("damage"), damage) +
                        topicDataScored(numberWeights.getUtility(), championReferenceData.getInt("utility"), utility)
        ), 1);
    }

    public JSONObject[] getWorstPerformanceAttributesTips(List<Object> performance)
            throws SQLException, ClassNotFoundException {
        String championTagType = String.valueOf(
                champion.getChampionData().getJSONArray("tags").get(0)
        ).toUpperCase();
        JSONObject tips = new DataTipsDao().getTipsData(1);
        JSONObject referenceData = new DataReferenceDao().getPerformanceData(1);
        JSONObject championReferenceData = referenceData.getJSONObject(championTagType);
        double vision = championReferenceData.getInt("vision") / (double) performance.get(0);
        double kda = 20 / (double) performance.get(1);
        double gold = championReferenceData.getInt("gold") / (double) performance.get(2);
        double damage = championReferenceData.getInt("damage") / (double) performance.get(3);
        double[] attributesPure = new double[]{vision, kda, gold, damage};
        double[] attributesSorted = new double[]{vision, kda, gold, damage};
        Arrays.sort(attributesSorted);
        double smallest = attributesSorted[0];
        double secondSmallest = attributesSorted[1];

        return new JSONObject[]{
                getChoosedTips(smallest, tips, attributesPure),
                getChoosedTips(secondSmallest, tips, attributesPure)
        };
    }


    private JSONObject getChoosedTips(double value, JSONObject tips, double[] attributes) {
        if (value == attributes[0])
            return tips.getJSONObject("vision");
        if (value == attributes[1])
            return tips.getJSONObject("utility");
        if (value == attributes[2])
            return tips.getJSONObject("gold");
        if (value == attributes[3])
            return tips.getJSONObject("damage");
        return null;
    }

    private double topicDataScored(double weight, double reference, double executed) {
        return (weight * executed) / reference;
    }
}
