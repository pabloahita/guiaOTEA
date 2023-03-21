package connection;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionToLocalDatabase{

    Connection con;
    Driver d;
    public ConnectionToLocalDatabase(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            d = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            con=d.connect("jdbc:sqlserver://localhost:1433;DatabaseName=fmiradas;instance=DESKTOP-PDB0U3J\\FMIRADAS;encrypt=true;trustServerCertificate=true;user=admin;password=admin", new Properties());
        } catch (IllegalAccessException e) {
            Log.d("IllegalAccessException",e.toString());
        } catch (InstantiationException e) {
            Log.d("InstantiationException",e.toString());
        } catch (ClassNotFoundException e) {
            Log.d("ClassNotFoundException",e.toString());
        } catch (SQLException e) {
            Log.d("SQLException",e.toString());
        }
    }
    public Connection getConnection() {
        return con;
    }

    public void setConnection(Connection con) {
        this.con = con;
    }





}
