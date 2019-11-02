package utils;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONUtils {

    public static JSONObject findIObjectInArrayByKeyStringValue (String key, String value, JSONArray arr) {
        for (Object jsonObject : arr) {
            JSONObject obj = (JSONObject) jsonObject;
            if(obj.getString(key).equals(value)) {
                return obj;
            }
        }
        return null;
    }
}
