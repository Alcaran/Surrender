package business;

import data.api.ApiHelper;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.JSONUtils;
import utils.RiotUtils;
import java.util.ArrayList;

public class SummonerChampions {

    private  ArrayList<JSONObject> topPlayedChampions;

    public ArrayList<JSONObject> getTopPlayedChampions() {
        return topPlayedChampions;
    }

    public JSONObject getChampionByIndex (int index) {
        return topPlayedChampions.get(index);
    }

    public SummonerChampions(String summonerId, JSONObject championData) throws Exception {
        ApiHelper apiHelper = new ApiHelper();
        JSONArray championsSummonerInfo =
                apiHelper.getChampionsSummonerInfo(summonerId)
                        .getJSONArray("array");

        this.topPlayedChampions  = RiotUtils.getChampionsNameById(
                JSONUtils.getNElementsOfJSONArrayAsStringArray(
                        3,
                        championsSummonerInfo,
                        "championId"
                ),
                championData
        );
    }

//    private ArrayList<JSONObject> getSummonerChampionsById(String[] championsId) {
//        ApiHelper apiHelper = new ApiHelper();
//        ArrayList<JSONObject> championsName = new ArrayList<>();
//
//        JSONObject championArrData = apiHelper.getChampionData().getJSONObject("data");
//        Iterator<String> keys = championArrData.keys();
//        while(keys.hasNext()) {
//            String key = keys.next();
//            if (championArrData.get(key) instanceof JSONObject) {
//                JSONObject obj = (JSONObject) championArrData.get(key);
//                if(Arrays.asList(championsId).contains(obj.getString("key"))) {
//                    championsName.add(obj);
//                }
//            }
//        }
//
//        return championsName;
//    }
}
