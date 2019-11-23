package business;

import data.api.ApiHelper;
import data.database.UserDao;
import org.json.JSONObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private String accountId;
    private String summonerId;
    private int summonerLevel;
    private int iconId;
    private String summonerName;
    private ArrayList<String> linkedAccounts;

    public Player(String summonerName, Enum server) throws Exception {
        ApiHelper apiHelper = new ApiHelper();
        JSONObject summonerInfo = apiHelper.getSumonerInfo(summonerName,server);

        this.accountId = summonerInfo.getString("accountId");
        this.summonerLevel = summonerInfo.getInt("summonerLevel");
        this.iconId = summonerInfo.getInt("profileIconId");
        this.summonerId = summonerInfo.getString("id");
        this.summonerName = summonerInfo.getString("name");
    }

    public Player(String name, String password) throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDao();
        linkedAccounts = userDao.getUser(name, password);
    }




    public String getSummonerName() {
        return summonerName;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getSummonerId() {
        return summonerId;
    }

    public int getSummonerLevel() {
        return summonerLevel;
    }

    public int getIconId() {
        return iconId;
    }

    public ArrayList<String> getLinkedAccounts() {
        return linkedAccounts;
    }
}
