package connection;

import java.beans.Statement;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Properties;

import android.icu.text.SimpleDateFormat;

public class ConnectionJDBCServer {
	static Connection con;
    Driver d;

    String username;
    String password;
    static String server="DESKTOP-MGP7BPL"; //TODO: Cambiar nombre del servidor cuando se pase éste a Azure
    static String instance="FMIRADAS";
    static String database="fmiradas";
    static int port=1433;
    boolean running;
    DataInputStream dis;
    static DataOutputStream dos;
    

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException, IOException{
    	if(args.length!=2) {
    		System.exit(1);
    	}
    	ConnectionJDBCServer conToDataBase=new ConnectionJDBCServer();
    	conToDataBase.setUsername(args[0]);
    	conToDataBase.setPassword(args[1]);
    	conToDataBase.run();
    }
    
    public void run() {
    	try(ServerSocket ss=new ServerSocket(port)){
    		Socket s=ss.accept();
    		dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
    		connect();
        	String order="";
        	ResultSet rs=null;
        	if(order!=null) {
    			if(order.length()>7) {
    				if(order.substring(0, 7).equalsIgnoreCase("SELECT ")) {
    					makeSelect(order);
    				}
    				else if(order.substring(0, 7).equalsIgnoreCase("INSERT ")) {
    					makeInsert(order);
    				}
    				else if(order.substring(0,7).equalsIgnoreCase("DELETE ")) {
    					makeDelete(order);
    				}
    			}
    		}
        	disconnect();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    public Connection getConnection() {
        return con;
    }

    public void setConnection(Connection con) {
        this.con = con;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRunning() {
        return running;
    }

    public void connect() throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException{
    	d=(Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
    	con=d.connect("jdbc:sqlserver://"+server+":"+port+";DatabaseName="+database+";instance="+instance+";encrypt=true;trustServerCertificate=true;username="+username+";password="+password, new Properties());
        this.running=true;
    	System.out.println("Conexión aceptada");
    }

    public void disconnect() throws SQLException{
        this.running=false;
        con.close();
    }
    
    @SuppressWarnings("unused")
	private static void makeSelect(String order) throws SQLException, IOException {
    	String columnsSelected=order.split(" ")[1];
    	int numColumnsSelected=getNumColumnsSelected(order,columnsSelected);
    	Statement st=(Statement) con.createStatement();
    	ResultSet rs=(ResultSet) ((java.sql.Statement) st).executeQuery(order);
    	ResultSetMetaData rsMetaData=rs.getMetaData();
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	while(rs.next()) {
    		for(int i=0;i<numColumnsSelected;i++) {
    			String type=rsMetaData.getColumnTypeName(i);
    			if(type=="VARCHAR") {dos.writeUTF(rs.getString(i));}
    			if(type=="INT") {dos.writeInt(rs.getInt(i));}
    			if(type=="DATE") {dos.writeUTF(dateFormat.format(rs.getDate(i)));}
    		}
    	}
    }
    
    private static int getNumColumnsSelected(String order,String columnsSelected) throws SQLException {
    	if(columnsSelected=="*") {
			String tableName=order.split(" ")[3];
			Statement st=(Statement) con.createStatement();
			ResultSet aux=((java.sql.Statement) st).executeQuery("SELECT COUNT(*) FROM "+tableName);
			if(aux.next()) {
				return aux.getInt(1);
			}
		}
    	return columnsSelected.split(",").length;
    }
}
