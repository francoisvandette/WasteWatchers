package WasteWatchers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnect {

    private static final String dbUser = "root";
    private static final String dbPassword = "";
    private static final String CONN_STRING = "jdbc:mysql://localhost:3306/wastewatchers";

    public static Connection getConnectionToDatabase() throws SQLException, ClassNotFoundException {
        Connection connection = null;
        Class.forName("com.mysql.jdbc.Driver");

        try {
            connection = DriverManager.getConnection(CONN_STRING, dbUser, dbPassword);
        } catch (SQLException e) {
            System.err.println(e);
        }

        return connection;

    }

}
