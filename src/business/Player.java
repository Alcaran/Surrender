package business;

import data.api.ApiHelper;
import org.json.JSONObject;

public class Player {
    private String accountId;
    private String summonerId;
    private int summonerLevel;
    private int iconId;
    private String summonerName;

    public Player(String summonerName) throws Exception {
        ApiHelper apiHelper = new ApiHelper();
        JSONObject summonerInfo = apiHelper.getSumonerInfo(summonerName);

        this.accountId = summonerInfo.getString("accountId");
        this.summonerLevel = summonerInfo.getInt("summonerLevel");
        this.iconId = summonerInfo.getInt("profileIconId");
        this.summonerId = summonerInfo.getString("id");
        this.summonerName = summonerInfo.getString("name");
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

    public int getSummonerLevel() {
        return summonerLevel;
    }

    public void setSummonerLevel(int summonerLevel) {
        this.summonerLevel = summonerLevel;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

}
