package business;

import data.api.ApiHelper;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.JSONUtils;
import utils.RiotUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class SummonerMatchList {

    JSONArray pureMatchHistory;
    ArrayList<JSONObject> matchHistoryPlayedChampions;
    ApiHelper apiHelper = new ApiHelper();

    public JSONArray getPureMatchHistory() {
        return pureMatchHistory;
    }

    public void setPureMatchHistory(JSONArray pureMatchHistory) {
        this.pureMatchHistory = pureMatchHistory;
    }

    public ArrayList<JSONObject> getMatchHistoryPlayedChampions() {
        return matchHistoryPlayedChampions;
    }

    public void setMatchHistoryPlayedChampions(ArrayList<JSONObject> matchHistoryPlayedChampions) {
        this.matchHistoryPlayedChampions = matchHistoryPlayedChampions;
    }

    public ApiHelper getApiHelper() {
        return apiHelper;
    }

    public void setApiHelper(ApiHelper apiHelper) {
        this.apiHelper = apiHelper;
    }


    public SummonerMatchList(String accountId, int matchHistoryLength) throws Exception {
        JSONArray matchHistory =
                apiHelper.getUserMatchHistory(accountId, 5)
                        .getJSONArray("matches");
        ArrayList<JSONObject> matchHistoryPlayedChampions = RiotUtils.getChampionsNameById(
                JSONUtils.getNElementsOfJSONArrayAsStringArray(matchHistoryLength, matchHistory, "champion")
        );

        this.pureMatchHistory = matchHistory;
        this.matchHistoryPlayedChampions = matchHistoryPlayedChampions;
    }

    public ArrayList<JSONObject> getChampionsNameById() throws Exception {
        ArrayList<JSONObject> championsName = new ArrayList<>();

        JSONObject championArrData = apiHelper.getChampionData().getJSONObject("data");
        Iterator<String> keys = championArrData.keys();
        while(keys.hasNext()) {
            String key = keys.next();
            if (championArrData.get(key) instanceof JSONObject) {
                JSONObject obj = (JSONObject) championArrData.get(key);
                if(Arrays.asList(matchHistoryPlayedChampions).contains(obj.getString("key"))) {
                    championsName.add(obj);
                }
            }
        }
        return championsName;
    }
}
