package business;

import data.api.ApiHelper;
import org.json.JSONArray;
public class SummonerMatchList {

    private JSONArray pureMatchHistory;
    private ApiHelper apiHelper = new ApiHelper();

    public JSONArray getPureMatchHistory() {
        return pureMatchHistory;
    }

    public SummonerMatchList(
            String accountId,
            int matchHistoryLength,
            int[] championId,
            Enum server
    ) throws Exception {

        this.pureMatchHistory =  apiHelper.getUserMatchHistory(accountId, matchHistoryLength, championId, server)
                .getJSONArray("matches");
    }
}
