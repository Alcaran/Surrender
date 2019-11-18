package business;

import data.database.DataReferenceDao;
import data.enums.PerformanceWeightByChampionClass;
import org.json.JSONObject;
import utils.NumberUtils;

import java.sql.SQLException;
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


    public Performance(Champion champion, JSONObject participant) throws SQLException, ClassNotFoundException {
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
        for (Object pureJsonMatch : summonerMatchList.getPureMatchHistory()) {
            JSONObject matchResponse = (JSONObject) pureJsonMatch;
            Match match = new Match(String.valueOf(matchResponse.getLong("gameId")));
            participant = match.getParticipantDtoBySummonerAccountId(accountId).getJSONObject("stats");
            wardsPlaced += participant.getInt("wardsPlaced");
            kda += (
                    participant.getInt("kills") + participant.getInt("assists")
            ) / match.setDeathToWhenItIsZero(participant.getInt("deaths"));
            gold += participant.getInt("goldEarned");
            damage  += participant.getInt("totalDamageDealt");
            score += calculatePerformanceScore();
        }
        return Stream.of(
                NumberUtils.round(wardsPlaced/5, 1),
                NumberUtils.round(kda/5, 1),
                NumberUtils.round(gold/5, 1),
                NumberUtils.round(damage/5, 1),
                NumberUtils.round(score/5, 1)
        ).collect(Collectors.toList());
    }

    private double calculatePerformanceScore() throws SQLException, ClassNotFoundException {
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
                topicDataScored(numberWeights.getVision(), championReferenceData.getDouble("vision"), vision) +
                        topicDataScored(numberWeights.getGold(), championReferenceData.getDouble("gold"), gold) +
                        topicDataScored(numberWeights.getDamage(), championReferenceData.getDouble("damage"), damage) +
                        topicDataScored(numberWeights.getUtility(), championReferenceData.getDouble("utility"), utility)
        ), 1);
    }

    private double topicDataScored(double weight, double reference, double executed) {
        return (weight * executed) / reference;
    }
}
