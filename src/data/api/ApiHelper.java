package data.api;

import data.api.HttpRequest;
import org.json.JSONObject;

import java.util.ArrayList;

public class ApiHelper {
    private HttpRequest request = new HttpRequest();
    private ArrayList<String[]> parameters = new ArrayList<>();

    public JSONObject getSumonerInfo(String summonerName) throws Exception {
        return request
                .sendGet("/lol/summoner/v4/summoners/by-name/" + summonerName, parameters, false);
    }

    public JSONObject getSumonerLeagueInfo(String summonerEncriptedId) throws Exception {
        return request.sendGet("/lol/league/v4/entries/by-summoner/" + summonerEncriptedId, parameters, false);
    }

    public JSONObject getChampionsSummonerInfo(String summonerEncriptedId) throws Exception {
        return request.sendGet("/lol/champion-mastery/v4/champion-masteries/by-summoner/" + summonerEncriptedId, parameters, false);
    }

    public JSONObject getChampionData() throws Exception {
        return request.sendGet("https://ddragon.leagueoflegends.com/cdn/9.21.1/data/en_US/champion.json", new ArrayList<>(), true);
    }

    public JSONObject getItemData(int itemId ) throws Exception {
        return request.sendGet("http://ddragon.leagueoflegends.com/cdn/9.22.1/img/item/" + itemId + ".png", new ArrayList<>(), true);
    }

    public JSONObject getUserMatchHistory(String summonerEncriptedId, int index) throws Exception {
        parameters.add(new String[] {"endIndex", String.valueOf(index)});
        return request.sendGet(
                "/lol/match/v4/matchlists/by-account/" + summonerEncriptedId,
                parameters,
                false);
    }

    public JSONObject getMatchDetails(String matchId) throws Exception {
        return request.sendGet("/lol/match/v4/matches/" + matchId, parameters, false);
    }
}
