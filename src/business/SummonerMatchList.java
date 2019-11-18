package business;

import data.api.ApiHelper;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.JSONUtils;
import utils.RiotUtils;

import java.util.ArrayList;

public class SummonerMatchList {

    private JSONArray pureMatchHistory;
    private ArrayList<JSONObject> matchHistoryPlayedChampions;
    private ApiHelper apiHelper = new ApiHelper();

    public JSONArray getPureMatchHistory() {
        return pureMatchHistory;
    }

    public ArrayList<JSONObject> getMatchHistoryPlayedChampions() {
        return matchHistoryPlayedChampions;
    }

    public SummonerMatchList(
            String accountId,
            int matchHistoryLength,
            JSONObject championData,
            int[] championId,
            boolean needChampionData
    ) throws Exception {
        JSONArray matchHistory =
                apiHelper.getUserMatchHistory(accountId, matchHistoryLength, championId)
                        .getJSONArray("matches");

        if(needChampionData) {
            ArrayList<JSONObject> matchHistoryPlayedChampions = RiotUtils.getChampionsNameById(
                    JSONUtils.getNElementsOfJSONArrayAsStringArray(matchHistoryLength, matchHistory, "champion"),
                    championData
            );
            this.matchHistoryPlayedChampions = matchHistoryPlayedChampions;
        }


        this.pureMatchHistory = matchHistory;
    }
}
