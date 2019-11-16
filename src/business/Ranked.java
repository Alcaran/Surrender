package business;

import data.api.ApiHelper;
import data.enums.QueueType;
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

        assert soloDuoInfo != null;
        this.leaguePoints = String.valueOf(soloDuoInfo.getInt("leaguePoints"));
        this.wins = String.valueOf(soloDuoInfo.getInt("wins"));
        this.losses = String.valueOf(soloDuoInfo.getInt("losses"));
        this.tier = soloDuoInfo.getString("tier");
    }

    public String getLeaguePoints() {
        return leaguePoints;
    }

    public String getWins() {
        return wins;
    }

    public String getLosses() {
        return losses;
    }

    public String getTier() {
        return tier;
    }
}
