package data.api;

import data.api.HttpRequest;
import org.json.JSONObject;

import java.util.ArrayList;

public class ApiHelper {
    private HttpRequest request = new HttpRequest();
    private ArrayList<Object[]> parameters = new ArrayList<>();

    public JSONObject getSumonerInfo(String summonerName, Enum server) throws Exception {
        return request
                .sendGet(
                        "/lol/summoner/v4/summoners/by-name/" + summonerName,
                        parameters,
                        false,
                        server
                );
    }

    public JSONObject getSumonerLeagueInfo(String summonerEncriptedId, Enum server) throws Exception {
        return request.sendGet(
                "/lol/league/v4/entries/by-summoner/" + summonerEncriptedId,
                parameters,
                false,
                server
        );
    }

    public JSONObject getChampionsSummonerInfo(String summonerEncriptedId, Enum server) throws Exception {
        return request.sendGet(
                "/lol/champion-mastery/v4/champion-masteries/by-summoner/" + summonerEncriptedId,
                parameters,
                false,
                server);
    }

    public JSONObject getChampionData() throws Exception {
        return request.sendGet(
                "https://ddragon.leagueoflegends.com/cdn/9.23.1/data/en_US/champion.json",
                new ArrayList<>(),
                true
        );
    }

    public JSONObject getUserMatchHistory(String summonerEncriptedId, int index, int[] championFilter, Enum server) throws Exception {
        parameters = new ArrayList<>();
        parameters.add(new String[] {"endIndex", String.valueOf(index)});
        if(championFilter[0] != 0)
            parameters.add(new Object[] {"champion", championFilter});
        return request.sendGet(
                "/lol/match/v4/matchlists/by-account/" + summonerEncriptedId,
                parameters,
                false,
                server);
    }

    public JSONObject getMatchDetails(String matchId, Enum server) throws Exception {
        return request.sendGet("/lol/match/v4/matches/" + matchId, parameters, false, server);
    }
}
