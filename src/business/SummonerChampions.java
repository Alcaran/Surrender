package business;

import data.api.ApiHelper;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.JSONUtils;
import java.util.ArrayList;
import java.util.Iterator;

public class SummonerChampions {

    private  ArrayList<Champion> topPlayedChampions;

    private JSONObject championJsonData;

    public Champion getChampionByIndex(int index) {
        return topPlayedChampions.get(index);
    }

    public SummonerChampions(String summonerId, JSONObject championData, Enum server) throws Exception {
        ApiHelper apiHelper = new ApiHelper();
        JSONArray championsSummonerInfo =
                apiHelper.getChampionsSummonerInfo(summonerId, server)
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
}
