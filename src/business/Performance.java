package business;

import data.database.DataReferenceDao;
import data.enums.PerformanceWeightByChampionClass;
import data.enums.PerformanceWeightByRole;
import org.json.JSONObject;
import utils.NumberUtils;

import java.sql.SQLException;

public class Performance {

    private JSONObject participant;
    private  Champion champion;
    private double performanceScore;


    public double getPerformanceScore() {
        return performanceScore;
    }


    public Performance(Champion champion, JSONObject participant) throws SQLException, ClassNotFoundException {
        this.champion = champion;
        this.participant = participant;
        this.performanceScore = calculatePerformanceScore();
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

        return NumberUtils.round(10*(
                topicDataScored( numberWeights.getVision(), championReferenceData.getDouble("vision"), vision) +
                topicDataScored( numberWeights.getGold(), championReferenceData.getDouble("gold"), gold) +
                topicDataScored( numberWeights.getDamage(), championReferenceData.getDouble("damage"), damage) +
                topicDataScored( numberWeights.getUtility(), championReferenceData.getDouble("utility"), utility)
        ), 1);
    }

    private double topicDataScored(double weight, double reference, double executed) {
        return (weight * executed) / reference;
    }
}
