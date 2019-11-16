package data.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class ConnectionDao {
    static Connection retrieveConnection() throws ClassNotFoundException, SQLException {
        Connection con;
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(
                "jdbc:mysql://surrender.c9wyymupfjjf.us-east-2.rds.amazonaws.com:3306/surrender",
                "admin", "12345678"
        );
        return con;
    }
}
