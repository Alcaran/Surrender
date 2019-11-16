package data.database;

import java.sql.*;
import java.util.ArrayList;

public class UserDao {

    public ArrayList<String> getUser(String user, String password) throws SQLException, ClassNotFoundException {
        Connection conn = ConnectionDao.retrieveConnection();
        ArrayList<String> linkedAccounts = new ArrayList<>();
        String playerLinkedAccount = null;
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "select nickname, linked_nickname from users u left join linked_players l" +
                            " on u.id = l.user_id" +
                            " where name = ? and password = ?");
            pstmt.setString(1, user);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next())  {
                linkedAccounts.add(rs.getString("nickname"));
                playerLinkedAccount = rs.getString("linked_nickname");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        conn.close();
        if (playerLinkedAccount != null)
            linkedAccounts.add(playerLinkedAccount);
        return linkedAccounts;
    }

}
