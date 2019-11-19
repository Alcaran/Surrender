package business;

import data.api.ApiHelper;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.JSONUtils;
import utils.RiotUtils;
import java.util.ArrayList;
import java.util.Iterator;

public class SummonerChampions {

    private  ArrayList<JSONObject> topPlayedChampions;

    public ArrayList<JSONObject> getTopPlayedChampions() {
        return topPlayedChampions;
    }

    public JSONObject championJsonData;

    public JSONObject getChampionByIndex (int index) {
        return topPlayedChampions.get(index);
    }

    public SummonerChampions(String summonerId, JSONObject championData) throws Exception {
        ApiHelper apiHelper = new ApiHelper();
        JSONArray championsSummonerInfo =
                apiHelper.getChampionsSummonerInfo(summonerId)
                        .getJSONArray("array");

        this.championJsonData = championData;
        this.topPlayedChampions  = getSummonerChampionsById(
                JSONUtils.getNElementsOfJSONArrayAsStringArray(
                        3,
                        championsSummonerInfo,
                        "championId"
                )
        );
    }

    private ArrayList<JSONObject> getSummonerChampionsById(String[] championsId) {
        ArrayList<JSONObject> championsName = new ArrayList<>();

        Iterator<String> keys = championJsonData.keys();
        while(keys.hasNext()) {
            String key = keys.next();
            if (championJsonData.get(key) instanceof JSONObject) {
                JSONObject obj = (JSONObject) championJsonData.get(key);
                if(championExists(championsId, (obj.getString("key")))) {
                    championsName.add(obj);
                }
            }
        }

        return championsName;
    }

    private boolean championExists(String[] championsId, String targetValue) {
        for (String champion : championsId)
            if(champion.equals(targetValue))
                return true;
        return false;
    }
}
