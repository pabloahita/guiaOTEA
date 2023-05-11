package connection;

import com.google.gson.Gson;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cli.organization.AutisticEvaluatedOrganization;
import cli.organization.AutisticEvaluatorOrganization;
import cli.organization.data.Address;
import cli.user.Administrator;
import cli.user.EvaluatedOrganizationUser;
import cli.user.EvaluatorOrganizationUser;
import cli.user.User;

public class ConnectionToDatabase extends HttpServlet{

    static String urlHTTP="http://localhost:8080/ConnectionToDatabase";
    String server="DESKTOP-MGP7BPL";
    int port=1433;
    String database="FMIRADAS";
    String instance="fmiradas";
    String url = "jdbc:sqlserver://"+server+":"+port+";databaseName="+database+";instance="+instance;
    String currentUser = "admin";
    String currentPassword = "admin";
    Connection con;
    PreparedStatement stmt;
    ResultSet rs;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lógica para procesar la solicitud POST y devolver los datos de la base de datos

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lógica para procesar la solicitud GET y devolver los datos de la base de datos
        String action=request.getParameter("action");
        if(action.equals("login")){
            User user=login(request.getParameter("email"),request.getParameter("password"));
            if(user!=null){
                Gson gson = new Gson();
                String json = gson.toJson(user);
                response.setContentType("application/json");
                response.getWriter().write(json);
            }
            else{
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Unknown action: " + action);
            }
        }
        //TODO: Añadir más funcionalidades
        else{
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Unknown action: " + action);
        }
    }

    protected User login(String username, String password){
        try {
            User login =null;
            con= DriverManager.getConnection(url,currentUser,currentPassword);
            stmt = con.prepareStatement("SELECT * FROM users WHERE emailUser = ? AND passwordUser = ?");
            stmt.setString(1, username);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            if(rs.next()){
                login=findByType(username,password);
                if(login!=null){
                    con.close();
                    setConParams(username,password);
                }
            }
            return login;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private User findByType(String username, String password) throws SQLException {
        String first_name=rs.getString("first_name");
        String last_name=rs.getString("last_name");
        int telephone=rs.getInt("telephone");
        String[] nombresTablas={"administrators","autisticEvaluatorOrgUsers", "autisticEvaluatedOrgUsers"};
        rs=null;
        int i=0;
        while(!rs.next()){
            stmt=con.prepareStatement("SELECT * from "+nombresTablas[i]+" WHERE emailUser=?");
            stmt.setString(1, username);
            rs=stmt.executeQuery();
            if(rs.next()){
                if(i==0){return new Administrator(first_name, last_name, username, password, telephone);}
                if(i==1){return findEvaluatorOrgUser(username,password,first_name,last_name,telephone);}
                if(i==2){return findEvaluatedOrgUser(username,password,first_name,last_name,telephone);}
            }
        }
        return null;
    }

    private User findEvaluatorOrgUser(String username, String password, String first_name,String last_name,int telephone) throws SQLException {
        int idOrganization=rs.getInt("idOrganization");
        String illness=rs.getString("illness");
        stmt=con.prepareStatement("SELECT * FROM organizations WHERE organization.orgType=\"EVALUATOR\""+
                "IdOrganization=? AND illness=?");
        stmt.setInt(1,idOrganization);
        stmt.setString(2,illness);
        rs=stmt.executeQuery();
        if(rs.next()) {
            String name=rs.getString("name");
            String orgType=rs.getString("orgType");
            int idAddress=rs.getInt("address");
            int telephoneOrg=rs.getInt("telephone");
            String email=rs.getString("email");
            String information=rs.getString("information");
            Address address=findAddressbyId(idAddress);
            if(address!=null){
                AutisticEvaluatorOrganization org=new AutisticEvaluatorOrganization(idOrganization, orgType, illness, name, address, telephoneOrg, email,information);
                return new EvaluatorOrganizationUser(first_name, last_name, username, password, telephone, org);
            }
        }
        return null;
    }

    private User findEvaluatedOrgUser(String username, String password, String first_name,String last_name,int telephone) throws SQLException {
        int idOrganization=rs.getInt("idOrganization");
        String illness=rs.getString("illness");
        stmt=con.prepareStatement("SELECT * FROM organizations WHERE organization.orgType=\"EVALUATED\""+
                "IdOrganization=? AND illness=?");
        stmt.setInt(1,idOrganization);
        stmt.setString(2,illness);
        rs=stmt.executeQuery();
        if(rs.next()) {
            String name=rs.getString("name");
            String orgType=rs.getString("orgType");
            int idAddress=rs.getInt("address");
            int telephoneOrg=rs.getInt("telephone");
            String email=rs.getString("email");
            String information=rs.getString("information");
            Address address=findAddressbyId(idAddress);
            if(address!=null){
                AutisticEvaluatedOrganization org= new AutisticEvaluatedOrganization(idOrganization, orgType, illness, name, address, telephoneOrg, email,information);
                return new EvaluatedOrganizationUser(first_name, last_name, username, password, telephone, org);
            }
        }
        return null;
    }

    private void setConParams(String username, String password) {
        currentUser=username;
        currentPassword=password;
    }

    private Address findAddressbyId(int idAddress) throws SQLException {
        stmt=con.prepareStatement("SELECT * FROM addresses WHERE idAddress=?");
        stmt.setInt(1,idAddress);
        rs=stmt.executeQuery();
        if(rs.next()) {
            String roadType = rs.getString("roadType");
            String nameSt = rs.getString("nameSt");
            int numberSt = rs.getInt("numberSt");
            int floorSt = rs.getInt("floorSt");
            char apt = rs.getString("apt").charAt(0);
            int zipCode = rs.getInt("zipCode");
            String city = rs.getString("city");
            String province = rs.getString("province");
            String region = rs.getString("region");
            String country = rs.getString("country");
            return new Address(roadType, nameSt, numberSt, floorSt, apt, zipCode, city, province, region, country);
        }
        return null;
    }

    public static String getUrlHTTP() {
        return urlHTTP;
    }
}
