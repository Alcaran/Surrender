package data.api;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class ApiHelper {
    private HttpRequest request = new HttpRequest();
    private List<String[]> parameters = Arrays.asList();

    public JSONObject getSumonerInfo(String summonerName) throws Exception {
        return request.sendGet("/lol/summoner/v4/summoners/by-name/" + summonerName, parameters);
    }

    public JSONObject getSumonerLeagueInfo(String summonerEncriptedId) throws Exception {
        return request.sendGet("/lol/league/v4/entries/by-summoner/" + summonerEncriptedId, parameters);
    }
}
