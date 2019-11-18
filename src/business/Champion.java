package business;

import data.api.ApiHelper;
import data.enums.ImagesUrl;
import org.json.JSONObject;
import java.util.Iterator;

public class Champion {
    public JSONObject getChampionData() {
        return championData;
    }

    private JSONObject championData;

    public Champion(String championId ,JSONObject championData) throws Exception {
        this.championData = this.getChampionNameById(championId, championData);
    }

    public  String getImageChampionBuiltUrl(ImagesUrl imageType) {
        return imageType.getUrl() + this.championData.getJSONObject("image").getString("full");
    }

    private JSONObject getChampionNameById(String championId, JSONObject championArrData) {
        Iterator<String> keys = championArrData.keys();
        while(keys.hasNext()) {
            String key = keys.next();
            if (championArrData.get(key) instanceof JSONObject) {
                JSONObject obj = (JSONObject) championArrData.get(key);
                if(championId.equals(obj.getString("key"))) {
                    return obj;
                }
            }
        }
        return null;
    }
}
