package business;

import data.api.ApiHelper;
import org.json.JSONObject;

public class Player {
    private String accountId;
    private String summonerId;
    private String summonerLevel;
    private String iconId;
    private String summonerName;

    public Player(String summonerName) throws Exception {
        ApiHelper apiHelper = new ApiHelper();
        JSONObject summonerInfo = apiHelper.getSumonerInfo("Alcarann");

        this.accountId = summonerInfo.getString("accountId");
        this.summonerLevel = summonerInfo.getString("summonerLevel");
        this.iconId = summonerInfo.getString("profileIconId");
        this.summonerId = summonerInfo.getString("id");
        this.summonerName = summonerName;
    }


    public String getSummonerName() {
        return summonerName;
    }

    public void setSummonerName(String summonerName) {
        this.summonerName = summonerName;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getSummonerId() {
        return summonerId;
    }

    public void setSummonerId(String summonerId) {
        this.summonerId = summonerId;
    }

    public String getSummonerLevel() {
        return summonerLevel;
    }

    public void setSummonerLevel(String summonerLevel) {
        this.summonerLevel = summonerLevel;
    }

    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }

}
