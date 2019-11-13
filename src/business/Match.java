package business;

import data.api.ApiHelper;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.NumberUtils;
import utils.RiotUtils;

public class Match {

    private String matchId;
    private JSONArray participantIdentities;
    private JSONArray participants;

    public String getGameDuration() {
        return String.valueOf(NumberUtils.round(gameDuration / 60, 2)).replace(".", ":");
    }

    private double gameDuration;
    ApiHelper apiHelper = new ApiHelper();

    public Match(String matchId) throws Exception {
        JSONObject fullMatchDetails = apiHelper
                .getMatchDetails(matchId);
        this.matchId = matchId;
        this.participantIdentities = fullMatchDetails.getJSONArray("participantIdentities");
        this.participants =  fullMatchDetails.getJSONArray("participants");
        this.gameDuration = fullMatchDetails.getLong("gameDuration");
    }

    public JSONObject getParticipantDtoBySummonerAccountId(String accountId) throws Exception {
        String participantId = getMatchParticipantIdByAccountId(accountId);
        JSONObject matchPlayerParticipant = getParticipantByParticipantId(participantId);

        return matchPlayerParticipant;
    }

    private JSONObject getParticipantByParticipantId(String participantId) {
        for(Object participant : participants) {
            JSONObject participantObj = (JSONObject) participant;
            if(String.valueOf(participantObj.getInt("participantId")).equals(participantId)) {
                return participantObj;
            }
        }
        return null;
    }

    private String getMatchParticipantIdByAccountId (String accountId) {
        for(Object participant : participantIdentities) {
            JSONObject participantObj = (JSONObject) participant;
            if(participantObj.getJSONObject("player").getString("accountId").equals(accountId)) {
                return String.valueOf(participantObj.getInt("participantId"));
            }
        }
        return null;
    }

    public double setDeathToWhenItIsZero(double death) {
        if(death > 1) return death;
        else return 1;
    }
}
