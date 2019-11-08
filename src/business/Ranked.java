package business;

import data.api.ApiHelper;
import data.api.Enums.QueueType;
import org.json.JSONObject;
import utils.JSONUtils;

public class Ranked {
    private String leaguePoints;
    private String wins;
    private String losses;
    private String tier;

    public Ranked (String summonerId) throws Exception {
        ApiHelper apiHelper = new ApiHelper();
        JSONObject summonerLeagueInfo = apiHelper.getSumonerLeagueInfo(summonerId);
        JSONObject soloDuoInfo = JSONUtils.findIObjectInArrayByKeyStringValue(
                "queueType",
                QueueType.SoloDuo.getType(),
                summonerLeagueInfo
                        .getJSONArray("array")
        );

        this.leaguePoints = String.valueOf(soloDuoInfo.getInt("leaguePoints"));
        this.wins = String.valueOf(soloDuoInfo.getInt("wins"));
        this.losses = String.valueOf(soloDuoInfo.getInt("losses"));
        this.tier = soloDuoInfo.getString("tier");
    }

    public String getLeaguePoints() {
        return leaguePoints;
    }

    public void setLeaguePoints(String leaguePoints) {
        this.leaguePoints = leaguePoints;
    }

    public String getWins() {
        return wins;
    }

    public void setWins(String wins) {
        this.wins = wins;
    }

    public String getLosses() {
        return losses;
    }

    public void setLosses(String losses) {
        this.losses = losses;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }
}
