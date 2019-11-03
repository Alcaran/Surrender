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

    public static String[] getNElementsOfJSONArrayAsStringArray(int n, JSONArray in, String key) {
        String[] out = new String[n];
        for(int i = 0; i < n; i++) {
            out[i] = String.valueOf(((JSONObject) in.get(i)).getInt(key));
        }
        return out;
    }
}
