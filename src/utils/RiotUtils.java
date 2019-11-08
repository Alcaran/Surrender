package utils;

import data.api.ApiHelper;
import data.api.Enums.ImagesUrl;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class RiotUtils {

    static public String getChampionNameById(String championId) throws Exception {
        ApiHelper apiHelper = new ApiHelper();

        JSONArray championArrData = apiHelper.getChampionData().getJSONArray("array");
        for (Object jsonObject : championArrData) {
            JSONObject obj = (JSONObject) jsonObject;
            if(obj.getString("key") == championId) {
                return obj.getString("name");
            }
        }
        return null;
    }

    static public ArrayList<JSONObject> getChampionsNameById(String[] championsId) throws Exception {
        ApiHelper apiHelper = new ApiHelper();
        ArrayList<JSONObject> championsName = new ArrayList<>();

        JSONObject championArrData = apiHelper.getChampionData().getJSONObject("data");
        Iterator<String> keys = championArrData.keys();
        while(keys.hasNext()) {
            String key = keys.next();
            if (championArrData.get(key) instanceof JSONObject) {
                JSONObject obj = (JSONObject) championArrData.get(key);
                if(Arrays.asList(championsId).contains(obj.getString("key"))) {
                    championsName.add(obj);
                }
            }
        }

        return championsName;
    }

    static public  String getImageChampionBuiltUrl(JSONObject champion, ImagesUrl imageType) {
        return imageType.getUrl() + champion.getJSONObject("image").getString("full");
    }

    static public String getMatchParticipantId(String accountId, JSONArray participantIdentities) {
        for(Object participant : participantIdentities) {
            JSONObject participantObj = (JSONObject) participant;
            if(participantObj.getJSONObject("player").getString("accountId").equals(accountId)) {
                return String.valueOf(participantObj.getInt("participantId"));
            }
        }
        return null;
    }

    static public JSONObject getParticipant (String participantId, JSONArray participants) {
        for(Object participant : participants) {
            JSONObject participantObj = (JSONObject) participant;
            if(String.valueOf(participantObj.getInt("participantId")).equals(participantId)) {
                return participantObj;
            }
        }
        return null;
    }
}
