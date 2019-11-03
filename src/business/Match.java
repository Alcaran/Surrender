package business;

import data.api.ApiHelper;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.RiotUtils;

public class Match {

    private String matchId;
    private JSONArray participantIdentities;
    private JSONArray participants;
    ApiHelper apiHelper = new ApiHelper();

    public Match(String matchId) throws Exception {
        JSONObject fullMatchDetails = apiHelper
                .getMatchDetails(matchId);
        this.matchId = matchId;
        this.participantIdentities = fullMatchDetails.getJSONArray("participantIdentities");
        this.participants =  fullMatchDetails.getJSONArray("participants");
    }

    public JSONObject getStatsBySummonerAccountId(String accountId) throws Exception {
        JSONObject fullMatchDetails = apiHelper
                .getMatchDetails(this.matchId);
        String participantId = RiotUtils
                .getMatchParticipantId(
                        accountId,
                        this.participantIdentities
                );
        JSONObject matchPlayerParticipant = RiotUtils
                .getParticipant(
                        participantId,
                        this.participants
                );

        return matchPlayerParticipant.getJSONObject("stats");
    }

}
