package business;

import data.api.ApiHelper;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.NumberUtils;

public class Match {

    private JSONObject fullMatchDetails;
    private String matchId;
    private JSONArray participantIdentities;
    private JSONArray participants;

    public String getGameDuration() {
        return String.valueOf(NumberUtils.round(gameDuration / 60, 2)).replace(".", ":");
    }

    private double gameDuration;
    private ApiHelper apiHelper = new ApiHelper();

    public Match(String matchId) throws Exception {
        JSONObject fullMatchDetails = apiHelper
                .getMatchDetails(matchId);
        this.fullMatchDetails = fullMatchDetails;
        this.matchId = matchId;
        this.participantIdentities = fullMatchDetails.getJSONArray("participantIdentities");
        this.participants = fullMatchDetails.getJSONArray("participants");
        this.gameDuration = fullMatchDetails.getLong("gameDuration");
    }

    public JSONObject getParticipantDtoBySummonerAccountId(String accountId) {
        String participantId = getMatchParticipantIdByAccountId(accountId);
        JSONObject matchPlayerParticipant = getParticipantByParticipantId(participantId);

        return matchPlayerParticipant;
    }

    public String getMatchResultByParticipantId(String participantId) {
        JSONObject participant = getParticipantByParticipantId(participantId);
        String participantMatchResult =
                (
                        (JSONObject) fullMatchDetails
                            .getJSONArray("teams").get(
                                getParticipantTeamIndex(participant)
                        )
                ).getString("win");

        if (participantMatchResult.equals("Win"))
            return "Victory";
        else
            return "Defeat";
    }

    public double setDeathToWhenItIsZero(double death) {
        if (death > 1) return death;
        else return 1;
    }

    // Private functions

    private int getParticipantTeamIndex(JSONObject participant) {
        return participant.getInt("teamId") == 100 ? 0 : 1;
    }

    private JSONObject getParticipantByParticipantId(String participantId) {
        for (Object participant : participants) {
            JSONObject participantObj = (JSONObject) participant;
            if (String.valueOf(participantObj.getInt("participantId")).equals(participantId)) {
                return participantObj;
            }
        }
        return null;
    }

    private String getMatchParticipantIdByAccountId(String accountId) {
        for (Object participant : participantIdentities) {
            JSONObject participantObj = (JSONObject) participant;
            if (participantObj.getJSONObject("player").getString("accountId").equals(accountId)) {
                return String.valueOf(participantObj.getInt("participantId"));
            }
        }
        return null;
    }
}
