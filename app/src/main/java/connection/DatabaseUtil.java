package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {

    static String server="DESKTOP-MGP7BPL";
    static int port=1433;
    static String database="FMIRADAS";
    static String instance="fmiradas";
    private static final String DB_URL= "jdbc:sqlserver://"+server+":"+port+";databaseName="+database+";instance="+instance+";trustServerCertificate=true";
    private static final String DB_USER = "admin";
    private static final String DB_PASSWORD = "admin";

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
