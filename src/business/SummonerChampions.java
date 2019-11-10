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

    public void setTopPlayedChampions(ArrayList<JSONObject> topPlayedChampions) {
        this.topPlayedChampions = topPlayedChampions;
    }

    public SummonerChampions(String summonerId) throws Exception {
        ApiHelper apiHelper = new ApiHelper();
        JSONArray championsSummonerInfo =
                apiHelper.getChampionsSummonerInfo(summonerId)
                        .getJSONArray("array");

        ArrayList<JSONObject> topPlayedChampions = RiotUtils.getChampionsNameById(
                JSONUtils.getNElementsOfJSONArrayAsStringArray(3, championsSummonerInfo, "championId")
        );

        this.topPlayedChampions = topPlayedChampions;
    }
}
