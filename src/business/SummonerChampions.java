package business;

import data.api.ApiHelper;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.JSONUtils;
import java.util.ArrayList;
import java.util.Iterator;

public class SummonerChampions {

    private  ArrayList<Champion> topPlayedChampions;

    public ArrayList<Champion> getTopPlayedChampions() {
        return topPlayedChampions;
    }

    private JSONObject championJsonData;

    public Champion getChampionByIndex (int index) {
        return topPlayedChampions.get(index);
    }

    public SummonerChampions(String summonerId, JSONObject championData) throws Exception {
        ApiHelper apiHelper = new ApiHelper();
        JSONArray championsSummonerInfo =
                apiHelper.getChampionsSummonerInfo(summonerId)
                        .getJSONArray("array");

        this.championJsonData = championData;
        this.topPlayedChampions  = buildTopPlayedChampionArr(
                JSONUtils.getNElementsOfJSONArrayAsStringArray(
                        3,
                        championsSummonerInfo,
                        "championId"
                )
        );
    }

    private ArrayList<Champion> buildTopPlayedChampionArr(String[] championsId) {
        ArrayList<Champion> champions = new ArrayList<>();
        for(String championId : championsId) {
            champions.add(new Champion(championId, championJsonData));
        }
        return champions;
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
