package data.database;

import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataTipsDao {
    public JSONObject getTipsData(int gameId) throws SQLException, ClassNotFoundException {
        Connection conn = ConnectionDao.retrieveConnection();
        ArrayList<String> linkedAccounts = new ArrayList<>();
        String jsonData = null;
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "select data from tips where id = ?");
            pstmt.setInt(1, gameId);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next())  {
                jsonData = rs.getString(1);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        conn.close();
        assert jsonData != null;
        return new JSONObject(jsonData);
    }
}
