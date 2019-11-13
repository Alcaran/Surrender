package business;

import data.api.ApiHelper;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.JSONUtils;
import utils.RiotUtils;

import java.util.ArrayList;

public class SummonerMatchList {

    JSONArray pureMatchHistory;
    ArrayList<JSONObject> matchHistoryPlayedChampions;
    ApiHelper apiHelper = new ApiHelper();

    public JSONArray getPureMatchHistory() {
        return pureMatchHistory;
    }

    public ArrayList<JSONObject> getMatchHistoryPlayedChampions() {
        return matchHistoryPlayedChampions;
    }

    public SummonerMatchList(String accountId, int matchHistoryLength) throws Exception {
        JSONArray matchHistory =
                apiHelper.getUserMatchHistory(accountId, matchHistoryLength)
                        .getJSONArray("matches");
        ArrayList<JSONObject> matchHistoryPlayedChampions = RiotUtils.getChampionsNameById(
                JSONUtils.getNElementsOfJSONArrayAsStringArray(matchHistoryLength, matchHistory, "champion")
        );

        this.pureMatchHistory = matchHistory;
        this.matchHistoryPlayedChampions = matchHistoryPlayedChampions;
    }
}
